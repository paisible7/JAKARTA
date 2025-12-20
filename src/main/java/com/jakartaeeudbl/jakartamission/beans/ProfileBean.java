package com.jakartaeeudbl.jakartamission.beans;

import com.jakartaeeudbl.jakartamission.business.SessionManager;
import com.jakartaeeudbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Bean pour gérer le profil utilisateur, la déconnexion et la modification du mot de passe.
 */
@Named
@SessionScoped
public class ProfileBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    private Utilisateur utilisateurConnecte;
    private String description;
    private String ancienMotDePasse;
    private String nouveauMotDePasse;
    private String confirmMotDePasse;
    private boolean sectionMotDePasseVisible = false;

    @PostConstruct
    public void init() {
        chargerUtilisateurConnecte();
    }

    /**
     * Charge l'utilisateur connecté à partir de l'email stocké en session.
     */
    public void chargerUtilisateurConnecte() {
        String email = sessionManager.getValueFromSession("utilisateur");
        if (email != null) {
            utilisateurConnecte = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
            if (utilisateurConnecte != null) {
                this.description = utilisateurConnecte.getDescription();
            }
        }
    }

    /**
     * Vérifie si l'utilisateur est connecté.
     */
    public boolean isConnecte() {
        return utilisateurConnecte != null;
    }

    /**
     * Déconnecte l'utilisateur et redirige vers la page de login.
     */
    public String logout() {
        sessionManager.invalidateSession();
        utilisateurConnecte = null;
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * Modifie la description de l'utilisateur.
     */
    public void modifierDescription() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (utilisateurConnecte == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erreur", "Utilisateur non connecté"));
            return;
        }

        utilisateurConnecte.setDescription(description);
        utilisateurEntrepriseBean.modifierUtilisateur(utilisateurConnecte);
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
            "Succès", "Description mise à jour"));
    }

    /**
     * Modifie le mot de passe de l'utilisateur.
     */
    public void modifierMotDePasse() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (utilisateurConnecte == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erreur", "Utilisateur non connecté"));
            return;
        }

        // Vérifier que tous les champs sont remplis
        if (ancienMotDePasse == null || ancienMotDePasse.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erreur", "Veuillez saisir votre ancien mot de passe"));
            return;
        }

        if (nouveauMotDePasse == null || nouveauMotDePasse.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erreur", "Veuillez saisir un nouveau mot de passe"));
            return;
        }

        if (confirmMotDePasse == null || confirmMotDePasse.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erreur", "Veuillez confirmer votre nouveau mot de passe"));
            return;
        }

        // Vérifier que les nouveaux mots de passe correspondent
        if (!nouveauMotDePasse.equals(confirmMotDePasse)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erreur", "Les nouveaux mots de passe ne correspondent pas"));
            return;
        }

        // Vérifier l'ancien mot de passe
        if (!utilisateurEntrepriseBean.verifierMotDePasse(ancienMotDePasse, utilisateurConnecte.getPassword())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erreur", "L'ancien mot de passe est incorrect"));
            return;
        }

        try {
            // Mettre à jour le mot de passe
            utilisateurEntrepriseBean.modifierMotDePasse(utilisateurConnecte.getId(), nouveauMotDePasse);
            
            // Recharger l'utilisateur pour avoir le nouveau hash
            chargerUtilisateurConnecte();

            // Réinitialiser les champs
            ancienMotDePasse = "";
            nouveauMotDePasse = "";
            confirmMotDePasse = "";
            sectionMotDePasseVisible = false;

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Succès", "Mot de passe modifié avec succès"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erreur", "Une erreur est survenue lors de la modification du mot de passe"));
        }
    }

    /**
     * Enregistre toutes les modifications (description + mot de passe si rempli).
     */
    public void enregistrerModifications() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Modifier la description
        if (utilisateurConnecte != null && description != null) {
            utilisateurConnecte.setDescription(description);
            utilisateurEntrepriseBean.modifierUtilisateur(utilisateurConnecte);
        }

        // Modifier le mot de passe si les champs sont remplis
        if (ancienMotDePasse != null && !ancienMotDePasse.isEmpty() 
            && nouveauMotDePasse != null && !nouveauMotDePasse.isEmpty()) {
            
            if (!nouveauMotDePasse.equals(confirmMotDePasse)) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erreur", "Les nouveaux mots de passe ne correspondent pas"));
                return;
            }

            if (!utilisateurEntrepriseBean.verifierMotDePasse(ancienMotDePasse, utilisateurConnecte.getPassword())) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erreur", "L'ancien mot de passe est incorrect"));
                return;
            }

            utilisateurEntrepriseBean.modifierMotDePasse(utilisateurConnecte.getId(), nouveauMotDePasse);
            chargerUtilisateurConnecte();

            // Réinitialiser les champs mot de passe
            ancienMotDePasse = "";
            nouveauMotDePasse = "";
            confirmMotDePasse = "";
        }

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
            "Succès", "Profil mis à jour avec succès"));
    }

    public void toggleSectionMotDePasse() {
        this.sectionMotDePasseVisible = !this.sectionMotDePasseVisible;
    }

    // Getters et Setters
    public Utilisateur getUtilisateurConnecte() {
        if (utilisateurConnecte == null) {
            chargerUtilisateurConnecte();
        }
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }

    public void setAncienMotDePasse(String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }

    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

    public String getConfirmMotDePasse() {
        return confirmMotDePasse;
    }

    public void setConfirmMotDePasse(String confirmMotDePasse) {
        this.confirmMotDePasse = confirmMotDePasse;
    }

    public boolean isSectionMotDePasseVisible() {
        return sectionMotDePasseVisible;
    }

    public void setSectionMotDePasseVisible(boolean sectionMotDePasseVisible) {
        this.sectionMotDePasseVisible = sectionMotDePasseVisible;
    }
}
