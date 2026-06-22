package com.sahtek.sahtek.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NoteMedicale {

    private String id;
    private Users utilisateur;
    private String contenu;
    private LocalDateTime creeLe;
    private String categorie;
}
