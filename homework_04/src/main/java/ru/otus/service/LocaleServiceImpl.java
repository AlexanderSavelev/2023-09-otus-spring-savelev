package ru.otus.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.LocaleProperties;

@Service
public class LocaleServiceImpl implements LocaleService {

    private final MessageSource messageSource;

    private final LocaleProperties localeProperties;

    public LocaleServiceImpl(MessageSource messageSource, LocaleProperties localeProperties) {
        this.messageSource = messageSource;
        this.localeProperties = localeProperties;
    }

    @Override
    public String getMessage(String messageCode, Object... args) {
        return messageSource.getMessage(messageCode, args, localeProperties.getLocale());
    }
}
