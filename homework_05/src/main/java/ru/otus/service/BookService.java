package ru.otus.service;

public interface BookService {

    String create(String title, long authorId, long genreId);

    String update(long id, String newTitle, long newAuthorId, long newGenreId);

    String findAll();

    String findById(long id);

    String findByAuthorId(long authorId);

    String findByGenreId(long genreId);

    void deleteById(long id);
}
