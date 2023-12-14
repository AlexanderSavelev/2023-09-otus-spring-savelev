package ru.otus.mapper;

import org.springframework.stereotype.Component;
import ru.otus.model.Book;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookStringMapperImpl implements BookStringMapper {

    @Override
    public String map(Book book) {
        return book.getId() + " " + book.getTitle() + " " +
                book.getAuthor().getFullName() + " " + book.getGenre().getName();
    }

    @Override
    public String map(List<Book> bookList) {
        return bookList.stream()
                .map(this::map)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
