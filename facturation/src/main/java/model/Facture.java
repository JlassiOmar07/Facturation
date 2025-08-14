package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Client client;

    @Column(nullable = false)
    private LocalDate date ;
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneFacture> lignesFacture;

    @Column(nullable = false, precision = 10, scale = 2)
    private double totalHT;

    @Column(nullable = false, precision = 10, scale = 2)
    private double totalTTC;

    @Column(nullable = false, precision = 10, scale = 2)
    private double totalTVA;

}
