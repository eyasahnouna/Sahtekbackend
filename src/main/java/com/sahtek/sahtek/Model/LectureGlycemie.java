package com.sahtek.sahtek.Model;

import com.sahtek.sahtek.Enum.ModeGlycemie;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LectureGlycemie {

    private String id;
    private Users utilisateur;
    private Double valeur;
    private Boolean estConvertie;
    private LocalDateTime horodatage;
    private String note;
    private String contexteRepas;
}
