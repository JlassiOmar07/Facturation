package dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FactureCreateDTO {
    private Long clientId;
    private LocalDate date;
    private List<LigneFactureDTO> lignesFacture;
}
