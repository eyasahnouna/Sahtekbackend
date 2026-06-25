package com.sahtek.sahtek.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailInvitationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromEmail;

    @Value("${smtp.from.name:Sahtek App}")
    private String fromName;

    public void envoyerInvitation(String destinationEmail, String inviterEmail, String role) {
        log.info("[EMAIL-SERVICE] envoyerInvitation() appelé");
        log.info("[EMAIL-SERVICE]   fromEmail = '{}'", fromEmail);
        log.info("[EMAIL-SERVICE]   fromName  = '{}'", fromName);
        log.info("[EMAIL-SERVICE]   SMTP_HOST env = {}", System.getenv("SMTP_HOST"));
        log.info("[EMAIL-SERVICE]   SMTP_USERNAME env = {}", System.getenv("SMTP_USERNAME") != null ? "***défini***" : "(null)");
        log.info("[EMAIL-SERVICE]   SMTP_PASSWORD env = {}", System.getenv("SMTP_PASSWORD") != null ? "***défini***" : "(null)");

        if (fromEmail == null || fromEmail.isBlank()) {
            log.error("[EMAIL-SERVICE] ✗ SMTP_USERNAME non configuré — abandon");
            throw new RuntimeException("SMTP non configuré : SMTP_USERNAME manquant dans les variables d'environnement Render");
        }

        String roleTexte = role.equals("doctor") ? "Médecin" : "Proche (Lecture seule)";

        log.info("[EMAIL-SERVICE] → Connexion SMTP et création du message...");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, fromName);
            helper.setTo(destinationEmail);
            helper.setSubject("Invitation à rejoindre Sahtek");
            helper.setText(construireCorps(inviterEmail, destinationEmail, roleTexte), true);

            log.info("[EMAIL-SERVICE] → Envoi SMTP vers {}...", destinationEmail);
            mailSender.send(message);
            log.info("[EMAIL-SERVICE] ✓ Message remis au serveur SMTP");
        } catch (MessagingException | MailException | UnsupportedEncodingException e) {
            log.error("[EMAIL-SERVICE] ✗ Erreur SMTP : {}", e.getMessage(), e);
            throw new RuntimeException("Impossible d'envoyer l'email : " + e.getMessage());
        }
    }

    private String construireCorps(String inviterEmail, String destinationEmail, String roleTexte) {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px;">
              <div style="max-width: 480px; margin: auto; background: white; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 16px rgba(0,0,0,0.08);">
                <div style="background: #4C9E8E; padding: 32px 24px; text-align: center;">
                  <h1 style="color: white; margin: 0; font-size: 24px;">❤️ Sahtek</h1>
                  <p style="color: rgba(255,255,255,0.85); margin: 8px 0 0;">Suivi médical partagé</p>
                </div>
                <div style="padding: 28px 24px;">
                  <h2 style="color: #2d2d2d; margin-top: 0;">Vous avez reçu une invitation</h2>
                  <p style="color: #555; line-height: 1.6;">
                    <strong>%s</strong> vous invite à accéder à ses données médicales sur Sahtek.
                  </p>
                  <div style="background: #f0f9f7; border-left: 4px solid #4C9E8E; border-radius: 4px; padding: 12px 16px; margin: 20px 0;">
                    <strong style="color: #4C9E8E;">Rôle attribué :</strong>
                    <span style="color: #333; margin-left: 8px;">%s</span>
                  </div>
                  <p style="color: #555; line-height: 1.6;">Pour accepter cette invitation :</p>
                  <ol style="color: #555; line-height: 2;">
                    <li>Téléchargez l'application <strong>Sahtek</strong></li>
                    <li>Connectez-vous avec <strong>%s</strong></li>
                    <li>Allez dans <strong>Accès partagé → Invitations</strong></li>
                    <li>Appuyez sur <strong>Accepter</strong></li>
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
