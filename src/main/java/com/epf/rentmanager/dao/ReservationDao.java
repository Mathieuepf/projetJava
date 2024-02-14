package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY);

			ps.setLong(1, reservation.getClient_id());
			ps.setLong(2, reservation.getVehicule_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));

			ps.execute();
			ps.close();
			connection.close();

			return reservation.getId();
		}catch (SQLException e) {
			throw new DaoException();
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY);

			ps.setLong(1, reservation.getId());

			ps.execute();
			ps.close();
			connection.close();

			return reservation.getId();

		}catch (SQLException e) {
			throw new DaoException();
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);

			ps.setLong(1, clientId);

			ps.execute();
			ResultSet resultSet = ps.getGeneratedKeys();

			ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

			while (resultSet.next()) {
				reservationList.add(new Reservation(resultSet.getLong(1), clientId, resultSet.getLong(2), resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate()));
			}

			resultSet.close();
			ps.close();
			connection.close();

			return reservationList;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);

			ps.setLong(1, vehicleId);

			ps.execute();
			ResultSet resultSet = ps.getGeneratedKeys();

			ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

			while (resultSet.next()) {
				reservationList.add(new Reservation(resultSet.getLong(1), resultSet.getLong(2), vehicleId, resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate()));
			}

			resultSet.close();
			ps.close();
			connection.close();

			return reservationList;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public List<Reservation> findAll() throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY);

			ps.execute();
			ResultSet resultSet = ps.getGeneratedKeys();

			ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

			while (resultSet.next()) {
				reservationList.add(new Reservation(resultSet.getLong(1), resultSet.getLong(2), resultSet.getLong(3), resultSet.getDate(4).toLocalDate(), resultSet.getDate(5).toLocalDate()));
			}

			resultSet.close();
			ps.close();
			connection.close();

			return reservationList;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}
}
