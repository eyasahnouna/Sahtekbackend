package com.sahtek.sahtek.Model;

import com.sahtek.sahtek.Enum.ModeGlycemie;
import com.sahtek.sahtek.Enum.TypeRepas;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class EntreeRepas {

    private String id;
    private RegistreJournalier registreJournalier;
    private String nom;
    private TypeRepas type;
    private String idSlot;
    private Double glycemie;
    private ModeGlycemie modeGlycemie;
    private Double dose;
    private Double glucidesG;
    private LocalTime heureRepas;
    private LocalTime heureDose;
    private String observation;
}
