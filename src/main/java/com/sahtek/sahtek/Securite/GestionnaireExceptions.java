package com.sahtek.sahtek.Securite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    // Accès refusé → 403 sans détails internes
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> gererRuntime(RuntimeException ex) {
        String message = ex.getMessage();

        if (message != null && message.equals("Accès refusé")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erreur", "Accès refusé"));
        }

        if (message != null && message.equals("Invitation déjà envoyée")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("erreur", message));
        }

        // Toute autre erreur → 500 sans exposer la cause interne
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("erreur", "Une erreur est survenue"));
    }

    // Erreur générique → 500 sans stacktrace
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> gererException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("erreur", "Une erreur est survenue"));
    }
}
