package ru.otus.dao;

import ru.otus.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Author create(Author author);

    Author update(Author author);

    boolean existById(long id);

    List<Author> findAll();

    Optional<Author> findById(long id);

    void deleteById(long id);
}