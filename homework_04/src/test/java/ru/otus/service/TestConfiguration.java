package ru.otus.service;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"ru.otus.dao", "ru.otus.service"})
@SpringBootConfiguration
public class TestConfiguration {
}
