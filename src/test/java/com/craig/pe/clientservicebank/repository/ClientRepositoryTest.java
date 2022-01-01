package com.craig.pe.clientservicebank.repository;

import com.craig.pe.clientservicebank.data.DataProvider;
import com.craig.pe.clientservicebank.documents.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class ClientRepositoryTest {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public ClientRepositoryTest(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Test
    void findAll() {
        Client client1 = DataProvider.clientOne();
        Client client2 = DataProvider.clientTwo();
        Client client3 = DataProvider.clientThree();

        Flux<Client> saved = Flux.just(client1, client2, client3)
                .flatMap(this.reactiveMongoTemplate::save);

        Flux<Client> findAll = this.reactiveMongoTemplate
                .dropCollection(Client.class)
                .thenMany(saved)
                .thenMany(this.reactiveMongoTemplate.findAll(Client.class));

        Predicate<Client> predicate = client -> StringUtils.hasText(client.getClientId());

        StepVerifier.create(findAll)
                .expectNextMatches(predicate)
                .expectNextMatches(predicate)
                .expectNextMatches(predicate)
                .verifyComplete();
    }

}
