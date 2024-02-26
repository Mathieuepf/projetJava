package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.Exception.CliException;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientCli {
    private ClientService clientService;
    public static ClientCli instance;

    private ClientCli() { this.clientService = ClientService.getInstance(); }

    public static ClientCli getInstance() {
        if(instance == null)
            instance = new ClientCli();
        return instance;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public long create() throws CliException {
        try {
            String nom, prenom, email;
            LocalDate naissance;
            nom = IOUtils.readString("Indiquez votre nom : ", true);
            prenom = IOUtils.readString("Indiquez votre prénom : ", true);
            email = IOUtils.readString("Indiquez votre mail : ", true);
            naissance = IOUtils.readDate("Indiquez votre date de naissance : ",true);
            return this.getClientService().create(new Client(nom,prenom,email,naissance));
        } catch (ServiceException e) {
            throw new CliException();
        }
    }

    public void findAll() throws CliException {
        try {
            List<Client> clients = new ArrayList<Client>();
            clients = this.getClientService().findAll();
            //System.out.println("Size du clients " + clients.size());
            for (Client client : clients) {
                IOUtils.print(client.toString());
            }
        } catch (ServiceException e) {
            throw new CliException();
        }
    }
}