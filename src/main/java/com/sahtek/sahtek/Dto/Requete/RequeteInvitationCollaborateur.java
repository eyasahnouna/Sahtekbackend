package com.sahtek.sahtek.Dto.Requete;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequeteInvitationCollaborateur {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format email invalide")
    @Size(max = 254, message = "Email trop long")
    private String email;

    @NotBlank(message = "Le rôle est obligatoire")
    @Pattern(
        regexp = "doctor|viewer",
        message = "Rôle invalide (doctor ou viewer)"
    )
    private String role;
}
