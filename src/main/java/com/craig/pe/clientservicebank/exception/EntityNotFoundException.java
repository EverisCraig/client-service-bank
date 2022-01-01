package com.craig.pe.clientservicebank.exception;

import com.craig.pe.clientservicebank.util.I18nException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotFoundException extends I18nException {

    public EntityNotFoundException(String key, Object... args) {
        super(key, args);
    }
}
