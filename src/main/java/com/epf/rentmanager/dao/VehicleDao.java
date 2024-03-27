package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle;";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";
	
	public long create(Vehicule vehicule) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, vehicule.getConstructeur());
			ps.setString(2, vehicule.getModele());
			ps.setShort(3, vehicule.getNb_places());

			ps.execute();
			ResultSet results = ps.getGeneratedKeys();
			results.next();
			long createId = results.getLong(1);

			results.close();
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
			throw new DaoException(e);
		}
	}

	public Vehicule findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);

			ps.setLong(1, id);

			ps.execute();

			ResultSet resultSet = ps.getResultSet();
			resultSet.next();
			String constructeur = resultSet.getString(2);
			String modele = resultSet.getString(3);
			short nb_places = resultSet.getShort(4);

			resultSet.close();
			ps.close();
			connection.close();


			return new Vehicule(id,constructeur, modele, nb_places);

		} catch (SQLException e){
			throw new DaoException(e);
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
				vehiculeList.add(new Vehicule(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getShort(4)));
			}

			resultSet.close();
			ps.close();
			connection.close();

			return vehiculeList;

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public int count() throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_VEHICLES_QUERY);
			ps.execute();
			ResultSet resultSet = ps.getResultSet();
			resultSet.next();
			int count = resultSet.getInt(1);

			resultSet.close();
			ps.close();
			connection.close();
			return count;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public long update(Vehicule vehicule) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(UPDATE_VEHICLE_QUERY);


			ps.setString(1, vehicule.getConstructeur());
			ps.setString(2, vehicule.getModele());
			ps.setShort(3, vehicule.getNb_places());
			ps.setLong(4, vehicule.getId());

			ps.execute();

			ps.close();
			connection.close();

			return vehicule.getId();

		}catch (SQLException e) {
			throw new DaoException(e);
		}
	}
}