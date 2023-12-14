package ru.otus.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.LauncherService;

@ShellComponent
public class CommandController {

    private final LauncherService launcherService;

    public CommandController(LauncherService launcherService) {
        this.launcherService = launcherService;
    }

    @ShellMethod(value = "Start test", key = "start")
    public void start() {
        launcherService.launch();
    }
}