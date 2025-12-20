package com.jakartaeeudbl.jakartamission.business;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtre de contrôle de session pour protéger les pages de l'application.
 */
@WebFilter("*.xhtml")
public class SessionControlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // Vérifier si l'utilisateur est connecté en vérifiant la présence de l'attribut de session "utilisateur"
        // (clé que nous avons définie dans WelcomeBean)
        String user = (String) httpRequest.getSession().getAttribute("utilisateur");

        // Autoriser l'accès à la page de connexion et d'inscription sans être connecté
        boolean isLoginPage = requestURI.endsWith("index.xhtml");
        boolean isRegisterPage = requestURI.endsWith("ajoute_utilisateur.xhtml");
        boolean isResource = requestURI.contains("jakarta.faces.resource");

        if (user == null && !isLoginPage && !isRegisterPage && !isResource) {
            // L'utilisateur n'est pas connecté et tente d'accéder à une page protégée
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.xhtml");
        } else {
            // L'utilisateur est connecté ou accède à une page publique
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Nettoyage du filtre
    }
}
