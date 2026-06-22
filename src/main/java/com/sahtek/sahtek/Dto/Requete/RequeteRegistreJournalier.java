package com.sahtek.sahtek.Dto.Requete;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RequeteRegistreJournalier {

    @NotBlank
    private String cleDate;          // YYYY-MM-DD

    private List<RequeteEntreeRepas> entrees;
}
