package com.sahtek.sahtek.Service;

import com.sahtek.sahtek.Dto.Requete.RequeteDoseInsuline;
import com.sahtek.sahtek.Dto.Reponse.ReponseDoseInsuline;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class DoseInsulineService {

    public List<ReponseDoseInsuline> obtenirTout(String uid) {
        return Collections.emptyList();
    }

    public ReponseDoseInsuline enregistrer(String uid, RequeteDoseInsuline requete) {
        return ReponseDoseInsuline.builder()
                .id(requete.getId() != null ? requete.getId() : UUID.randomUUID().toString())
                .unites(requete.getUnites())
                .typeInsuline(requete.getTypeInsuline())
                .horodatage(requete.getHorodatage())
                .note(requete.getNote())
                .contexteRepas(requete.getContexteRepas())
                .build();
    }

    public void supprimer(String uid, String id) {}
}
