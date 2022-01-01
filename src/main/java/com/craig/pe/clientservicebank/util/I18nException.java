package com.craig.pe.clientservicebank.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class I18nException extends RuntimeException{
    protected final transient Object[] args;

    public I18nException( String key,Object[] args) {
        super(key);
        this.args = args;
    }
}
