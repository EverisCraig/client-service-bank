package com.craig.pe.clientservicebank.service;

import com.craig.pe.clientservicebank.documents.Client;
import reactor.core.publisher.Mono;

public interface IClientService extends IBaseService<Client,String> {
    Mono<Client> findByClientName(String clientName);
    Mono<Client> findByClientIdentityNumber(String clientIdentityNumber);
}
