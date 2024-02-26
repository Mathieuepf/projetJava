package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
    private long id;
    private LocalDate debut, fin;
    private long client_id;
    private long vehicule_id;

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

    public Reservation (long id, long client_id, long vehicule_id, LocalDate debut, LocalDate fin) {
        this.setId(id);
        this.setVehicule_id(vehicule_id);
        this.setClient_id(client_id);
        this.setDebut(debut);
        this.setFin(fin);
    }

    public Reservation (long client_id, long vehicule_id, LocalDate debut, LocalDate fin) {
        this.setClient_id(client_id);
        this.setVehicule_id(vehicule_id);
        this.setDebut(debut);
        this.setFin(fin);
    }

    @Override
    public String toString() {
        return "Reservation = " +
                "id=" + id +
                ", client id=" + client_id +
                ", vehicule id =" + vehicule_id +
                ", debut=" + debut +
                ", fin=" + fin;
    }
}
