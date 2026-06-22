package com.sahtek.sahtek.Controller;

import com.sahtek.sahtek.Dto.Requete.RequeteInvitationCollaborateur;
import com.sahtek.sahtek.Model.Collaborateur;
import com.sahtek.sahtek.Service.CollaborateurService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collaborateurs")
@RequiredArgsConstructor
public class CollaborateurController {

    private final CollaborateurService service;
    private final FirebaseAuth firebaseAuth;

    // POST /api/collaborateurs  → inviter
    @PostMapping
    public ResponseEntity<Collaborateur> inviter(
            @Valid @RequestBody RequeteInvitationCollaborateur requete,
            Authentication auth) throws Exception {
        UserRecord user = firebaseAuth.getUser(auth.getName());
        return ResponseEntity.ok(service.inviter(auth.getName(), user.getEmail(), requete));
    }

    // GET /api/collaborateurs/envoyes  → mes invitations envoyées
    @GetMapping("/envoyes")
    public List<Collaborateur> envoyes(Authentication auth) {
        return service.mesInvitationsEnvoyees(auth.getName());
    }

    // GET /api/collaborateurs/recus  → mes invitations reçues
    @GetMapping("/recus")
    public List<Collaborateur> recus(Authentication auth) throws Exception {
        UserRecord user = firebaseAuth.getUser(auth.getName());
        return service.mesInvitationsRecues(user.getEmail());
    }

    // PUT /api/collaborateurs/{id}/accepter
    @PutMapping("/{id}/accepter")
    public ResponseEntity<Void> accepter(@PathVariable String id) {
        service.accepter(id);
        return ResponseEntity.ok().build();
    }

    // PUT /api/collaborateurs/{id}/refuser
    @PutMapping("/{id}/refuser")
    public ResponseEntity<Void> refuser(@PathVariable String id) {
        service.refuser(id);
        return ResponseEntity.ok().build();
    }

    // DELETE /api/collaborateurs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable String id, Authentication auth) {
        service.supprimer(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
