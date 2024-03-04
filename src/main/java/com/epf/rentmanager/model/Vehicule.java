package com.epf.rentmanager.model;

public class Vehicule {
    private long id;
    private String constructeur, modele;
    private short nb_places;

    public long getId() {
        return id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public String getModele() {
        return modele;
    }

    public short getNb_places() {
        return nb_places;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setConstrueur(String constructeur) {
        this.constructeur = constructeur;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setNb_places(short nb_places) {
        this.nb_places = nb_places;
    }

    public Vehicule(long id, String constructeur, short nb_places){
        this.setId(id);
        this.setConstrueur(constructeur);
        this.setNb_places(nb_places);
    }
    public Vehicule(){}

    public Vehicule(long id, String constructeur, String modele, short nb_places){
        this.setId(id);
        this.setConstrueur(constructeur);
        this.setModele(modele);
        this.setNb_places(nb_places);
    }

    public Vehicule(String constructeur, String modele, short nb_places){
        this.setConstrueur(constructeur);
        this.setModele(modele);
        this.setNb_places(nb_places);
    }

    public Vehicule(String constructeur, short nb_places){
        this.setConstrueur(constructeur);
        this.setNb_places(nb_places);
    }

    @Override
    public String toString() {
        return "Vehicule = " +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nb_places='" + nb_places + '\'';
    }
}
