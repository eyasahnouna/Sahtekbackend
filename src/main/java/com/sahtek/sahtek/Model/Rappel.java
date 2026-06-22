package com.sahtek.sahtek.Model;

import com.sahtek.sahtek.Enum.TypeRappel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class Rappel {

    private String id;
    private Users utilisateur;
    private TypeRappel type;
    private LocalTime heure;
    private String libellePersonnalise;
    private Boolean estActive = true;
}
