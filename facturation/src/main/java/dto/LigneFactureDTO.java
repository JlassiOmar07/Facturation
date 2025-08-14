package dto;

import lombok.Data;

@Data
public class LigneFactureDTO {

    private String description;
    private int quantite;
    private double prixUnitaireHT;
    private String tauxTVA;
}
