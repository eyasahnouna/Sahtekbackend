package com.sahtek.sahtek.Model;

import com.sahtek.sahtek.Enum.TypeInsuline;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DoseInsuline {

    private String id;
    private Users utilisateur;
    private Double unites;
    private TypeInsuline typeInsuline;
    private LocalDateTime horodatage;
    private String note;
    private String contexteRepas;
}
