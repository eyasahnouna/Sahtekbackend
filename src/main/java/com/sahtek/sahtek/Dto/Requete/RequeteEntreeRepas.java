package com.sahtek.sahtek.Dto.Requete;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequeteEntreeRepas {

    @Size(max = 36)
    private String id;

    @Size(max = 200, message = "Le nom du repas ne peut pas dépasser 200 caractères")
    private String nom;

    @Pattern(
        regexp = "breakfast|lunch|dinner|snack|mesure",
        message = "Type repas invalide"
    )
    private String type;

    @Size(max = 50)
    private String idSlot;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "50.0", message = "Glycémie invalide (max 50 g/L)")
    private Double glycemie;

    @Pattern(regexp = "directe|convertie", message = "Mode glycémie invalide")
    private String modeGlycemie;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "300.0", message = "Dose insuline invalide (max 300 unités)")
    private Double dose;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "1000.0", message = "Glucides invalides (max 1000 g)")
    private Double glucidesG;

    @Min(0) @Max(23)
    private Integer heureRepasH;

    @Min(0) @Max(59)
    private Integer heureRepasMin;

    @Min(0) @Max(23)
    private Integer heureDoseH;

    @Min(0) @Max(59)
    private Integer heureDoseMin;

    @Size(max = 1000, message = "L'observation ne peut pas dépasser 1000 caractères")
    private String observation;
}
