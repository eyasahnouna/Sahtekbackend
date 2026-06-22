package com.sahtek.sahtek.Controller;

import com.sahtek.sahtek.Dto.Requete.RequeteDoseInsuline;
import com.sahtek.sahtek.Dto.Reponse.ReponseDoseInsuline;
import com.sahtek.sahtek.Service.DoseInsulineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insuline")
@RequiredArgsConstructor
public class DoseInsulineController {

    private final DoseInsulineService service;

    // GET /api/insuline
    @GetMapping
    public List<ReponseDoseInsuline> obtenirTout(Authentication auth) {
        return service.obtenirTout(auth.getName());
    }

    // POST /api/insuline
    @PostMapping
    public ReponseDoseInsuline enregistrer(@Valid @RequestBody RequeteDoseInsuline requete,
                                            Authentication auth) {
        return service.enregistrer(auth.getName(), requete);
    }

    // DELETE /api/insuline/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable String id, Authentication auth) {
        service.supprimer(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
