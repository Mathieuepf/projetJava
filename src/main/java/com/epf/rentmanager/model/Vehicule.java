package com.epf.rentmanager.model;

public class Vehicule {
    private int id;
    private String construeur, modele;
    private short nb_places;

    public int getId() {
        return id;
    }

    public String getConstrueur() {
        return construeur;
    }

    public String getModele() {
        return modele;
    }

    public short getNb_places() {
        return nb_places;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setConstrueur(String construeur) {
        this.construeur = construeur;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setNb_places(short nb_places) {
        this.nb_places = nb_places;
    }

    @Override
    public String toString() {
        return "Vehicule = " +
                "id=" + id +
                ", construeur='" + construeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nb_places='" + nb_places + '\'';
    }
}
