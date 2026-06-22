package com.sahtek.sahtek.Dto.Requete;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequeteLectureGlycemie {

    @Size(max = 36)
    private String id;

    @NotNull(message = "La valeur glycémique est obligatoire")
    @DecimalMin(value = "0.1", message = "La glycémie ne peut pas être inférieure à 0.1 g/L")
    @DecimalMax(value = "50.0", message = "La glycémie ne peut pas dépasser 50 g/L")
    private Double valeur;

    private Boolean estConvertie;

    @NotBlank(message = "L'horodatage est obligatoire")
    @Pattern(
        regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*",
        message = "Format horodatage invalide (attendu: ISO8601)"
    )
    private String horodatage;

    @Size(max = 500, message = "La note ne peut pas dépasser 500 caractères")
    private String note;

    @Pattern(
        regexp = "fasting|before_meal|after_meal|bedtime|sensor",
        message = "Contexte repas invalide"
    )
    private String contexteRepas;
}
