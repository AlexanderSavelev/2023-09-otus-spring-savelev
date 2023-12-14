package ru.otus.controller;

public interface BookController {

    String create(String title, long authorId, long genreId);

    String update(long id, String newTitle, long newAuthorId, long newGenreId);

    String findById(long id);

    String findAll();

    String findByAuthorId(long authorId);

    String findByGenreId(long genreId);

    void deleteById(long id);
}
