package com.sahtek.sahtek.Dto.Requete;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequeteEmailInvitation {

    @NotBlank
    @Email
    @Size(max = 254)
    private String destinationEmail;

    @NotBlank
    @Email
    @Size(max = 254)
    private String inviterEmail;

    @NotBlank
    @Pattern(regexp = "viewer|doctor")
    private String role;
}
