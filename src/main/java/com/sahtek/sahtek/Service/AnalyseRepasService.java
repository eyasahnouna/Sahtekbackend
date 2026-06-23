package com.sahtek.sahtek.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahtek.sahtek.Dto.Requete.RequeteAnalyseRepas;
import com.sahtek.sahtek.Dto.Reponse.ReponseAlimentaire;
import com.sahtek.sahtek.Dto.Reponse.ReponseAnalyseRepas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyseRepasService {

    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;

    public ReponseAnalyseRepas analyser(RequeteAnalyseRepas requete) {
        String jsonGemini = geminiService.analyserImage(requete.getImageBase64(), requete.getHint());
        return parserReponseGemini(jsonGemini);
    }

    private ReponseAnalyseRepas parserReponseGemini(String json) {
        try {
            JsonNode racine = objectMapper.readTree(json);

            List<ReponseAlimentaire> aliments = new ArrayList<>();
            JsonNode alimentsNode = racine.path("foods");

            if (alimentsNode.isArray()) {
                for (JsonNode noeud : alimentsNode) {
                    aliments.add(ReponseAlimentaire.builder()
                            .name(noeud.path("name").asText("Aliment inconnu"))
                            .estimatedGrams(noeud.path("estimated_grams").asDouble(0.0))
                            .carbsG(noeud.path("carbs_g").asDouble(0.0))
                            .confidence(noeud.path("confidence").asDouble(0.5))
                            .build());
                }
            }

            // Utiliser total_carbs_g de Gemini ou recalculer depuis les aliments
            double totalGlucidesG = racine.path("total_carbs_g").asDouble(0.0);
            if (totalGlucidesG == 0.0 && !aliments.isEmpty()) {
                totalGlucidesG = aliments.stream()
                        .mapToDouble(ReponseAlimentaire::getCarbsG)
                        .sum();
            }

            return ReponseAnalyseRepas.builder()
                    .foods(aliments)
                    .totalCarbsG(Math.round(totalGlucidesG * 10.0) / 10.0)
                    .notes(racine.path("notes").asText(""))
                    .source("Gemini Vision API")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Impossible de parser la réponse nutritionnelle : " + e.getMessage());
        }
    }
}
