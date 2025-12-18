/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}
