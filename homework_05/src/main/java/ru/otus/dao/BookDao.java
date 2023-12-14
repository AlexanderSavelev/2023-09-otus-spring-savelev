package ru.otus.dao;

import ru.otus.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Book create(Book book);

    Book update(Book book);

    boolean existById(long id);

    boolean existByAuthorId(long authorId);

    boolean existByGenreId(long genreId);

    List<Book> findAll();

    Optional<Book> findById(long id);

    List<Book> findByAuthorId(long authorId);

    List<Book> findByGenreId(long genreId);

    void deleteById(long id);
}
