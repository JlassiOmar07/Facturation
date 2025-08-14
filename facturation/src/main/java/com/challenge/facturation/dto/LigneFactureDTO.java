package com.challenge.facturation.dto;

import lombok.Data;
import com.challenge.facturation.model.TauxTVA;

import java.math.BigDecimal;

@Data
public class LigneFactureDTO {

    private String description;
    private int quantite;
    private BigDecimal prixUnitaireHT;
    private TauxTVA tauxTVA;
}
