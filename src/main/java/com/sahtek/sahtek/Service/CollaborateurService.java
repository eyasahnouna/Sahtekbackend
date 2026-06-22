package com.sahtek.sahtek.Service;

import com.sahtek.sahtek.Dto.Requete.RequeteInvitationCollaborateur;
import com.sahtek.sahtek.Model.Collaborateur;
import com.sahtek.sahtek.Model.Users;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class CollaborateurService {

    public Collaborateur inviter(String proprietaireUid, String emailProprietaire,
                                 RequeteInvitationCollaborateur requete) {
        Users proprietaire = new Users();
        proprietaire.setUid(proprietaireUid);
        proprietaire.setEmail(emailProprietaire);

        Collaborateur collab = new Collaborateur();
        collab.setId(UUID.randomUUID().toString());
        collab.setProprietaire(proprietaire);
        collab.setEmailProprietaire(emailProprietaire);
        collab.setEmail(requete.getEmail());
        collab.setRole(requete.getRole());
        collab.setStatut("pending");
        collab.setCreeLe(LocalDateTime.now());
        return collab;
    }

    public List<Collaborateur> mesInvitationsEnvoyees(String uid) {
        return Collections.emptyList();
    }

    public List<Collaborateur> mesInvitationsRecues(String email) {
        return Collections.emptyList();
    }

    public void accepter(String id) {}

    public void refuser(String id) {}

    public void supprimer(String uid, String id) {}
}
