package com.sahtek.sahtek.Controller;

import com.sahtek.sahtek.Dto.Requete.RequeteRegistreJournalier;
import com.sahtek.sahtek.Dto.Reponse.ReponseRegistreJournalier;
import com.sahtek.sahtek.Service.RegistreJournalierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registre")
@RequiredArgsConstructor
public class RegistreJournalierController {

    private final RegistreJournalierService service;

    // GET /api/registre
    @GetMapping
    public List<ReponseRegistreJournalier> obtenirTout(Authentication auth) {
        return service.obtenirTout(auth.getName());
    }

    // POST /api/registre  → upsert par cleDate
    @PostMapping
    public ReponseRegistreJournalier sauvegarder(@Valid @RequestBody RequeteRegistreJournalier requete,
                                                  Authentication auth) {
        return service.sauvegarder(auth.getName(), requete);
    }
}
