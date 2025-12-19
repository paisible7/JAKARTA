/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jakartaeeudbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author paisible
 */

@RequestScoped
@Named("welcomeBean")
public class WelcomeBean {

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    private String name;    
    private String message;
    private Double usd;
    private Double ird;
    private String usdToIrdMessage;
    private String irdToUsdMessage;
    private String email;
    private String password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void afficher(){
        this.message = "Bienvenue, en indonésie cher " + this.name;
    }

    public String sAuthentifier() {
        Utilisateur utilisateur = utilisateurEntrepriseBean.authentifier(email, password);
        FacesContext context = FacesContext.getCurrentInstance();

        if (utilisateur != null) {
            this.name = utilisateur.getUsername();
            return "home?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email ou mot de passe incorrect", null));
            return null;
        }
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
