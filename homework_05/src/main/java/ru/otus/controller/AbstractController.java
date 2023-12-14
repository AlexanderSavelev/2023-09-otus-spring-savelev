package ru.otus.controller;

import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.shell.command.annotation.ExceptionResolver;
import ru.otus.exception.ForeignKeyException;
import ru.otus.exception.NotFoundException;

public abstract class AbstractController {

    @ExceptionResolver
    static CommandHandlingResult errorHandler(NotFoundException exception) {
        return CommandHandlingResult.of(exception.getMessage() + System.lineSeparator(), 42);
    }

    @ExceptionResolver
    static CommandHandlingResult errorHandler(ForeignKeyException exception) {
        return CommandHandlingResult.of(exception.getMessage() + System.lineSeparator(), 42);
    }
}
