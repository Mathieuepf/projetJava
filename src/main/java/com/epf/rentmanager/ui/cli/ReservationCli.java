package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.Exception.CliException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationCli {
    private ReservationService reservationService;
    public static ReservationCli instance;

    private ReservationCli() { this.reservationService = ReservationService.getInstance(); }

    public static ReservationCli getInstance() {
        if(instance == null)
            instance = new ReservationCli();
        return instance;
    }
    public ReservationService getReservationService() {
        return reservationService;
    }

    public long create() throws CliException {
        try {
            long client_id, vehicle_id;
            LocalDate debut, fin;
            client_id = IOUtils.readLong("Indiquez l'id du client qui réalise la réservation");
            vehicle_id = IOUtils.readLong("Indiquez l'id du véhicule loué");
            debut = IOUtils.readDate("Indiquez la date de début de réservation",true);
            fin = IOUtils.readDate("Indiquez la date de fin de réservation",true);
            return this.getReservationService().create(new Reservation(client_id,vehicle_id,debut,fin));
        } catch (ServiceException e) {
            throw new CliException();
        }
    }

    /*public long delete() throws CliException {
        try {

        } catch (ServiceException e) {
            throw new CliException();
        }
    }*/

    public void findResaByVehicletId() throws CliException {
        try {
            long vehicle_id = IOUtils.readLong("Indiquez l'id du véhicule dont vous souhaitez les réservations");
            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations = this.getReservationService().findResaByVehicletId(vehicle_id);
            for (Reservation reservation : reservations){
                IOUtils.print(reservation.toString());
            }
        } catch (ServiceException e) {
            throw new CliException();
        }
    }
    public void findResaByClientId() throws CliException {
        try {
            long client_id = IOUtils.readLong("Indiquez l'id du client dont vous souhaitez les réservations");
            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations = this.getReservationService().findResaByVehicletId(client_id);
            for (Reservation reservation : reservations){
                IOUtils.print(reservation.toString());
            }
        } catch (ServiceException e) {
            throw new CliException();
        }
    }
    public void findall() throws CliException {
        try {
            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations = this.getReservationService().findAll();
            for (Reservation reservation : reservations){
                IOUtils.print(reservation.toString());
            }
        } catch (ServiceException e) {
            throw new CliException();
        }
    }
}
