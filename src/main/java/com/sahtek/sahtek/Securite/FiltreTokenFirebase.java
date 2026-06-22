package com.sahtek.sahtek.Securite;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FiltreTokenFirebase extends OncePerRequestFilter {

    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest requete,
                                    HttpServletResponse reponse,
                                    FilterChain chaine)
            throws ServletException, IOException {

        String entete = requete.getHeader("Authorization");

        if (entete != null && entete.startsWith("Bearer ")) {
            String token = entete.substring(7);
            try {
                FirebaseToken tokenDecode = firebaseAuth.verifyIdToken(token);
                // auth.getName() retourne l'UID Firebase dans tous les controllers
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                tokenDecode.getUid(), null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (FirebaseAuthException e) {
                reponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Firebase invalide");
                return;
            }
        }

        chaine.doFilter(requete, reponse);
    }
}
