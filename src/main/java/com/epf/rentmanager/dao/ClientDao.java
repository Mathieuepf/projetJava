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
import java.util.Optional;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.Exception.DaoException;

import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY);

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));

			ps.execute();
			ps.close();
			connection.close();
		}catch (SQLException e) {
			throw new DaoException();
		}
		return client.getId(); // SQL
	}
	
	public long delete(Client client) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY);

			ps.setLong(1, client.getId());

			ps.execute();
			ps.close();
			connection.close();
        } catch (SQLException e) {
            throw new DaoException();
        }

        return client.getId();
	}

	public Client findById(long id) throws DaoException { // Slide n 22
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY);

			ps.setLong(1, id);

			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();


			resultSet.close();
			ps.close();
			connection.close();

			return new Client(id,resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDate(4).toLocalDate());
		} catch (SQLException e){
			throw new DaoException();
		}
	}

	public List<Client> findAll() throws DaoException {

		try {
			ArrayList<Client> clientList = new ArrayList<Client>();

			Connection connection = ConnectionManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);
			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();

			resultSet.close();
			ps.close();
			connection.close();

			while (resultSet.next()) {
				clientList.add(new Client(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDate(5).toLocalDate()));
			}

			return clientList;

		} catch (SQLException e) {
			throw new DaoException();
		}

	}

}
