package com.craig.pe.clientservicebank.handler;

import com.craig.pe.clientservicebank.documents.Client;
import com.craig.pe.clientservicebank.exception.EntityNotFoundException;
import com.craig.pe.clientservicebank.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@Slf4j
@Component
public class ClientHandler {

    private static final String MSJ_ERROR_UPDATE_CUSTOMER = "THE CLIENT CANNOT BE UPDATED";
    private static final String MSJ_ERROR_FIND_CUSTOMER = "CLIENT DOES NOT EXIST";
    private static final String MSJ_ERROR_IDENTITY_NUMBER_CLIENT = "THE CUSTOMER DOES NOT EXIST IN MICRO SERVICE CLIENT";
    private static final String MSJ_ERROR_FIND_CLIENT="CLIENT DOES NOT EXIST";

    private final IClientService clientService;

    @Autowired
    public ClientHandler(IClientService clientService) {
        this.clientService = clientService;
    }

    public Mono<ServerResponse> findAll() {
        log.info("Get all clients");
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(clientService.findAll(), Client.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("clientId");
        log.info("Get client by id {}", id);
        return clientService.findById(id)
                .flatMap(client -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(client))
                .switchIfEmpty(Mono.error(new EntityNotFoundException(MSJ_ERROR_FIND_CLIENT)))
                .onErrorResume(error -> Mono.error(new EntityNotFoundException(error.getMessage())));
    }

    public Mono<ServerResponse> findByClientName(ServerRequest request) {
        String name = request.pathVariable("clientName");
        log.info("Get client by name {}", name);
        return clientService.findByClientName(name)
                .flatMap(client -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(client))
                .switchIfEmpty(Mono.error(new EntityNotFoundException(MSJ_ERROR_FIND_CUSTOMER)))
                .onErrorResume(error -> Mono.error(new EntityNotFoundException(error.getMessage())));
    }

    public Mono<ServerResponse> findByClientIdentityNumber(ServerRequest request) {
        String clientIdentityNumber = request.pathVariable("clientIdentityNumber");
        log.info("Get client by identity number {}", clientIdentityNumber);
        return clientService.findByClientIdentityNumber(clientIdentityNumber)
                .flatMap(client -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(client))
                .switchIfEmpty(Mono.error(new EntityNotFoundException(MSJ_ERROR_IDENTITY_NUMBER_CLIENT)))
                .onErrorResume(error -> Mono.error(new EntityNotFoundException(error.getMessage())));
    }

//    public Mono<ServerResponse> create(ServerRequest request) {
//        return request.bodyToMono(Client.class)
//                .flatMap(client -> clientService.create(client)
//                        .flatMap(clientCreated -> ServerResponse.created(request.uri())
//                                .contentType(APPLICATION_JSON)
//                                .body(Mono.just(clientCreated), Client.class)))
//                .onErrorResume(error -> {
//                    WebClientResponseException ex = (WebClientResponseException) error;
//                    if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
//                        return ServerResponse.badRequest()
//                                .contentType(APPLICATION_JSON)
//                                .body(Mono.just(ex.getResponseBodyAsString()), Client.class);
//                    }
//                    return Mono.error(error);
//                });
//    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Client> clientMono = request.bodyToMono(Client.class);
        log.info("Create client {}", clientMono);
        return clientMono.flatMap(clientService::create)
                .flatMap(clientCreated -> ServerResponse.created(URI.create("/client/".concat(clientCreated.getClientId())))
                        .contentType(APPLICATION_JSON)
                        .bodyValue(clientCreated))
                .onErrorResume(error -> {
                    WebClientResponseException ex = (WebClientResponseException) error;
                    if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return ServerResponse.badRequest()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(ex.getResponseBodyAsString());
                    }
                    return Mono.error(error);
                });
    }


    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<Client> bill = request.bodyToMono(Client.class);
        log.info("Update client {}", bill);
        return bill.flatMap(client -> clientService.findByClientIdentityNumber(client.getClientIdentityNumber())
                        .flatMap(clientFound -> {
                            clientFound.setClientIdentityType(client.getClientIdentityType());
                            clientFound.setClientIdentityNumber(client.getClientIdentityNumber());
                            clientFound.setClientName(client.getClientName());
                            clientFound.setClientLastName(client.getClientLastName());
                            clientFound.setClientPhone(client.getClientPhone());
                            clientFound.setClientEmail(client.getClientEmail());
                            return clientService.update(clientFound);
                        })).flatMap(clientUpdated -> ServerResponse.created(URI.create("/client/".concat(clientUpdated.getClientId())))
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(clientUpdated), Client.class))
                .onErrorResume(error -> Mono.error(new RuntimeException(MSJ_ERROR_UPDATE_CUSTOMER, error)));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("clientId");
        log.info("Delete client {}", id);
        return clientService.delete(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage())));
    }
}
