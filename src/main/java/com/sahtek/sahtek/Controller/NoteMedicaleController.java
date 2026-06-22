package com.sahtek.sahtek.Controller;

import com.sahtek.sahtek.Dto.Requete.RequeteNoteMedicale;
import com.sahtek.sahtek.Model.NoteMedicale;
import com.sahtek.sahtek.Service.NoteMedicaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteMedicaleController {

    private final NoteMedicaleService service;

    // GET /api/notes
    @GetMapping
    public List<NoteMedicale> obtenirTout(Authentication auth) {
        return service.obtenirTout(auth.getName());
    }

    // POST /api/notes
    @PostMapping
    public NoteMedicale enregistrer(@Valid @RequestBody RequeteNoteMedicale requete,
                                     Authentication auth) {
        return service.enregistrer(auth.getName(), requete);
    }

    // DELETE /api/notes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable String id, Authentication auth) {
        service.supprimer(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
