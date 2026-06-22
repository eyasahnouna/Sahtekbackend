package com.sahtek.sahtek.Dto.Requete;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequeteDoseInsuline {

    @Size(max = 36)
    private String id;

    @NotNull(message = "Le nombre d'unités est obligatoire")
    @DecimalMin(value = "0.5", message = "La dose minimale est 0.5 unités")
    @DecimalMax(value = "300.0", message = "La dose ne peut pas dépasser 300 unités")
    private Double unites;

    @NotBlank(message = "Le type d'insuline est obligatoire")
    @Pattern(
        regexp = "rapid|slow|mixed",
        message = "Type insuline invalide (rapid, slow ou mixed)"
    )
    private String typeInsuline;

    @NotBlank(message = "L'horodatage est obligatoire")
    @Pattern(
        regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*",
        message = "Format horodatage invalide (attendu: ISO8601)"
    )
    private String horodatage;

    @Size(max = 500, message = "La note ne peut pas dépasser 500 caractères")
    private String note;

    @Size(max = 100)
    private String contexteRepas;
}
