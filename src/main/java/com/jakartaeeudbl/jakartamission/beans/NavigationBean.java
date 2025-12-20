/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 *
 * @author paisible
 */

@Named(value = "navigationController")
@RequestScoped
public class NavigationBean {
    public void voirApropos(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("pages/a_propos.xhtml"); 
        } catch (Exception e) {
            e.printStackTrace();    
        }
    }
}
