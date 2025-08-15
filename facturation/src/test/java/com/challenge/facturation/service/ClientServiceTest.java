package com.challenge.facturation.service;

import com.challenge.facturation.model.Client;
import com.challenge.facturation.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository ;

    @InjectMocks
    private  ClientService clientService;

    @Test
    public void shouldReturnAllClients() {
        // Given
        Client client1 = new Client();
        client1.setNom("omar");
        Client client2 = new Client();
        client2.setNom("imed");

        when(clientRepository.findAll()).
                thenReturn(List.of(client1, client2));

        // When
        List <Client> result = clientService.findAllClients();

        // Then
        assertThat(result).hasSize(2).extracting(Client::getNom)
                .containsExactly("omar", "imed");
    }

}
