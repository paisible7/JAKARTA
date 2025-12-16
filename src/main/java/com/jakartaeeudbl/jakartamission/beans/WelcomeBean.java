/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jakartaeeudbl.jakartamission.entities.Lieu;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 *
 * @author paisible
 */

@RequestScoped
@Named("welcomeBean")
public class WelcomeBean {
    private String name;    
    private String message;
    private Double usd;
    private Double ird;
    private String usdToIrdMessage;
    private String irdToUsdMessage;
    private static final double RATE = 16669.00;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMessage(){
        return message;
    }

    public Double getUsd() {
        return usd;
    }   

    public void setUsd(Double usd) {
        this.usd = usd;
    }

    public Double getIrd() {
        return ird;
    }

    public void setIrd(Double ird) {
        this.ird = ird;
    }

    public String getUsdToIrdMessage() {
        return usdToIrdMessage;
    }

    public String getIrdToUsdMessage() {
        return irdToUsdMessage;
    }

    public void afficher(){
        this.message = "Bienvenue, en indonésie cher " + this.name;
    }


    public void convertUsdToIrd(){
        if(this.usd == null){
            this.usdToIrdMessage = null;
            return; 
        }

        double result = this.usd * RATE;
        this.usdToIrdMessage = String.format("Montant en IRD = %.2f",result);
    }

    public void convertIrdToUsd(){
        if(this.ird == null){
            this.irdToUsdMessage = null;
            return;
        }
        double result = this.ird * RATE;
        this.irdToUsdMessage = String.format("Montant en USD = %.2f",result);
    }



    // Lieux 


    private String nameLieu;
    private String description;
    private Double latitude;
    private Double longitude;

    // simple in-memory storage for demo purposes
    private static final List<Lieu> LIEUX = Collections.synchronizedList(new ArrayList<>());

    public String addLieu() {
        Lieu l = new Lieu(nameLieu, description, latitude, longitude);
        LIEUX.add(l);
        // clear fields after add
        this.nameLieu = null;
        this.description = null;
        this.latitude = null;
        this.longitude = null;
        // stay on the same page (redirect to avoid duplicate submission)
        return "lieu?faces-redirect=true";
    }

    public List<Lieu> getLieux() {
        return LIEUX;
    }

    // getters / setters for form binding
    public String getNameLieu() { return nameLieu; }
    public void setNameLieu(String nameLieu) { this.nameLieu = nameLieu; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
