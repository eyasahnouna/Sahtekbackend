package com.sahtek.sahtek.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

/**
 * Envoi d'emails via l'API HTTP Brevo (https://app.brevo.com).
 * SMTP bloqué par Render → on passe par l'API REST de Brevo (port 443).
 */
@Slf4j
@Service
public class EmailInvitationService {

    private final RestClient restClient;

    @Value("${brevo.api.key:}")
    private String apiKey;

    @Value("${brevo.from.email:}")
    private String fromEmail;

    @Value("${brevo.from.name:Sahtek App}")
    private String fromName;

    public EmailInvitationService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.brevo.com/v3")
                .build();
    }

    public void envoyerInvitation(String destinationEmail, String inviterEmail, String role) {
        log.info("[EMAIL-SERVICE] envoyerInvitation() via Brevo API");
        log.info("[EMAIL-SERVICE]   from    = '{} <{}>'", fromName, fromEmail);
        log.info("[EMAIL-SERVICE]   to      = {}", destinationEmail);
        log.info("[EMAIL-SERVICE]   apiKey  = {}", apiKey.isBlank() ? "(non configuré)" : "***défini***");

        if (apiKey.isBlank()) {
            log.error("[EMAIL-SERVICE] ✗ BREVO_API_KEY non configuré");
            throw new RuntimeException("Service email non configuré : BREVO_API_KEY manquant dans les variables Render");
        }
        if (fromEmail.isBlank()) {
            log.error("[EMAIL-SERVICE] ✗ BREVO_FROM_EMAIL non configuré");
            throw new RuntimeException("Service email non configuré : BREVO_FROM_EMAIL manquant dans les variables Render");
        }

        String roleTexte = role.equals("doctor") ? "Médecin" : "Proche (Lecture seule)";

        // Structure JSON attendue par Brevo
        Map<String, Object> payload = Map.of(
                "sender",      Map.of("name", fromName, "email", fromEmail),
                "to",          List.of(Map.of("email", destinationEmail)),
                "subject",     "Invitation à rejoindre Sahtek",
                "htmlContent", construireCorps(inviterEmail, destinationEmail, roleTexte)
        );

        log.info("[EMAIL-SERVICE] → POST https://api.brevo.com/v3/smtp/email");

        try {
            var response = restClient.post()
                    .uri("/smtp/email")
                    .header("api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .onStatus(status -> !status.is2xxSuccessful(), (req, res) -> {
                        String body = new String(res.getBody().readAllBytes());
                        log.error("[EMAIL-SERVICE] ✗ Brevo erreur {} : {}", res.getStatusCode(), body);
                        throw new RuntimeException("Brevo API erreur " + res.getStatusCode() + " : " + body);
                    })
                    .toBodilessEntity();

            log.info("[EMAIL-SERVICE] ✓ Email envoyé via Brevo — status={}", response.getStatusCode());

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("[EMAIL-SERVICE] ✗ Exception inattendue : {}", e.getMessage(), e);
            throw new RuntimeException("Impossible d'envoyer l'email : " + e.getMessage());
        }
    }

    private String construireCorps(String inviterEmail, String destinationEmail, String roleTexte) {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px;">
              <div style="max-width: 480px; margin: auto; background: white; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 16px rgba(0,0,0,0.08);">
                <div style="background: #4C9E8E; padding: 32px 24px; text-align: center;">
                  <h1 style="color: white; margin: 0; font-size: 24px;">Sahtek</h1>
                  <p style="color: rgba(255,255,255,0.85); margin: 8px 0 0;">Suivi médical partag&#233;</p>
                </div>
                <div style="padding: 28px 24px;">
                  <h2 style="color: #2d2d2d; margin-top: 0;">Vous avez re&#231;u une invitation</h2>
                  <p style="color: #555; line-height: 1.6;">
                    <strong>%s</strong> vous invite &#224; acc&#233;der &#224; ses donn&#233;es m&#233;dicales sur Sahtek.
                  </p>
                  <div style="background: #f0f9f7; border-left: 4px solid #4C9E8E; border-radius: 4px; padding: 12px 16px; margin: 20px 0;">
                    <strong style="color: #4C9E8E;">R&#244;le attribu&#233; :</strong>
                    <span style="color: #333; margin-left: 8px;">%s</span>
                  </div>
                  <p style="color: #555; line-height: 1.6;">Pour accepter cette invitation :</p>
                  <ol style="color: #555; line-height: 2;">
                    <li>T&#233;l&#233;chargez l&#39;application <strong>Sahtek</strong></li>
                    <li>
                      <strong>Cr&#233;ez un compte</strong> avec <strong>%s</strong>
                      <br/><span style="color:#888;font-size:13px;">(si vous avez d&#233;j&#224; un compte, connectez-vous simplement)</span>
                    </li>
                    <li>Allez dans <strong>Acc&#232;s partag&#233; &#8594; Invitations</strong></li>
                    <li>Appuyez sur <strong>Accepter</strong> et choisissez un mot de passe</li>
                  </ol>
                </div>
                <div style="background: #f5f5f5; padding: 16px 24px; text-align: center;">
                  <p style="color: #999; font-size: 12px; margin: 0;">
                    Si vous ne connaissez pas cet utilisateur, ignorez cet email.
                  </p>
                </div>
              </div>
            </body>
            </html>
            """.formatted(inviterEmail, roleTexte, destinationEmail);
    }
}
