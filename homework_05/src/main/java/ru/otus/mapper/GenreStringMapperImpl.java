package ru.otus.mapper;

import org.springframework.stereotype.Component;
import ru.otus.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreStringMapperImpl implements GenreStringMapper {

    @Override
    public String map(Genre genre) {
        return genre.getId() + " " + genre.getName();
    }

    @Override
    public String map(List<Genre> genreList) {
        return genreList.stream()
                .map(this::map)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
