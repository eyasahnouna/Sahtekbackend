package com.sahtek.sahtek.Dto.Requete;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequeteRappel {

    @Size(max = 36)
    private String id;

    @NotBlank
    @Pattern(
        regexp = "fastingGlucose|afterBreakfast|beforeLunch|beforeDinner|insulin|custom",
        message = "Type de rappel invalide"
    )
    private String type;

    @NotNull
    @Min(value = 0, message = "L'heure doit être entre 0 et 23")
    @Max(value = 23, message = "L'heure doit être entre 0 et 23")
    private Integer heure;

    @NotNull
    @Min(value = 0, message = "Les minutes doivent être entre 0 et 59")
    @Max(value = 59, message = "Les minutes doivent être entre 0 et 59")
    private Integer minutes;

    @Size(max = 100, message = "Le libellé ne peut pas dépasser 100 caractères")
    private String libellePersonnalise;

    private Boolean estActive;
}
