package com.dwes.empleadosapi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Extrae el token JW de la cabecera Authoritation de la petición HTTP
 */
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.extractToken(request);

        if(this.tokenProvider.validateToken(token)){
            String username = this.tokenProvider.extractUsername(token);

            //UserDetails representa al usuario
            UserDetails user = this.userDetailsService.loadUserByUsername(username); //Carga el usuario de la base de datos

            //Información sobre el usuario que se acaba de autenticar
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities());

            //SecurityContext permite ver si hay un usuario logeado o no
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        //Reenviamos la petición a los siguientes filtros
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); //Quitamos el Bearer y nos quedamos con el token
        }
        return null;
    }
}
