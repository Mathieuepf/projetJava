package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;

	@Autowired
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public ClientDao getClientDao() {
		return clientDao;
	}

	public long create(Client client) throws ServiceException {
		try {
			if ((!(client.getNom() == null)) && !client.getNom().isEmpty()) {
				return this.getClientDao().create(client);
			} else throw new ServiceException();
		}catch (DaoException e) {
			System.out.println("ExceptionDAO");
			throw new ServiceException();
		}
	}

	public long delete(Client client) throws ServiceException {
		try {
			return this.getClientDao().delete(client);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public Client findById(long id) throws ServiceException {
		try {
			return this.getClientDao().findById(id);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return this.getClientDao().findAll();
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public int count() throws ServiceException {
		try {
			return this.getClientDao().count();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
}