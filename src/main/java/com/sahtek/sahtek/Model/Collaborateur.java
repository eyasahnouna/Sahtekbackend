package com.sahtek.sahtek.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Collaborateur {

    private String id;
    private Users proprietaire;
    private String emailProprietaire;
    private String email;
    private String role;
    private String statut = "pending";
    private LocalDateTime creeLe;
    private LocalDateTime accepteLe;
}
