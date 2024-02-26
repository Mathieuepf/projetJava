package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
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

			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, vehicule.getConstructeur());
			ps.setShort(2, vehicule.getNb_places());

			ResultSet results = ps.getResultSet();
			results.next();
			long createId = results.getLong(1);

			results.close();
			ps.execute();
			ps.close();
			connection.close();

			return createId;
		}catch (SQLException e) {
			throw new DaoException();
		}
	}

	public long delete(Vehicule vehicule) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY);

			ps.setLong(1, vehicule.getId());

			ps.execute();
			ps.close();
			connection.close();

			return vehicule.getId();

		}catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Vehicule findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);

			ps.setLong(1, id);

			ps.execute();

			ResultSet resultSet = ps.getResultSet();

			resultSet.close();
			ps.close();
			connection.close();


			return new Vehicule(id,resultSet.getString(2),resultSet.getShort(3));

		} catch (SQLException e){
			throw new DaoException();
		}

	}

	public List<Vehicule> findAll() throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);

			ps.execute();
			ResultSet resultSet = ps.getResultSet();

			ArrayList<Vehicule> vehiculeList = new ArrayList<Vehicule>();

			while (resultSet.next()) {
				vehiculeList.add(new Vehicule(resultSet.getLong(1), resultSet.getString(2), resultSet.getShort(3)));
			}

			resultSet.close();
			ps.close();
			connection.close();

			return vehiculeList;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}
}