package com.craig.pe.clientservicebank.service;

import com.craig.pe.clientservicebank.repository.IRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class BaseService<T, ID> implements IBaseService<T, ID> {

    protected abstract IRepository<T, ID> getRepository();

    @Override
    public Mono<T> create(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public Flux<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Mono<T> findById(ID id) {
        return getRepository().findById(id);
    }

    @Override
    public Mono<T> update(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public Mono<Void> delete(ID id) {
        return getRepository().deleteById(id);
    }
}
