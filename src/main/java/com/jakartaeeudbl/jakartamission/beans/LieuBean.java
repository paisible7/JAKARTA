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
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author paisible
 */
@Named(value = "lieuBean")
@ViewScoped
public class LieuBean implements Serializable{

    private String nom;
    private String description;
    private Double longitude;
    private Double latitude;
    
    private Integer selectedLieu; // ID du lieu sélectionné pour la météo
    private String weatherMessage;
    
    private Lieu lieuSelectionne; // Pour l'édition/suppression

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    
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
            latitude = null;
            longitude = null;
        }
    }

    public void fetchWeatherMessage(Lieu l) {
        if (l != null) {
            String serviceURL = "http://localhost:8080/jakartamission/webapi/JakartaWeather?latitude="
                    + l.getLatitude() + "&longitude=" + l.getLongitude();
            try {
                Client client = ClientBuilder.newClient();
                String response = client.target(serviceURL)
                        .request(MediaType.TEXT_PLAIN)
                        .get(String.class);
                this.weatherMessage = response;
            } catch (Exception e) {
                this.weatherMessage = "Service météo indisponible.";
            }
        }
    }

    public void updateWeatherMessage(AjaxBehaviorEvent event) {
        if (selectedLieu != null) {
            Lieu lieu = lieuEntrepriseBean.getLieuById(selectedLieu);
            this.fetchWeatherMessage(lieu);
        }
    }

    public Integer getSelectedLieu() {
        return selectedLieu;
    }

    public void setSelectedLieu(Integer selectedLieu) {
        this.selectedLieu = selectedLieu;
    }

    public String getWeatherMessage() {
        return weatherMessage;
    }

    public void setWeatherMessage(String weatherMessage) {
        this.weatherMessage = weatherMessage;
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
            nom,
            description,
            latitude,
            longitude
        );
        lieuSelectionne = null;
        nom = null;
        description = null;
        latitude = null;
        longitude = null;  
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
            latitude = null;
            longitude = null;
        }
    }
}