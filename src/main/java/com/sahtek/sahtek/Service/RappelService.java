package com.sahtek.sahtek.Service;

import com.sahtek.sahtek.Dto.Requete.RequeteRappel;
import com.sahtek.sahtek.Enum.TypeRappel;
import com.sahtek.sahtek.Model.Rappel;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class RappelService {

    public List<Rappel> obtenirTout(String uid) {
        return Collections.emptyList();
    }

    public Rappel enregistrer(String uid, RequeteRappel requete) {
        Rappel rappel = new Rappel();
        rappel.setId(requete.getId() != null ? requete.getId() : UUID.randomUUID().toString());
        rappel.setType(TypeRappel.valueOf(requete.getType()));
        rappel.setHeure(LocalTime.of(requete.getHeure(), requete.getMinutes()));
        rappel.setLibellePersonnalise(requete.getLibellePersonnalise());
        rappel.setEstActive(requete.getEstActive() != null ? requete.getEstActive() : true);
        return rappel;
    }

    public void supprimer(String uid, String id) {}
}
