package com.challenge.facturation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column (nullable = false)
    private String nom;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false, unique = true)
    private String siret;

    @Column
    private LocalDateTime dateCreation = LocalDateTime.now();


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Facture> factures;
}
