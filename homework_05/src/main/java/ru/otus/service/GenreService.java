package ru.otus.service;

public interface GenreService {

    String create(String name);

    String update(long id, String newName);

    String findAll();

    String findById(long id);

    void deleteById(long id);
}
