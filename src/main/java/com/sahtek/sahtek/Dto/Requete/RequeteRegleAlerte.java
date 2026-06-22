package com.sahtek.sahtek.Dto.Requete;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequeteRegleAlerte {

    @Size(max = 36)
    private String id;

    @NotBlank(message = "Le nom de la règle est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nom;

    @NotBlank
    @Pattern(
        regexp = "above|below|between",
        message = "Condition invalide (above, below ou between)"
    )
    private String condition;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "50.0", message = "Seuil invalide (max 50 g/L)")
    private Double seuil1;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "50.0", message = "Seuil invalide (max 50 g/L)")
    private Double seuil2;

    @NotBlank
    @Pattern(
        regexp = "hydration|insulin|notify|call",
        message = "Action invalide (hydration, insulin, notify ou call)"
    )
    private String action;

    @Size(max = 500, message = "Le message ne peut pas dépasser 500 caractères")
    private String messagePersonnalise;

    @Min(0) @Max(1440)
    private Integer repetitionMinutes;

    private Boolean estActive;
}
