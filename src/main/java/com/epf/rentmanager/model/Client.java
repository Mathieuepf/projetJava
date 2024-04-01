package com.epf.rentmanager.model;

import com.sun.org.apache.xpath.internal.operations.Equals;

import java.time.LocalDate;
import java.util.Objects;

public class Client {
    private long id;
    private String nom, prenom, email;
    private LocalDate naissance;

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getPrenom() {
        return prenom;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Client(long id, String nom, String prenom, String email, LocalDate naissance){
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setNaissance(naissance);
    }
    public Client(String nom, String prenom, String email, LocalDate naissance) {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setNaissance(naissance);
    }

    public Client(String nom, String prenom, String email) {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
    }

    public Client(){}

    public Client(long id) {
        this.setId(id);
    }
    @Override
    public String toString() {
        return "Client : " +
                "id = " + id +
                ", nom = '" + nom + '\'' +
                ", prenom = '" + prenom + '\'' +
                ", email = '" + email + '\'' +
                ", naissance = " + naissance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return getId() == client.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
