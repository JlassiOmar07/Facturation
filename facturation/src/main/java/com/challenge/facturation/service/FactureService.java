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

        BigDecimal ht = BigDecimal.ZERO;
        BigDecimal tva = BigDecimal.ZERO;
        List <LigneFacture> ligneFactures = factureCreateDTO.getLignesFacture()
                .stream()
                .map(ligneFactureDTO -> {
                    LigneFacture ligneFacture = new LigneFacture();
                    ligneFacture.setDescription(ligneFactureDTO.getDescription());
                    ligneFacture.setQuantite(ligneFactureDTO.getQuantite());
                    ligneFacture.setPrixUnitaireHT(ligneFactureDTO.getPrixUnitaireHT());
                    ligneFacture.setTauxTVA(ligneFactureDTO.getTauxTVA());

                    ligneFacture.setFacture(facture);

                    BigDecimal ligneHT = ligneFactureDTO.getPrixUnitaireHT()
                            .multiply(BigDecimal.valueOf(ligneFactureDTO.getQuantite()));
                    BigDecimal ligneTVA = ligneHT.multiply(BigDecimal.valueOf(ligneFactureDTO.getTauxTVA().getValeur() / 100));

                    ht.add(ligneHT);

                    return ligneFacture;
                }).collect(Collectors.toList());

        facture.setLignesFacture(ligneFactures);
        facture.setTotalHT(ht);
        facture.setTotalTVA(tva);
        facture.setTotalTTC(ht.add(tva));
        return factureRepository.save(facture);


    }
}
