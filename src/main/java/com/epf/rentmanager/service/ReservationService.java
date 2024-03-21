package com.epf.rentmanager.service;

import java.util.List;
import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            reservation.setId(this.getReservationDao().create(reservation));
            return reservation.getId();
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

    public List<Reservation> findResaByVehicletId(long vehicleId) throws ServiceException {
        try {
            List<Reservation> reservations = this.getReservationDao().findResaByClientId(vehicleId);
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
                System.out.println("résa id : " + reservation.getId() + "véhicule id " + reservation.getVehicule_id());
                reservation.setClient(this.getClientService().findById(reservation.getClient_id()));
                reservation.setVehicule(this.getVehicleService().findById(reservation.getVehicule_id()));
                System.out.println("résa id : " + reservation.getId() + " véhicule : " + this.getVehicleService().findById(reservation.getVehicule_id()));
                System.out.println("résa id : " + reservation.getId() + " client : " + this.getClientService().findById(reservation.getVehicule_id()));
            }

            System.out.println("après boucle : "+reservations.getFirst().getVehicule());

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

    public int count() throws ServiceException {
        try {
            return this.getReservationDao().count();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}