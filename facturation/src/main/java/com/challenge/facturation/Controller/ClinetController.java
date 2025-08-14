package com.challenge.facturation.Controller;

import lombok.RequiredArgsConstructor;
import com.challenge.facturation.model.Client;
import org.springframework.web.bind.annotation.*;
import com.challenge.facturation.repository.ClientRepository;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClinetController {
    private final ClientRepository clientRepository;

    @GetMapping public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping ("/{id}") public Client getClientById(@PathVariable Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    @PostMapping public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }
}
