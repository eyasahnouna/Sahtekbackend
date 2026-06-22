package com.sahtek.sahtek.Dto.Reponse;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReponseRegistreJournalier {
    private String cleDate;
    private List<ReponseEntreeRepas> entrees;
    private Double totalDose;        // somme des doses du jour
    private Double moyenneGlycemie;  // moyenne des glycémies du jour
}
