package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Reservation {
    private long id;
    private LocalDate debut, fin;
    private long client_id;
    private long vehicule_id;
    private Vehicule vehicule;
    private Client client;
    private int dureeReservation;

    public long getId() {
        return id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public long getClient_id() {
        return client_id;
    }

    public long getVehicule_id() {
        return vehicule_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public void setVehicule_id(long vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Client getClient() {
        return client;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public int getDureeReservation() {
        return dureeReservation;
    }

    public void setDureeReservation() {
        this.dureeReservation = Period.between(this.getDebut(),this.getFin()).getDays() + 1;
    }

    public Reservation (long id, long client_id, long vehicule_id, LocalDate debut, LocalDate fin) {
        this.setId(id);
        this.setVehicule_id(vehicule_id);
        this.setClient_id(client_id);
        this.setDebut(debut);
        this.setFin(fin);
        this.setDureeReservation();
    }

    public Reservation (long client_id, long vehicule_id, LocalDate debut, LocalDate fin) {
        this.setClient_id(client_id);
        this.setVehicule_id(vehicule_id);
        this.setDebut(debut);
        this.setFin(fin);
        this.setDureeReservation();
    }

    public Reservation (Client client, Vehicule vehicule, LocalDate debut, LocalDate fin) {
        this.setClient(client);
        this.setVehicule(vehicule);
        this.setClient_id(client.getId());
        this.setVehicule_id(vehicule.getId());
        this.setDebut(debut);
        this.setFin(fin);
        this.setDureeReservation();
    }

    public Reservation (long id ,Client client, Vehicule vehicule, LocalDate debut, LocalDate fin) {
        this.setId(id);
        this.setClient(client);
        this.setVehicule(vehicule);
        this.setClient_id(client.getId());
        this.setVehicule_id(vehicule.getId());
        this.setDebut(debut);
        this.setFin(fin);
        this.setDureeReservation();
    }

    public Reservation(long id) {
        this.setId(id);
    }

    @Override
    public String toString() {
        return "Reservation = " +
                "id=" + id +
                ", client id=" + client_id +
                ", vehicule id =" + vehicule_id +
                ", debut=" + debut +
                ", fin=" + fin +
                ", dureeReservation=" + dureeReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
