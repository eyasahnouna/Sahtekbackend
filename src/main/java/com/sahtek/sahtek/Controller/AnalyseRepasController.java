package com.sahtek.sahtek.Controller;

import com.sahtek.sahtek.Dto.Requete.RequeteAnalyseRepas;
import com.sahtek.sahtek.Dto.Reponse.ReponseAnalyseRepas;
import com.sahtek.sahtek.Service.AnalyseRepasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class AnalyseRepasController {

    private final AnalyseRepasService service;

    // POST /api/meals/analyze  ← identique à AiService Flutter
    @PostMapping("/analyze")
    public ReponseAnalyseRepas analyser(@Valid @RequestBody RequeteAnalyseRepas requete) {
        return service.analyser(requete);
    }
}
