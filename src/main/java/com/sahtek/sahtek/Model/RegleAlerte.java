package com.sahtek.sahtek.Model;

import com.sahtek.sahtek.Enum.ActionAlerte;
import com.sahtek.sahtek.Enum.ConditionAlerte;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegleAlerte {

    private String id;
    private Users utilisateur;
    private String nom;
    private ConditionAlerte condition;
    private Double seuil1;
    private Double seuil2;
    private ActionAlerte action;
    private String messagePersonnalise;
    private Integer repetitionMinutes;
    private Boolean estActive = true;
}
