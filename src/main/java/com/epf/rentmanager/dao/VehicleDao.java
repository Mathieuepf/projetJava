package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.Exception.DaoException;

import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	
	public long create(Vehicule vehicule) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY);

			ps.setString(1, vehicule.getConstrueur());
			ps.setShort(2, vehicule.getNb_places());

			ps.execute();
			ps.close();
			connection.close();

			return vehicule.getId();
		}catch (SQLException e) {
			throw new DaoException();
		}
	}

	public long delete(Vehicule vehicule) throws DaoException {
		return 0;
	}

	public Vehicule findById(long id) throws DaoException {
		return new Vehicule();
	}

	public List<Vehicule> findAll() throws DaoException {
		return new ArrayList<Vehicule>();
		
	}
	

}
