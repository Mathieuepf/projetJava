package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.Period;
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
			Period agePeriod = Period.between(client.getNaissance(),LocalDate.now());
			int age = agePeriod.getYears();
			if (client.getPrenom().length() > 2 && client.getPrenom().length() > 2
					&& age >= 18 && !this.findAll().contains(client)) {
				return this.getClientDao().create(client);
			} else return -1;
		}catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	public long delete(Client client) throws ServiceException {
		try {
			return this.getClientDao().delete(client);
		}catch (DaoException e){
			throw new ServiceException(e);
		}
	}

	public Client findById(long id) throws ServiceException {
		try {
			return this.getClientDao().findById(id);
		}catch (DaoException e){
			throw new ServiceException(e);
		}
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return this.getClientDao().findAll();
		}catch (DaoException e){
			throw new ServiceException(e);
		}
	}

	public int count() throws ServiceException {
		try {
			return this.getClientDao().count();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	public long update(Client client) throws ServiceException {
        try {
			Period agePeriod = Period.between(client.getNaissance(),LocalDate.now());
			int age = agePeriod.getYears();
			if (client.getPrenom().length() > 2 && client.getPrenom().length() > 2
					&& age >= 18) {
				return this.getClientDao().update(client);
			} else return -1;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}