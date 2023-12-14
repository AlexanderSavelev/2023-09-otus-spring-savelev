package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;

@ConfigurationProperties(prefix = "test")
public class TestApplicationConfiguration implements TestProperties, LocaleProperties {

    private final String separator;

    private final String name;

    private final int pass;

    private final Locale locale;

    @ConstructorBinding
    public TestApplicationConfiguration(String separator, String name, int pass, Locale locale) {
        this.separator = separator;
        this.name = name;
        this.pass = pass;
        this.locale = locale;
    }

    @Override
    public String getSeparator() {
        return separator;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPass() {
        return pass;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
