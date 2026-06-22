package com.sahtek.sahtek.Dto.Requete;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequeteNoteMedicale {

    @Size(max = 36)
    private String id;

    @NotBlank(message = "Le contenu de la note est obligatoire")
    @Size(max = 2000, message = "La note ne peut pas dépasser 2000 caractères")
    private String contenu;

    @Pattern(
        regexp = "glucose|insulin|symptom|general",
        message = "Catégorie invalide (glucose, insulin, symptom ou general)"
    )
    private String categorie;
}
