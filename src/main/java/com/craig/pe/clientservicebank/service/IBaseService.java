package com.craig.pe.clientservicebank.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBaseService<T,ID> {
    Mono<T> create(T entity);
    Flux<T> findAll();
    Mono<T> findById(ID id);
    Mono<T> update(T entity);
    Mono<Void> delete(ID id);
}
