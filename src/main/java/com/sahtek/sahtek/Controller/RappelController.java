package com.sahtek.sahtek.Controller;

import com.sahtek.sahtek.Dto.Requete.RequeteRappel;
import com.sahtek.sahtek.Model.Rappel;
import com.sahtek.sahtek.Service.RappelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rappels")
@RequiredArgsConstructor
public class RappelController {

    private final RappelService service;

    // GET /api/rappels
    @GetMapping
    public List<Rappel> obtenirTout(Authentication auth) {
        return service.obtenirTout(auth.getName());
    }

    // POST /api/rappels
    @PostMapping
    public Rappel enregistrer(@Valid @RequestBody RequeteRappel requete,
                               Authentication auth) {
        return service.enregistrer(auth.getName(), requete);
    }

    // DELETE /api/rappels/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable String id, Authentication auth) {
        service.supprimer(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
