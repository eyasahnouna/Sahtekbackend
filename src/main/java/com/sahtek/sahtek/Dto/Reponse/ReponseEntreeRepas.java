package com.sahtek.sahtek.Dto.Reponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReponseEntreeRepas {
    private String id;
    private String nom;
    private String type;
    private String idSlot;
    private Double glycemie;
    private String modeGlycemie;
    private Double dose;
    private Double glucidesG;
    private String heureRepas;       // format HH:mm
    private String heureDose;        // format HH:mm
    private String observation;
}
