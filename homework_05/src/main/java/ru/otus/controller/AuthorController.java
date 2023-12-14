package ru.otus.controller;

public interface AuthorController {

    String create(String fullName);

    String update(long id, String newFullName);

    String findById(long id);

    String findAll();

    void deleteById(long id);
}
