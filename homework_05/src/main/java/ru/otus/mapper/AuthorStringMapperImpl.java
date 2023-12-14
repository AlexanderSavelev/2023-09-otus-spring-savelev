package ru.otus.mapper;

import org.springframework.stereotype.Component;
import ru.otus.model.Author;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorStringMapperImpl implements AuthorStringMapper {

    @Override
    public String map(Author author) {
        return author.getId() + " " + author.getFullName();
    }

    @Override
    public String map(List<Author> authorList) {
        return authorList.stream()
                .map(this::map)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
