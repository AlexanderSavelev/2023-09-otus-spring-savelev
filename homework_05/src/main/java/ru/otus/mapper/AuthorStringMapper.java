package ru.otus.mapper;

import ru.otus.model.Author;

import java.util.List;

public interface AuthorStringMapper {

    String map(Author author);

    String map(List<Author> authorList);
}
