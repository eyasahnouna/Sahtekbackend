package com.sahtek.sahtek.Dto.Reponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReponseDoseInsuline {
    private String id;
    private Double unites;
    private String typeInsuline;
    private String horodatage;
    private String note;
    private String contexteRepas;
}
