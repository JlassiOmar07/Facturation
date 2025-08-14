package com.challenge.facturation.service;

import com.challenge.facturation.dto.FactureCreateDTO;
import com.challenge.facturation.exeptions.InvalidFactureExeption;
import lombok.RequiredArgsConstructor;
import com.challenge.facturation.model.Client;
import com.challenge.facturation.model.Facture;
import com.challenge.facturation.model.LigneFacture;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.challenge.facturation.repository.ClientRepository;
import com.challenge.facturation.repository.FactureRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureRepository factureRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Facture creerFacture (FactureCreateDTO factureCreateDTO){
        if (factureCreateDTO.getLignesFacture() == null || factureCreateDTO.getLignesFacture().isEmpty()){
            throw new InvalidFactureExeption("une facture doit avoir au moins une ligne");
        }

        Client client = clientRepository.findById(factureCreateDTO.getClientId())
                .orElseThrow(() -> new InvalidFactureExeption("Client non trouvé"));

        Facture facture = new Facture();
        facture.setClient(client);
        facture.setDate(factureCreateDTO.getDate());


        /* 1. Créer les lignes */
        List<LigneFacture> lignes = factureCreateDTO.getLignesFacture()
                .stream()
                .map(d -> {
                    LigneFacture l = new LigneFacture();
                    l.setDescription(d.getDescription());
                    l.setQuantite(d.getQuantite());
                    l.setPrixUnitaireHT(d.getPrixUnitaireHT());
                    l.setTauxTVA(d.getTauxTVA());
                    l.setFacture(facture);
                    return l;
                })
                .collect(Collectors.toList());

        /* 2. Calculer les totaux */
        BigDecimal ht = lignes.stream()
                .map(l -> l.getPrixUnitaireHT()
                        .multiply(BigDecimal.valueOf(l.getQuantite())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal tva = lignes.stream()
                .map(l -> l.getPrixUnitaireHT()
                        .multiply(BigDecimal.valueOf(l.getQuantite()))
                        .multiply(BigDecimal.valueOf(l.getTauxTVA().getValeur() / 100)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        /* 3. Affecter et sauvegarder */
        facture.setLignesFacture(lignes);
        facture.setTotalHT(ht);
        facture.setTotalTVA(tva);
        facture.setTotalTTC(ht.add(tva));

        return factureRepository.save(facture);
    }
}
