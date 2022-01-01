package com.craig.pe.clientservicebank.service;

import com.craig.pe.clientservicebank.documents.Client;
import com.craig.pe.clientservicebank.exception.EntityNotFoundException;
import com.craig.pe.clientservicebank.repository.IClientRepository;
import com.craig.pe.clientservicebank.repository.IRepository;
import com.craig.pe.clientservicebank.util.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ClientService extends BaseService<Client, String> implements IClientService {

    private final IClientRepository clientRepository;
    private final CustomMessage customMessage;

    @Autowired
    public ClientService(IClientRepository clientRepository, CustomMessage customMessage) {
        this.clientRepository = clientRepository;
        this.customMessage = customMessage;
    }

    @Override
    protected IRepository<Client, String> getRepository() {
        return clientRepository;
    }


    @Override
    public Mono<Client> findByClientName(String clientName) {
        Mono<Client> clientMono = clientRepository.findByClientName(clientName);
        return clientMono.hasElement().flatMap(hasElement -> {
            if (Boolean.TRUE.equals(hasElement)) {
                return clientMono;
            } else {
                return Mono.error(new EntityNotFoundException(customMessage.getMessageSource("entity.client.notNameFound")));
            }
        });
    }


    @Override
    public Mono<Client> findByClientIdentityNumber(String clientIdentityNumber) {
        Mono<Client> clientMono = clientRepository.findByClientIdentityNumber(clientIdentityNumber);
        return clientMono.hasElement().flatMap(hasElement -> {
            if (Boolean.TRUE.equals(hasElement)) {
                return clientMono;
            } else {
                return Mono.error(new EntityNotFoundException(customMessage.getMessageSource("entity.client.notIdentityNumberFound")));
            }
        });
    }


}
