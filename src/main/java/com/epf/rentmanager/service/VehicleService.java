package com.epf.rentmanager.service;

import java.util.List;
import java.util.Objects;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		return instance;
	}

	public VehicleDao getVehicleDao() {
		return vehicleDao;
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
		}catch (Exception e){
			throw new ServiceException();
		}
	}
	
}
