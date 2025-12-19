/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;

import java.io.Serializable;
import java.util.List;

import com.jakartaeeudbl.jakartamission.entities.Lieu;
import com.jakartaeeudbl.jakartamission.business.LieuEntrepriseBean;

/**
 *
 * @author paisible
 */
@Named(value = "lieuBean")
@ViewScoped
public class LieuBean implements Serializable{

    private String nom;
    private String description;
    private double longitude;
    private double latitude;
    
    private Lieu lieuSelectionne; // Pour l'édition/suppression

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    
    public Lieu getLieuSelectionne() { return lieuSelectionne; }
    public void setLieuSelectionne(Lieu lieu) { this.lieuSelectionne = lieu; }

    public List<Lieu> getLieux() { 
        return lieuEntrepriseBean.listerTousLesLieux(); 
        }

    public void ajouterLieu() {
        if (nom != null && !nom.isEmpty() && description != null && !description.isEmpty()) {
            lieuEntrepriseBean.ajouterLieuEntreprise(nom, description, latitude, longitude);
            // Nettoyer le formulaire après ajout
            nom = null;
            description = null;
            latitude = 0;
            longitude = 0;
        }
    }
    
    public void preparerModifier(Lieu lieu) {
        this.lieuSelectionne = lieu;
        this.nom = lieu.getNom();
        this.description = lieu.getDescription();
        this.latitude = lieu.getLatitude();
        this.longitude = lieu.getLongitude();
    }
    
   public void modifierLieu() {
    if (lieuSelectionne != null) {
        lieuEntrepriseBean.modifierLieu(
            lieuSelectionne.getId(),
            lieuSelectionne.getNom(),
            lieuSelectionne.getDescription(),
            lieuSelectionne.getLatitude(),
            lieuSelectionne.getLongitude()
        );
        lieuSelectionne = null;
    }
   }


    public void preparerSupprimer(Lieu l) {
    
    this.lieuSelectionne = l;
    }

    
    public void supprimerLieu() {
        if (lieuSelectionne != null) {
            lieuEntrepriseBean.supprimerLieu(lieuSelectionne.getId());
            // Nettoyer après suppression
            lieuSelectionne = null;
            nom = null;
            description = null;
            latitude = 0;
            longitude = 0;
        }
    }
}