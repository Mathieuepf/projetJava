package com.epf.rentmanager.service;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservationService {
    private ReservationDao reservationDao;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
    }
    public ClientService getClientService() {
        return clientService;
    }
    public VehicleService getVehicleService() {
        return vehicleService;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            if (this.rentConditions(reservation))
                return this.getReservationDao().create(reservation);
            else return -1;
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        try {
            return this.getReservationDao().delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation> findResaByVehicleId(long vehicleId) throws ServiceException {
        try {
            List<Reservation> reservations = this.getReservationDao().findResaByVehicleId(vehicleId);
            for(Reservation reservation : reservations){
                reservation.setClient(this.getClientService().findById(reservation.getClient_id()));
                reservation.setVehicule(this.getVehicleService().findById(reservation.getVehicule_id()));
            }
            return reservations;
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation> findResaByClientId(long clientId) throws ServiceException {
        try {
            List<Reservation> reservations = this.getReservationDao().findResaByClientId(clientId);
            for(Reservation reservation : reservations) {
                reservation.setClient(this.getClientService().findById(reservation.getClient_id()));
                reservation.setVehicule(this.getVehicleService().findById(reservation.getVehicule_id()));
            }

            return reservations;
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            List<Reservation> reservations = this.getReservationDao().findAll();
            for(Reservation reservation : reservations){
                reservation.setClient(this.getClientService().findById(reservation.getClient_id()));
                reservation.setVehicule(this.getVehicleService().findById(reservation.getVehicule_id()));
            }
            return reservations;
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public Reservation findById(long id) throws ServiceException {
        try {
            Reservation reservation = this.getReservationDao().findById(id);
            reservation.setClient(this.getClientService().findById(reservation.getClient_id()));
            reservation.setVehicule(this.getVehicleService().findById(reservation.getVehicule_id()));
            return reservation;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int count() throws ServiceException {
        try {
            return this.getReservationDao().count();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long update(Reservation reservation) throws ServiceException {
        try {
            if (this.rentConditions(reservation))
                return this.getReservationDao().update(reservation);
            else
                return -1;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    private boolean rentConditions(Reservation reservation) throws ServiceException {
        if (reservation.getDebut().isAfter(reservation.getFin()))
            return false;
        if (reservation.getDureeReservation() > 7)
            return false;

        List<Reservation> reservations = this.findResaByVehicleId(reservation.getVehicule_id());
        List<Reservation> listResClient = new ArrayList<Reservation>();
        reservations.remove(reservation);
        int nbJoursResaVoitCumule = reservation.getDureeReservation();
        if (!reservations.isEmpty())
            nbJoursResaVoitCumule = this.dureesResaCumulees(reservations, reservation);

        if (nbJoursResaVoitCumule > 30)
            return false;

        for (Reservation r : reservations){
            if (reservation.equals(r))
                continue;
            if (reservation.getDebut().isAfter(r.getDebut()) && reservation.getDebut().isBefore(r.getFin()))
                return false;
            if (reservation.getFin().isAfter(r.getDebut()) && reservation.getFin().isBefore(r.getFin()))
                return false;
            if (reservation.getDebut().isBefore(r.getDebut()) && reservation.getFin().isAfter(r.getFin()))
                return false;
            if (reservation.getDebut().isEqual(r.getDebut()) || reservation.getDebut().isEqual(r.getFin())
                    || reservation.getFin().isEqual(r.getDebut()) || reservation.getFin().isEqual(r.getFin()))
                return false;
            if (r.getClient_id() == reservation.getClient_id())
                listResClient.add(r);
        }
        if (!listResClient.isEmpty()){
            int nbJoursResaClientCumule = this.dureesResaCumulees(listResClient,reservation);
            if (nbJoursResaClientCumule > 7)
                return false;
        }
        return true;
    }

    private int dureesResaCumulees(List<Reservation> reservations, Reservation reservation){
        int dureeCumulee = reservation.getDureeReservation();
        Reservation currentReservation = reservation;

        boolean havePreduce = true;
        boolean haveNext = true;
        while(havePreduce && dureeCumulee <= 30) {
            for (Reservation r : reservations) {
                if (Math.abs(Period.between(currentReservation.getDebut(),r.getFin()).getDays()) == 1){
                    dureeCumulee += r.getDureeReservation();
                    currentReservation = r;
                    break;
                }

                if(reservations.get(reservations.size() - 1).equals(r))
                    havePreduce = false;
            }
        }

        currentReservation = reservation;

        while (haveNext && dureeCumulee <= 30) {
            for (Reservation r : reservations){
                if (Math.abs(Period.between(currentReservation.getFin(),r.getDebut()).getDays()) == 1) {
                    dureeCumulee += r.getDureeReservation();
                    currentReservation = r;
                    break;
                }
                if(reservations.get(reservations.size() - 1).equals(r))
                    haveNext = false;
            }
        }

        return dureeCumulee;
    }
}