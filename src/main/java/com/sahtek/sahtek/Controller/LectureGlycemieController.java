package com.sahtek.sahtek.Controller;

import com.sahtek.sahtek.Dto.Requete.RequeteLectureGlycemie;
import com.sahtek.sahtek.Dto.Reponse.ReponseLectureGlycemie;
import com.sahtek.sahtek.Service.LectureGlycemieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/glycemie")
@RequiredArgsConstructor
public class LectureGlycemieController {

    private final LectureGlycemieService service;

    // GET /api/glycemie
    @GetMapping
    public List<ReponseLectureGlycemie> obtenirTout(Authentication auth) {
        return service.obtenirTout(auth.getName());
    }

    // POST /api/glycemie
    @PostMapping
    public ReponseLectureGlycemie enregistrer(@Valid @RequestBody RequeteLectureGlycemie requete,
                                               Authentication auth) {
        return service.enregistrer(auth.getName(), requete);
    }

    // DELETE /api/glycemie/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable String id, Authentication auth) {
        service.supprimer(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
