package com.sahtek.sahtek.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    private static final String GEMINI_BASE_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s";

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.model:gemini-2.0-flash}")
    private String model;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeminiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Envoie l'image base64 à Gemini Vision et retourne le JSON d'analyse nutritionnelle.
     */
    public String analyserImage(String imageBase64, String hint) {
        String url = String.format(GEMINI_BASE_URL, model, apiKey);

        String imageData = nettoyerBase64(imageBase64);
        String mimeType = detecterMimeType(imageData);

        String corps = construireRequete(imageData, mimeType, hint);

        HttpHeaders entetes = new HttpHeaders();
        entetes.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requete = new HttpEntity<>(corps, entetes);

        try {
            ResponseEntity<String> reponse = restTemplate.postForEntity(url, requete, String.class);
            return extraireTexteReponse(reponse.getBody());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new RuntimeException("Clé Gemini API invalide ou non autorisée");
            }
            if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                throw new RuntimeException("Quota Gemini API dépassé, réessayez plus tard");
            }
            throw new RuntimeException("Erreur Gemini API (" + e.getStatusCode() + ")");
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Erreur serveur Gemini (" + e.getStatusCode() + ")");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de contacter Gemini API : " + e.getMessage());
        }
    }

    private String construireRequete(String imageData, String mimeType, String hint) {
        try {
            ObjectNode racine = objectMapper.createObjectNode();
            ArrayNode contents = objectMapper.createArrayNode();
            ObjectNode content = objectMapper.createObjectNode();
            ArrayNode parts = objectMapper.createArrayNode();

            // Partie texte — prompt nutritionnel
            ObjectNode partiTexte = objectMapper.createObjectNode();
            partiTexte.put("text", construirePrompt(hint));
            parts.add(partiTexte);

            // Partie image en base64
            ObjectNode partiImage = objectMapper.createObjectNode();
            ObjectNode inlineData = objectMapper.createObjectNode();
            inlineData.put("mimeType", mimeType);
            inlineData.put("data", imageData);
            partiImage.set("inlineData", inlineData);
            parts.add(partiImage);

            content.set("parts", parts);
            contents.add(content);
            racine.set("contents", contents);

            // Config : forcer une réponse JSON structurée
            ObjectNode generationConfig = objectMapper.createObjectNode();
            generationConfig.put("temperature", 0.1);
            generationConfig.put("maxOutputTokens", 1024);
            generationConfig.put("responseMimeType", "application/json");
            racine.set("generationConfig", generationConfig);

            return objectMapper.writeValueAsString(racine);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la construction de la requête Gemini");
        }
    }

    private String construirePrompt(String hint) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Tu es un expert en nutrition pour diabétiques. ");
        prompt.append("Analyse cette image de repas et identifie tous les aliments visibles. ");

        if (hint != null && !hint.isBlank()) {
            prompt.append("Information complémentaire : ").append(hint.trim()).append(". ");
        }

        prompt.append("Pour chaque aliment, estime le poids en grammes et les glucides. ");
        prompt.append("Réponds UNIQUEMENT avec un objet JSON valide dans ce format exact : ");
        prompt.append("{");
        prompt.append("\"foods\":[{");
        prompt.append("\"name\":\"nom de l'aliment\",");
        prompt.append("\"estimated_grams\":100.0,");
        prompt.append("\"carbs_g\":20.0,");
        prompt.append("\"confidence\":0.9");
        prompt.append("}],");
        prompt.append("\"total_carbs_g\":20.0,");
        prompt.append("\"notes\":\"observations nutritionnelles\"");
        prompt.append("}");

        return prompt.toString();
    }

    private String extraireTexteReponse(String corpsReponse) {
        try {
            JsonNode racine = objectMapper.readTree(corpsReponse);

            // Vérifier s'il y a un message d'erreur dans la réponse
            if (racine.has("error")) {
                String messageErreur = racine.path("error").path("message").asText("Erreur inconnue");
                throw new RuntimeException("Erreur Gemini : " + messageErreur);
            }

            JsonNode candidates = racine.path("candidates");
            if (!candidates.isArray() || candidates.isEmpty()) {
                throw new RuntimeException("Gemini n'a retourné aucun résultat");
            }

            JsonNode premier = candidates.get(0);

            // Vérifier la raison de fin de génération
            String finishReason = premier.path("finishReason").asText("");
            if ("SAFETY".equals(finishReason)) {
                throw new RuntimeException("Contenu bloqué par les filtres de sécurité Gemini");
            }

            String texte = premier
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            if (texte == null || texte.isBlank()) {
                throw new RuntimeException("Réponse Gemini vide");
            }

            return texte;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Impossible de lire la réponse Gemini : " + e.getMessage());
        }
    }

    /**
     * Supprime le préfixe data URL si présent (ex: "data:image/jpeg;base64,...").
     * Flutter peut envoyer les deux formats.
     */
    private String nettoyerBase64(String base64) {
        if (base64 != null && base64.startsWith("data:")) {
            int virgule = base64.indexOf(',');
            if (virgule > 0) {
                return base64.substring(virgule + 1).trim();
            }
        }
        return base64 != null ? base64.trim() : "";
    }

    /**
     * Détecte le type MIME à partir des premiers octets décodés.
     */
    private String detecterMimeType(String base64) {
        try {
            // Décoder seulement les premiers caractères pour détecter le type
            String echantillon = base64.substring(0, Math.min(16, base64.length()));
            byte[] octets = java.util.Base64.getDecoder().decode(echantillon);

            if (octets.length >= 2 && octets[0] == (byte) 0xFF && octets[1] == (byte) 0xD8) {
                return "image/jpeg";
            }
            if (octets.length >= 4 && octets[0] == (byte) 0x89 && octets[1] == (byte) 0x50
                    && octets[2] == (byte) 0x4E && octets[3] == (byte) 0x47) {
                return "image/png";
            }
            if (octets.length >= 6 && octets[0] == 'G' && octets[1] == 'I' && octets[2] == 'F') {
                return "image/gif";
            }
        } catch (Exception ignored) {
            // En cas d'erreur de décodage, utiliser JPEG par défaut
        }
        return "image/jpeg";
    }
}
