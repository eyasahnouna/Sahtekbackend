package com.sahtek.sahtek.Service;

import com.sahtek.sahtek.Dto.Requete.RequeteLectureGlycemie;
import com.sahtek.sahtek.Dto.Reponse.ReponseLectureGlycemie;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class LectureGlycemieService {

    public List<ReponseLectureGlycemie> obtenirTout(String uid) {
        return Collections.emptyList();
    }

    public ReponseLectureGlycemie enregistrer(String uid, RequeteLectureGlycemie requete) {
        return ReponseLectureGlycemie.builder()
                .id(requete.getId() != null ? requete.getId() : UUID.randomUUID().toString())
                .valeur(requete.getValeur())
                .estConvertie(requete.getEstConvertie())
                .horodatage(requete.getHorodatage())
                .note(requete.getNote())
                .contexteRepas(requete.getContexteRepas())
                .build();
    }

    public void supprimer(String uid, String id) {}
}
