package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.Exception.CliException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.utils.IOUtils;

import java.util.ArrayList;
import java.util.List;

public class VehicleCli {
    private VehicleService vehicleService;
    public static VehicleCli instance;

    private VehicleCli() { this.vehicleService = VehicleService.getInstance(); }

    public static VehicleCli getInstance() {
        if(instance == null)
            instance = new VehicleCli();
        return instance;
    }

    public VehicleService getVehicleService() {
        return vehicleService;
    }

    public long create() throws CliException {
        try {
            String constructeur;
            short nb_places;
            constructeur = IOUtils.readString("Indiquez le constructeur de votre voiture",true);
            nb_places = IOUtils.readShort("Indiquez le nombre de voyageurs maximum dans votre voiture");
            Vehicule vehicule = new Vehicule(constructeur,nb_places);
            long id = this.getVehicleService().create(vehicule);
            vehicule.setId(id);
            return id;
        } catch (ServiceException e) {
            throw new CliException();
        }
    }

    public void findAll() throws CliException {
        try {
            List<Vehicule> vehicules = this.getVehicleService().findAll();
            for (Vehicule vehicule : vehicules) {
                IOUtils.print(vehicule.toString());
            }
        } catch (ServiceException e) {
            throw new CliException();
        }
    }
}