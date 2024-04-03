package com.epf.rentmanager.service;

import java.util.List;
import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	@Autowired
	private ReservationService reservationService;

	@Autowired
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	public VehicleDao getVehicleDao() {
		return vehicleDao;
	}

	public ReservationService getReservationService() {
		return reservationService;
	}

	public long create(Vehicule vehicle) throws ServiceException {
		try {
			if((!(vehicle.getConstructeur() == null)) && !vehicle.getConstructeur().isEmpty())
				return this.getVehicleDao().create(vehicle);
			else throw new ServiceException();
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public long delete(Vehicule vehicule) throws ServiceException {
		try {
			List<Reservation> reservations = this.getReservationService().findResaByVehicleId(vehicule.getId());
			reservations.forEach(reservation -> {
                try {
                    this.getReservationService().delete(reservation);
                } catch (ServiceException e) {
                    throw new RuntimeException(e);
                }
            });
			return this.getVehicleDao().delete(vehicule);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public Vehicule findById(long id) throws ServiceException {
		try {
			return this.getVehicleDao().findById(id);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public List<Vehicule> findAll() throws ServiceException {
		try{
			return this.getVehicleDao().findAll();
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public int count() throws ServiceException {
		try {
			return this.getVehicleDao().count();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public long update(Vehicule vehicule) throws ServiceException {
		try {
			return this.getVehicleDao().update(vehicule);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
}
