package com.sahtek.sahtek.Dto.Requete;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequeteAnalyseRepas {

    @NotBlank(message = "L'image est obligatoire")
    @Size(
        min = 100,
        max = 5_000_000,
        message = "Image invalide (taille entre 100 et 5 000 000 caractères base64 ≈ 3.7 Mo)"
    )
    private String imageBase64;

    @Size(max = 200, message = "L'indice ne peut pas dépasser 200 caractères")
    private String hint;
}
