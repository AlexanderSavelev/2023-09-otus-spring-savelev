package ru.otus.controller;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.BookService;

@ShellComponent
@ShellCommandGroup("Books")
public class BookControllerImpl extends AbstractController implements BookController {

    private final BookService bookService;

    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @ShellMethod(value = "Create book", key = "book create")
    public String create(@ShellOption(value = {"-t", "--title"}, help = "Book title") String title,
                         @ShellOption(value = {"-a", "--author"}, help = "Author id") long authorId,
                         @ShellOption(value = {"-g", "--genre"}, help = "Genre id") long genreId) {
        return bookService.create(title, authorId, genreId);
    }

    @Override
    @ShellMethod(value = "Update book", key = "book update")
    public String update(@ShellOption(value = {"-i", "--id"}, help = "Book id") long id,
                         @ShellOption(value = {"-t", "--title"}, help = "Book title") String newTitle,
                         @ShellOption(value = {"-a", "--authors"}, help = "Author id") long newAuthorId,
                         @ShellOption(value = {"-g", "--genre"}, help = "Genre id") long newGenreId) {
        return bookService.update(id, newTitle, newAuthorId, newGenreId);
    }

    @ShellMethod(value = "Find all books", key = "book find-all")
    public String findAll() {
        return bookService.findAll();
    }

    @Override
    @ShellMethod(value = "Find book by id", key = "book find-by-id")
    public String findById(@ShellOption(value = {"-i", "--id"}, help = "Book id") long id) {
        return bookService.findById(id);
    }

    @Override
    @ShellMethod(value = "Find books by author id", key = "book find-by-author-id")
    public String findByAuthorId(@ShellOption(value = {"-i", "--id"}, help = "Author id") long authorId) {
        return bookService.findByAuthorId(authorId);
    }

    @Override
    @ShellMethod(value = "Find books by genre id", key = "book find-by-genre-id")
    public String findByGenreId(@ShellOption(value = {"-i", "--id"}, help = "Genre id") long genreId) {
        return bookService.findByGenreId(genreId);
    }

    @Override
    @ShellMethod(value = "Delete book by id", key = "book delete-by-id")
    public void deleteById(@ShellOption(value = {"-i", "--id"}, help = "Book id") long id) {
        bookService.deleteById(id);
    }
}