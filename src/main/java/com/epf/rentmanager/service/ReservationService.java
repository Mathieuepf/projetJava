package com.epf.rentmanager.service;

import java.util.List;
import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;

import java.util.List;

public class ReservationService {
    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService() { this.reservationDao = ReservationDao.getInstance(); }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
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
            return this.getReservationDao().findResaByVehicleId(vehicleId);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation> findResaByClientId(long clientId) throws ServiceException {
        try {
            return this.getReservationDao().findResaByClientId(clientId);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return this.getReservationDao().findAll();
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }


}
