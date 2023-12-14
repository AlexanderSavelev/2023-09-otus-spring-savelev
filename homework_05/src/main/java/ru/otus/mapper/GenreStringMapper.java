package ru.otus.mapper;

import ru.otus.model.Genre;

import java.util.List;

public interface GenreStringMapper {

    String map(Genre genre);

    String map(List<Genre> genreList);
}
