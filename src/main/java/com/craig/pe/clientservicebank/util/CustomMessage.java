package com.craig.pe.clientservicebank.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CustomMessage {
    private final MessageSource messageSource;

    public CustomMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, Locale.ENGLISH);
    }

    public String getMessageSource(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }
}
