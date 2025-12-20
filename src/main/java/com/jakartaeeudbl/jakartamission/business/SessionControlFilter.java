package com.jakartaeeudbl.jakartamission.business;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtre de contrôle de session pour protéger les pages de l'application.
 */
@WebFilter("/pages/*")
public class SessionControlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Vérifier si l'utilisateur est connecté
        String user = (String) httpRequest.getSession().getAttribute("utilisateur");

        if (user == null) {
            // L'utilisateur n'est pas connecté, rediriger vers la page de connexion
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.xhtml");
        } else {
            // L'utilisateur est connecté, laisser passer
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Nettoyage du filtre
    }
}
