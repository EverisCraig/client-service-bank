package com.craig.pe.clientservicebank.handler;

import com.craig.pe.clientservicebank.ClientServiceBankApplication;
import com.craig.pe.clientservicebank.configuration.RouterConfiguration;
import com.craig.pe.clientservicebank.data.DataProvider;
import com.craig.pe.clientservicebank.documents.Client;
import com.craig.pe.clientservicebank.repository.IClientRepository;
import com.craig.pe.clientservicebank.service.ClientService;
import com.craig.pe.clientservicebank.util.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ClientServiceBankApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ClientHandlerTest {
    @Autowired
    RouterConfiguration routes;
    IClientRepository clientRepository;
    ClientService clientService;
    ClientHandler clientHandler;
    WebTestClient client;
    CustomMessage message;

    @BeforeEach
    void setUp() {
        clientRepository = Mockito.mock(IClientRepository.class);
        clientHandler = Mockito.mock(ClientHandler.class);
        client = WebTestClient.bindToRouterFunction(routes.routes(clientHandler))
                .build();
    }

    @Test
    void findAll() {
        Client client1 = DataProvider.clientOne();
        Client client2 = DataProvider.clientTwo();
        Client client3 = DataProvider.clientThree();

        List<Client> clients = Arrays.asList(client1, client2, client3);

        Mockito.when(clientService.findAll())
                .thenReturn(Flux.just(client1, client2, client3));

        Flux<Client> clientFlux = Flux.fromIterable(clients);

        given(clientService.findAll())
                .willReturn(clientFlux);

        client.get()
                .uri("/clients")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Client.class)
                .isEqualTo(clients);

    }

}
