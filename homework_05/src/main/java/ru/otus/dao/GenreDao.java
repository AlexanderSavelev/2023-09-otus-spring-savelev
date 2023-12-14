package ru.otus.dao;

import ru.otus.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Genre create(Genre genre);

    Genre update(Genre genre);

    boolean existById(long id);

    List<Genre> findAll();

    Optional<Genre> findById(long id);

    void deleteById(long id);
}