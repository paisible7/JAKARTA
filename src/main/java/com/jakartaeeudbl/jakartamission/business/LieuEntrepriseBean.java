/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package com.jakartaeeudbl.jakartamission.business;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

import com.jakartaeeudbl.jakartamission.entities.Lieu;

/**
 *
 * @author paisible
 */
@Stateless
@LocalBean
public class LieuEntrepriseBean {
    
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void ajouterLieuEntreprise(String nom, String description, double latitude, double longitude) {
        Lieu lieu = new Lieu(nom, description, latitude, longitude);
        em.persist(lieu);
    }

    public List<Lieu> listerTousLesLieux() {
        return em.createQuery("SELECT L FROM Lieu L", Lieu.class).getResultList();
    }

    @Transactional
    public void supprimerLieu(int id) {
        Lieu lieu = em.find(Lieu.class, id);
        if (lieu != null) {
            em.remove(lieu);
        }
    }

    public Lieu trouverLieuParId(int id) {
        return em.find(Lieu.class, id);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
