package com.sahtek.sahtek.Securite;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FiltreRateLimit extends OncePerRequestFilter {

    // Un bucket par adresse IP
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket creerBucket() {
        // 60 requêtes par minute par IP
        Bandwidth limite = Bandwidth.builder()
                .capacity(60)
                .refillIntervally(60, Duration.ofMinutes(1))
                .build();
        return Bucket.builder().addLimit(limite).build();
    }

    private Bucket obtenirBucket(String ip) {
        return buckets.computeIfAbsent(ip, k -> creerBucket());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest requete,
                                    HttpServletResponse reponse,
                                    FilterChain chaine)
            throws ServletException, IOException {

        String ip = obtenirIp(requete);
        Bucket bucket = obtenirBucket(ip);

        if (bucket.tryConsume(1)) {
            chaine.doFilter(requete, reponse);
        } else {
            reponse.setStatus(429);
            reponse.setContentType("application/json");
            reponse.getWriter().write(
                "{\"erreur\": \"Trop de requêtes. Réessayez dans une minute.\"}"
            );
        }
    }

    private String obtenirIp(HttpServletRequest requete) {
        String ip = requete.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank()) {
            ip = requete.getRemoteAddr();
        }
        // Si plusieurs IPs (proxy chain), prendre la première
        return ip.split(",")[0].trim();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest requete) {
        // Appliquer uniquement sur les endpoints API
        return !requete.getRequestURI().startsWith("/api/");
    }
}
