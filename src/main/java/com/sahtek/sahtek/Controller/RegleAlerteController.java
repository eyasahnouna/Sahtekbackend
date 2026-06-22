package com.sahtek.sahtek.Controller;

import com.sahtek.sahtek.Dto.Requete.RequeteRegleAlerte;
import com.sahtek.sahtek.Model.RegleAlerte;
import com.sahtek.sahtek.Service.RegleAlerteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertes")
@RequiredArgsConstructor
public class RegleAlerteController {

    private final RegleAlerteService service;

    // GET /api/alertes
    @GetMapping
    public List<RegleAlerte> obtenirTout(Authentication auth) {
        return service.obtenirTout(auth.getName());
    }

    // POST /api/alertes
    @PostMapping
    public RegleAlerte enregistrer(@Valid @RequestBody RequeteRegleAlerte requete,
                                    Authentication auth) {
        return service.enregistrer(auth.getName(), requete);
    }

    // DELETE /api/alertes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable String id, Authentication auth) {
        service.supprimer(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
