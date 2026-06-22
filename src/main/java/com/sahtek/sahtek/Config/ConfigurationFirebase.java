package com.sahtek.sahtek.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class ConfigurationFirebase {

    // Chemin configurable dans application.properties
    // Par défaut : src/main/resources/firebase-service-account.json
    @Value("${firebase.service-account:firebase-service-account.json}")
    private String serviceAccountPath;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {

            InputStream serviceAccount;

            // Essayer d'abord depuis le classpath (src/main/resources/)
            ClassPathResource classPathResource = new ClassPathResource(serviceAccountPath);
            if (classPathResource.exists()) {
                serviceAccount = classPathResource.getInputStream();
            } else {
                // Sinon chercher comme chemin absolu ou relatif au répertoire courant
                serviceAccount = new java.io.FileInputStream(serviceAccountPath);
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            return FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp app) {
        return FirebaseAuth.getInstance(app);
    }
}
