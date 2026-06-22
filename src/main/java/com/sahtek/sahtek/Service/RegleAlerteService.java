package com.sahtek.sahtek.Service;

import com.sahtek.sahtek.Dto.Requete.RequeteRegleAlerte;
import com.sahtek.sahtek.Enum.ActionAlerte;
import com.sahtek.sahtek.Enum.ConditionAlerte;
import com.sahtek.sahtek.Model.RegleAlerte;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class RegleAlerteService {

    public List<RegleAlerte> obtenirTout(String uid) {
        return Collections.emptyList();
    }

    public RegleAlerte enregistrer(String uid, RequeteRegleAlerte requete) {
        RegleAlerte regle = new RegleAlerte();
        regle.setId(requete.getId() != null ? requete.getId() : UUID.randomUUID().toString());
        regle.setNom(requete.getNom());
        regle.setCondition(ConditionAlerte.valueOf(requete.getCondition()));
        regle.setSeuil1(requete.getSeuil1());
        regle.setSeuil2(requete.getSeuil2());
        regle.setAction(ActionAlerte.valueOf(requete.getAction()));
        regle.setMessagePersonnalise(requete.getMessagePersonnalise());
        regle.setRepetitionMinutes(requete.getRepetitionMinutes() != null ? requete.getRepetitionMinutes() : 0);
        regle.setEstActive(requete.getEstActive() != null ? requete.getEstActive() : true);
        return regle;
    }

    public void supprimer(String uid, String id) {}
}
