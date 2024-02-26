package com.epf.rentmanager.ui.cli;
import com.epf.rentmanager.Exception.CliException;
import com.epf.rentmanager.ui.cli.ClientCli;
import com.epf.rentmanager.ui.cli.VehicleCli;
import com.epf.rentmanager.utils.IOUtils;

public class Main {

    public static void main(String[] args) {
        ClientCli clientCli = ClientCli.getInstance();
        VehicleCli vehicleCli = VehicleCli.getInstance();
        try {
            //long n = clientCli.create();
            //clientCli.findAll();
            long nv = vehicleCli.create();
            vehicleCli.findAll();
        } catch (CliException e) {
            throw new RuntimeException(e);
        }
    }
}
