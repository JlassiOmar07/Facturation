package com.challenge.facturation.service;

import com.challenge.facturation.model.Client;
import com.challenge.facturation.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAllClients () {
        return clientRepository.findAll();
    }

}
