package com.challenge.facturation.controller;

import com.challenge.facturation.dto.FactureCreateDTO;
import com.challenge.facturation.model.Facture;
import com.challenge.facturation.repository.FactureRepository;
import com.challenge.facturation.service.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureRepository factureRepository;
    private final FactureService service;

    @GetMapping
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    @GetMapping("/{id}")
    public Facture getFactureById (@PathVariable Long id) {
        return factureRepository.findById(id).orElseThrow();
    }
    @PostMapping
    public Facture createFacture(@RequestBody FactureCreateDTO factureDto) {
        return service.creerFacture(factureDto);
    }
    @GetMapping("/{id}/export")
    public Facture exportFacture(@PathVariable Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found with id: " + id));
    }
}
