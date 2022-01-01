package com.craig.pe.clientservicebank.service;


import com.craig.pe.clientservicebank.data.DataProvider;
import com.craig.pe.clientservicebank.documents.Client;
import com.craig.pe.clientservicebank.exception.EntityNotFoundException;
import com.craig.pe.clientservicebank.repository.IClientRepository;
import com.craig.pe.clientservicebank.util.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;


@Slf4j
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ClientServiceTest {
    IClientRepository clientRepository;
    ClientService clientService;
    CustomMessage customMessage;

    @BeforeEach
    void setUp() {
        clientRepository = Mockito.mock(IClientRepository.class);
        clientService = new ClientService(clientRepository, customMessage);
    }

    @Test
    void findByName() {
        log.info("findByName");
        Client clientRequest = DataProvider.clientOne();
        Mockito.when(clientRepository.findByClientName(clientRequest.getClientName()))
                .thenReturn(Mono.just(clientRequest));
        Mono<Client> clientMono = clientService.findByClientName(clientRequest.getClientName());
        StepVerifier.create(clientMono)
                .expectNext(clientRequest)
                .verifyComplete();
    }

    @Test
    void findByNameNotFound() {
        log.info("findByNameNotFound");
        Client clientRequest = DataProvider.clientOne();
        clientRequest.setClientName("Rick");
        Mockito.when(clientRepository.findByClientName(clientRequest.getClientName()))
                .thenReturn(Mono.error(() -> new EntityNotFoundException(customMessage.getMessageSource("client.not.found"))));
        Mono<Client> clientMono = clientService.findByClientName(clientRequest.getClientName());
        StepVerifier.create(clientMono)
                .verifyError(RuntimeException.class);
    }

    @Test
    void findByNameError() {
        log.info("findByNameError");
        Client clientRequest = DataProvider.clientOne();
        clientRequest.setClientName("Jeremy");
        Mockito.when(clientRepository.findByClientName(clientRequest.getClientName()))
                .thenReturn(Mono.error(new RuntimeException("Error")));
        Mono<Client> clientMono = clientService.findByClientName(clientRequest.getClientName());
        StepVerifier.create(clientMono)
                .verifyError(RuntimeException.class);
    }

    @Test
    void findByClientIdentityNumber(){
        log.info("findByNameError");
        Client clientRequest = DataProvider.clientOne();
        Mockito.when(clientRepository.findByClientIdentityNumber(clientRequest.getClientIdentityNumber()))
                .thenReturn(Mono.just(clientRequest));
        Mono<Client> clientIdentityNumberMono = clientService.findByClientIdentityNumber(clientRequest.getClientIdentityNumber());
        StepVerifier.create(clientIdentityNumberMono)
                .expectNext(clientRequest)
                .verifyComplete();
    }

    @Test
    void findByClientIdentityNumberNotFound(){
        log.info("findByNameError");
        Client clientRequest = DataProvider.clientOne();
        clientRequest.setClientIdentityNumber("123456789");
        Mockito.when(clientRepository.findByClientIdentityNumber(clientRequest.getClientIdentityNumber()))
                .thenReturn(Mono.error(() -> new EntityNotFoundException(customMessage.getMessageSource("client.not.found"))));
        Mono<Client> clientIdentityNumberMono = clientService.findByClientIdentityNumber(clientRequest.getClientIdentityNumber());
        StepVerifier.create(clientIdentityNumberMono)
                .verifyError(RuntimeException.class);
    }

    @Test
    void findByClientIdentityNumberError(){
        log.info("findByNameError");
        Client clientRequest = DataProvider.clientOne();
        clientRequest.setClientIdentityNumber("123456789");
        Mockito.when(clientRepository.findByClientIdentityNumber(clientRequest.getClientIdentityNumber()))
                .thenReturn(Mono.error(new RuntimeException("Error")));
        Mono<Client> clientIdentityNumberMono = clientService.findByClientIdentityNumber(clientRequest.getClientIdentityNumber());
        StepVerifier.create(clientIdentityNumberMono)
                .verifyError(RuntimeException.class);
    }

//    @Test
//    void findAll() {
//        log.info("findAll");
//        List<Client> clientList = (Arrays.asList(DataProvider.clientOne(), DataProvider.clientTwo(), DataProvider.clientThree()));
//        Mockito.when(clientRepository.findAll())
//                .thenReturn(Flux.fromIterable(clientList));
//        Flux<Client> clientFlux = clientService.findAll();
//        StepVerifier.create(clientFlux)
//                .expectNext(clientList.get(0))
//                .expectNext(clientList.get(1))
//                .expectNext(clientList.get(2))
//                .verifyComplete();
//    }
//
//    @Test
//    void findAllError() {
//        log.info("findAllError");
//        Mockito.when(clientRepository.findAll())
//                .thenReturn(Flux.error(new RuntimeException("Error")));
//        Flux<Client> clientFlux = clientService.findAll();
//        StepVerifier.create(clientFlux)
//                .verifyError(RuntimeException.class);
//    }


}
