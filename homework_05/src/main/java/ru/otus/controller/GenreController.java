package ru.otus.controller;

public interface GenreController {

    String create(String name);

    String update(long id, String newName);

    String findById(long id);

    String findAll();

    void deleteById(long id);
}
