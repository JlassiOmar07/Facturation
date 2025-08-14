package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneFacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  String description;

    @Column(nullable = false)
    private int quantite;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaireHT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TauxTVA tauxTVA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facture_id")
    @ToString.Exclude
    private Facture facture;


}
