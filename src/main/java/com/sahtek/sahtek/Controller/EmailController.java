package com.sahtek.sahtek.Controller;

import com.sahtek.sahtek.Dto.Requete.RequeteEmailInvitation;
import com.sahtek.sahtek.Service.EmailInvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailInvitationService emailService;

    @PostMapping("/invitation")
    public ResponseEntity<Map<String, String>> envoyerInvitation(
            @Valid @RequestBody RequeteEmailInvitation requete) {

        log.info("[EMAIL] POST /api/email/invitation reçu");
        log.info("[EMAIL]   destination = {}", requete.getDestinationEmail());
        log.info("[EMAIL]   inviteur    = {}", requete.getInviterEmail());
        log.info("[EMAIL]   role        = {}", requete.getRole());

        try {
            emailService.envoyerInvitation(
                    requete.getDestinationEmail(),
                    requete.getInviterEmail(),
                    requete.getRole()
            );
            log.info("[EMAIL] ✓ envoi réussi vers {}", requete.getDestinationEmail());
            return ResponseEntity.ok(Map.of("message", "Email envoyé avec succès"));
        } catch (Exception e) {
            log.error("[EMAIL] ✗ ERREUR lors de l'envoi : {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /** Endpoint de test rapide — GET /api/email/test */
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        log.info("[EMAIL] GET /api/email/test — backend email opérationnel");
        return ResponseEntity.ok(Map.of(
            "status", "ok",
            "smtpHost", System.getenv().getOrDefault("SMTP_HOST", "smtp.gmail.com"),
            "smtpUser", System.getenv().getOrDefault("SMTP_USERNAME", "(non configuré)")
        ));
    }
}
