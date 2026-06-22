package com.sahtek.sahtek.Service;

import com.sahtek.sahtek.Dto.Requete.RequeteNoteMedicale;
import com.sahtek.sahtek.Model.NoteMedicale;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class NoteMedicaleService {

    public List<NoteMedicale> obtenirTout(String uid) {
        return Collections.emptyList();
    }

    public NoteMedicale enregistrer(String uid, RequeteNoteMedicale requete) {
        NoteMedicale note = new NoteMedicale();
        note.setId(requete.getId() != null ? requete.getId() : UUID.randomUUID().toString());
        note.setContenu(requete.getContenu());
        note.setCategorie(requete.getCategorie());
        note.setCreeLe(LocalDateTime.now());
        return note;
    }

    public void supprimer(String uid, String id) {}
}
