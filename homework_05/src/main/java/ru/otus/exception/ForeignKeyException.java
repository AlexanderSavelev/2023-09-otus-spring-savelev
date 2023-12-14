package ru.otus.exception;

public class ForeignKeyException extends RuntimeException {

    public ForeignKeyException(String message) {
        super(message);
    }
}
