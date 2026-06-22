package com.sahtek.sahtek.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RegistreJournalier {

    private String id;
    private Users utilisateur;
    private String cleDate;
    private List<EntreeRepas> entrees = new ArrayList<>();
    private LocalDateTime modifieLe;
}
