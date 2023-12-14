package ru.otus.mapper;

import ru.otus.model.Book;

import java.util.List;

public interface BookStringMapper {

    String map(Book book);

    String map(List<Book> bookList);
}
