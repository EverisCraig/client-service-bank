package com.craig.pe.clientservicebank.repository;

import com.craig.pe.clientservicebank.documents.Client;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IClientRepository extends IRepository<Client, String> {
    Mono<Client> findByClientName(String name);
    Mono<Client> findByClientIdentityNumber(String identityNumber);
}
