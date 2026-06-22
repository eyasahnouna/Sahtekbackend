package com.sahtek.sahtek.Dto.Reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReponseAlimentaire {

    // Noms exacts attendus par Flutter (FoodItemResult.fromJson)
    private String name;

    @JsonProperty("estimated_grams")
    private Double estimatedGrams;

    @JsonProperty("carbs_g")
    private Double carbsG;

    private Double confidence;
}
