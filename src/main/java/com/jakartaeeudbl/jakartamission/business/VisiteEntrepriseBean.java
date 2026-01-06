package com.jakartaeeudbl.jakartamission.business;

import com.jakartaeeudbl.jakartamission.entities.Visite;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class VisiteEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    public void enregistrerVisite(Visite visite) {
        em.persist(visite);
    }

    public List<Visite> listerToutesLesVisites() {
        return em.createQuery("SELECT v FROM Visite v", Visite.class).getResultList();
    }
}
