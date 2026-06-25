package com.sahtek.sahtek.Securite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GestionnaireExceptions {

    // Erreurs de validation (@Valid) → 400 avec liste des champs invalides
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> gererValidation(
            MethodArgumentNotValidException ex) {

        List<String> erreurs = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .toList();

        Map<String, Object> corps = new HashMap<>();
        corps.put("statut", 400);
        corps.put("erreurs", erreurs);
        return ResponseEntity.badRequest().body(corps);
    }

    // Timeout ou réseau indisponible → 503
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Map<String, String>> gererReseau(ResourceAccessException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("erreur", "Service d'analyse temporairement indisponible"));
    }

    // Erreurs métier connues → statuts HTTP précis
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> gererRuntime(RuntimeException ex) {
        ex.printStackTrace(); // Added to see errors in Render logs
        String message = ex.getMessage();

        if (message == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Une erreur est survenue"));
        }

        if (message.equals("Accès refusé")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erreur", "Accès refusé"));
        }

        if (message.equals("Invitation déjà envoyée")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("erreur", message));
        }

        if (message.contains("Clé Gemini API invalide") || message.contains("non autorisée")) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("erreur", "Service d'analyse non configuré"));
        }

        if (message.contains("Quota Gemini API dépassé")) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("erreur", "Service d'analyse temporairement indisponible"));
        }

        // Toute autre erreur → 500 sans exposer la cause interne
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("erreur", "Une erreur est survenue"));
    }

    // Erreur générique → 500 sans stacktrace
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> gererException(Exception ex) {
        ex.printStackTrace(); // Added to see errors in Render logs
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("erreur", "Une erreur est survenue"));
    }
}
