package com.jakartaeeudbl.jakartamission.beans;

import com.jakartaeeudbl.jakartamission.business.LieuEntrepriseBean;
import com.jakartaeeudbl.jakartamission.business.VisiteEntrepriseBean;
import com.jakartaeeudbl.jakartamission.entities.Lieu;
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import com.jakartaeeudbl.jakartamission.entities.Visite;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class VisiteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private VisiteEntrepriseBean visiteEntrepriseBean;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    @Inject
    private ProfileBean profileBean;

    private int selectedLieuId;
    private String description;
    private String message;

    public String afficherFormulaireVisite() {
        // Redirige vers la page de visite
        return "visite?faces-redirect=true";
    }

    public String enregistrerVisite() {
        Utilisateur utilisateur = profileBean.getUtilisateurConnecte();
        Lieu lieu = lieuEntrepriseBean.getLieuById(selectedLieuId);

        if (utilisateur != null && lieu != null) {
            Visite visite = new Visite(description, lieu, utilisateur);
            visiteEntrepriseBean.enregistrerVisite(visite);
            return "home?faces-redirect=true";
        } else {
            this.message = "Erreur : Utilisateur ou lieu introuvable.";
            return null;
        }
    }

    // Getters et Setters
    public int getSelectedLieuId() {
        return selectedLieuId;
    }

    public void setSelectedLieuId(int selectedLieuId) {
        this.selectedLieuId = selectedLieuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
