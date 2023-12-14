package ru.otus.service;

public interface AuthorService {

    String create(String fullName);

    String update(long id, String newFullName);

    String findAll();

    String findById(long id);

    void deleteById(long id);
}