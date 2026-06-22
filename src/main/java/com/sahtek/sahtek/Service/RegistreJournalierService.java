package com.sahtek.sahtek.Service;

import com.sahtek.sahtek.Dto.Requete.RequeteEntreeRepas;
import com.sahtek.sahtek.Dto.Requete.RequeteRegistreJournalier;
import com.sahtek.sahtek.Dto.Reponse.ReponseEntreeRepas;
import com.sahtek.sahtek.Dto.Reponse.ReponseRegistreJournalier;
import com.sahtek.sahtek.Enum.ModeGlycemie;
import com.sahtek.sahtek.Enum.TypeRepas;
import com.sahtek.sahtek.Model.EntreeRepas;
import com.sahtek.sahtek.Model.RegistreJournalier;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class RegistreJournalierService {

    public List<ReponseRegistreJournalier> obtenirTout(String uid) {
        return Collections.emptyList();
    }

    public ReponseRegistreJournalier sauvegarder(String uid, RequeteRegistreJournalier requete) {
        RegistreJournalier registre = new RegistreJournalier();
        registre.setId(uid + "_" + requete.getCleDate());
        registre.setCleDate(requete.getCleDate());

        if (requete.getEntrees() != null) {
            for (RequeteEntreeRepas req : requete.getEntrees()) {
                registre.getEntrees().add(versEntree(req, registre));
            }
        }

        return versReponse(registre);
    }

    private EntreeRepas versEntree(RequeteEntreeRepas req, RegistreJournalier registre) {
        EntreeRepas e = new EntreeRepas();
        e.setId(req.getId() != null ? req.getId() : UUID.randomUUID().toString());
        e.setRegistreJournalier(registre);
        e.setNom(req.getNom());
        if (req.getType() != null) e.setType(TypeRepas.valueOf(req.getType()));
        e.setIdSlot(req.getIdSlot());
        e.setGlycemie(req.getGlycemie());
        if (req.getModeGlycemie() != null) e.setModeGlycemie(ModeGlycemie.valueOf(req.getModeGlycemie()));
        e.setDose(req.getDose());
        e.setGlucidesG(req.getGlucidesG());
        if (req.getHeureRepasH() != null)
            e.setHeureRepas(LocalTime.of(req.getHeureRepasH(), req.getHeureRepasMin()));
        if (req.getHeureDoseH() != null)
            e.setHeureDose(LocalTime.of(req.getHeureDoseH(), req.getHeureDoseMin()));
        e.setObservation(req.getObservation());
        return e;
    }

    private ReponseRegistreJournalier versReponse(RegistreJournalier r) {
        double totalDose = r.getEntrees().stream()
                .filter(e -> e.getDose() != null)
                .mapToDouble(EntreeRepas::getDose).sum();

        double moyenneGlycemie = r.getEntrees().stream()
                .filter(e -> e.getGlycemie() != null && e.getGlycemie() > 0)
                .mapToDouble(EntreeRepas::getGlycemie).average().orElse(0);

        List<ReponseEntreeRepas> entrees = r.getEntrees().stream().map(e ->
                ReponseEntreeRepas.builder()
                        .id(e.getId()).nom(e.getNom())
                        .type(e.getType() != null ? e.getType().name() : null)
                        .idSlot(e.getIdSlot()).glycemie(e.getGlycemie())
                        .modeGlycemie(e.getModeGlycemie() != null ? e.getModeGlycemie().name() : null)
                        .dose(e.getDose()).glucidesG(e.getGlucidesG())
                        .heureRepas(e.getHeureRepas() != null ? e.getHeureRepas().toString() : null)
                        .heureDose(e.getHeureDose() != null ? e.getHeureDose().toString() : null)
                        .observation(e.getObservation())
                        .build()
        ).toList();

        return ReponseRegistreJournalier.builder()
                .cleDate(r.getCleDate()).entrees(entrees)
                .totalDose(totalDose).moyenneGlycemie(moyenneGlycemie)
                .build();
    }
}
