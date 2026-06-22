package com.sahtek.sahtek.Service;

import com.sahtek.sahtek.Dto.Requete.RequeteAnalyseRepas;
import com.sahtek.sahtek.Dto.Reponse.ReponseAnalyseRepas;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyseRepasService {

    // Intégrer ici votre client Gemini Vision API
    // La requête Flutter envoie une image en base64 + un indice facultatif
    public ReponseAnalyseRepas analyser(RequeteAnalyseRepas requete) {

        // TODO : décoder base64 → appeler Gemini API → parser la réponse

        return ReponseAnalyseRepas.builder()
                .foods(List.of())
                .totalCarbsG(0.0)
                .notes("Analyse en cours de configuration")
                .source("Gemini Vision API")
                .build();
    }
}
