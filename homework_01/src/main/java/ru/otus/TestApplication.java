package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.service.LauncherService;

public class TestApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        LauncherService launcherService = context.getBean(LauncherService.class);
        launcherService.launch();
        context.close();
    }
}