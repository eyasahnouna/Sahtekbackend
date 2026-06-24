package com.sahtek.sahtek.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class ConfigurationFirebase {

    @Value("${FIREBASE_CREDENTIALS:}")
    private String firebaseCredentialsJson;

    @Value("${firebase.service-account:firebase-service-account.json}")
    private String serviceAccountPath;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {

            InputStream serviceAccount;

            if (firebaseCredentialsJson != null && !firebaseCredentialsJson.isBlank()) {
                serviceAccount = new ByteArrayInputStream(
                        firebaseCredentialsJson.getBytes(StandardCharsets.UTF_8));
            } else {
                ClassPathResource classPathResource = new ClassPathResource(serviceAccountPath);
                if (classPathResource.exists()) {
                    serviceAccount = classPathResource.getInputStream();
                } else {
                    serviceAccount = new java.io.FileInputStream(serviceAccountPath);
                }
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
