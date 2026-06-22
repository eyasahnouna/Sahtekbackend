package com.sahtek.sahtek.Dto.Reponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReponseLectureGlycemie {
    private String id;
    private Double valeur;
    private Boolean estConvertie;
    private String horodatage;
    private String note;
    private String contexteRepas;
}
