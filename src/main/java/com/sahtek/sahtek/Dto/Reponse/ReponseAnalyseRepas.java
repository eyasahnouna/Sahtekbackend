package com.sahtek.sahtek.Dto.Reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReponseAnalyseRepas {

    // Noms exacts attendus par Flutter (FoodItemResult.fromJson)
    private List<ReponseAlimentaire> foods;

    @JsonProperty("total_carbs_g")
    private Double totalCarbsG;

    private String notes;
    private String source;
}
