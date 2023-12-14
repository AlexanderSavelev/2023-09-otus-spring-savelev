package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.exception.NotFoundException;
import ru.otus.mapper.BookStringMapper;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false", classes = BookServiceImpl.class)
class BookServiceImplTest {

    private final static long TEST_BOOK_ID = 1;

    private final static long TEST_AUTHOR_ID = 1;

    private final static long TEST_UPDATED_AUTHOR_ID = 2;

    private final static long TEST_GENRE_ID = 1;

    private final static long TEST_UPDATED_GENRE_ID = 2;

    private final static String TEST_BOOK_TITLE = "TestBook";

    private final static String TEST_UPDATED_BOOK_TITLE = "UpdatedTestBook";

    private final static String TEST_AUTHOR_FULLNAME = "TestAuthor";

    private final static String TEST_UPDATED_AUTHOR_FULLNAME = "UpdatedTestAuthor";

    private final static String TEST_GENRE_NAME = "TestGenre";

    private final static String TEST_UPDATED_GENRE_NAME = "UpdatedTestGenre";

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @MockBean
    private BookStringMapper mapper;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    void whenCreateShouldReturnNewBook() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book expectedBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        String expectedBookString = expectedBook.getId() + " " + expectedBook.getTitle() + " " +
                expectedBook.getAuthor().getFullName() + " " + expectedBook.getGenre().getName();
        when(authorDao.findById(author.getId()))
                .thenReturn(Optional.of(author));
        when(genreDao.findById(genre.getId()))
                .thenReturn(Optional.of(genre));
        when(bookDao.create(any(Book.class)))
                .thenReturn(expectedBook);
        when(mapper.map(any(Book.class)))
                .thenAnswer(values -> {
                    Book newBook = values.getArgument(0);
                    return mapToString(newBook);
                });
        assertEquals(expectedBookString, bookService.create(TEST_BOOK_TITLE, TEST_AUTHOR_ID,
                TEST_GENRE_ID));
    }

    @Test
    void whenCreateShouldThrowNotFoundErrorBecauseOfNotExistingAuthor() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book expectedBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        when(authorDao.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        when(genreDao.findById(genre.getId()))
                .thenReturn(Optional.of(genre));
        when(bookDao.create(any(Book.class)))
                .thenReturn(expectedBook);
        when(mapper.map(any(Book.class)))
                .thenAnswer(values -> {
                    Book newBook = values.getArgument(0);
                    return mapToString(newBook);
                });
        assertThrows(NotFoundException.class, () -> bookService.create(TEST_BOOK_TITLE, TEST_AUTHOR_ID,
                TEST_GENRE_ID));
    }

    @Test
    void whenCreateShouldThrowNotFoundErrorBecauseOfNotExistingGenre() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book expectedBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        when(authorDao.findById(author.getId()))
                .thenReturn(Optional.of(author));
        when(genreDao.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        when(bookDao.create(any(Book.class)))
                .thenReturn(expectedBook);
        when(mapper.map(any(Book.class)))
                .thenAnswer(values -> {
                    Book newBook = values.getArgument(0);
                    return mapToString(newBook);
                });
        assertThrows(NotFoundException.class, () -> bookService.create(TEST_BOOK_TITLE, TEST_AUTHOR_ID,
                TEST_GENRE_ID));
    }

    @Test
    void whenUpdateShouldReturnUpdatedBook() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book book = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        Author updatedAuthor = new Author(TEST_UPDATED_AUTHOR_ID, TEST_UPDATED_AUTHOR_FULLNAME);
        Genre updatedGenre = new Genre(TEST_UPDATED_GENRE_ID, TEST_UPDATED_GENRE_NAME);
        Book expectedUpdatedBook = new Book(TEST_BOOK_ID, TEST_UPDATED_BOOK_TITLE, updatedAuthor, updatedGenre);
        String expectedUpdatedBookString = expectedUpdatedBook.getId() + " " + expectedUpdatedBook.getTitle() + " " +
                expectedUpdatedBook.getAuthor().getFullName() + " " + expectedUpdatedBook.getGenre().getName();
        when(authorDao.findById(any(Long.class)))
                .thenReturn(Optional.of(updatedAuthor));
        when(genreDao.findById(any(Long.class)))
                .thenReturn(Optional.of(updatedGenre));
        when(bookDao.findById(any(Long.class)))
                .thenReturn(Optional.of(book));
        when(bookDao.update(any(Book.class)))
                .thenReturn(expectedUpdatedBook);
        when(mapper.map(any(Book.class)))
                .thenAnswer(values -> {
                    Book updatedBook = values.getArgument(0);
                    return mapToString(updatedBook);
                });
        assertEquals(expectedUpdatedBookString, bookService.update(TEST_BOOK_ID, TEST_UPDATED_BOOK_TITLE,
                TEST_UPDATED_AUTHOR_ID, TEST_UPDATED_GENRE_ID));
    }

    @Test
    void whenUpdateShouldThrowNotFoundErrorBecauseOfNotExistingAuthor() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book book = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        Author updatedAuthor = new Author(TEST_UPDATED_AUTHOR_ID, TEST_UPDATED_AUTHOR_FULLNAME);
        Genre updatedGenre = new Genre(TEST_UPDATED_GENRE_ID, TEST_UPDATED_GENRE_NAME);
        Book expectedUpdatedBook = new Book(TEST_BOOK_ID, TEST_UPDATED_BOOK_TITLE, updatedAuthor, updatedGenre);
        when(authorDao.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        when(genreDao.findById(any(Long.class)))
                .thenReturn(Optional.of(updatedGenre));
        when(bookDao.findById(any(Long.class)))
                .thenReturn(Optional.of(book));
        when(bookDao.update(any(Book.class)))
                .thenReturn(expectedUpdatedBook);
        when(mapper.map(any(Book.class)))
                .thenAnswer(values -> {
                    Book updatedBook = values.getArgument(0);
                    return mapToString(updatedBook);
                });
        assertThrows(NotFoundException.class, () -> bookService.update(TEST_BOOK_ID, TEST_UPDATED_BOOK_TITLE,
                TEST_UPDATED_AUTHOR_ID, TEST_UPDATED_GENRE_ID));
    }

    @Test
    void whenUpdateShouldThrowNotFoundErrorBecauseOfNotExistingGenre() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book book = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        Author updatedAuthor = new Author(TEST_UPDATED_AUTHOR_ID, TEST_UPDATED_AUTHOR_FULLNAME);
        Genre updatedGenre = new Genre(TEST_UPDATED_GENRE_ID, TEST_UPDATED_GENRE_NAME);
        Book expectedUpdatedBook = new Book(TEST_BOOK_ID, TEST_UPDATED_BOOK_TITLE, updatedAuthor, updatedGenre);
        when(authorDao.findById(any(Long.class)))
                .thenReturn(Optional.of(updatedAuthor));
        when(genreDao.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        when(bookDao.findById(any(Long.class)))
                .thenReturn(Optional.of(book));
        when(bookDao.update(any(Book.class)))
                .thenReturn(expectedUpdatedBook);
        when(mapper.map(any(Book.class)))
                .thenAnswer(values -> {
                    Book updatedBook = values.getArgument(0);
                    return mapToString(updatedBook);
                });
        assertThrows(NotFoundException.class, () -> bookService.update(TEST_BOOK_ID, TEST_UPDATED_BOOK_TITLE,
                TEST_UPDATED_AUTHOR_ID, TEST_UPDATED_GENRE_ID));
    }

    @Test
    void whenFindAllShouldReturnAllBooks() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book foundBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        List<Book> allBooks = List.of(foundBook);
        String expectedBooksString = allBooks.stream()
                .map(this::mapToString)
                .collect(Collectors.joining(System.lineSeparator()));
        when(bookDao.findAll())
                .thenReturn(allBooks);
        when(mapper.map(anyList()))
                .thenAnswer(values -> {
                    List<Book> books = values.getArgument(0);
                    return books.stream()
                            .map(this::mapToString)
                            .collect(Collectors.joining(System.lineSeparator()));
                });
        assertEquals(expectedBooksString, bookService.findAll());
    }

    @Test
    void whenFindByIdShouldReturnBook() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book expectedBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        String expectedBookString = expectedBook.getId() + " " + expectedBook.getTitle() + " " +
                expectedBook.getAuthor().getFullName() + " " + expectedBook.getGenre().getName();
        when(bookDao.findById(anyLong()))
                .thenReturn(Optional.of(expectedBook));
        when(mapper.map(any(Book.class)))
                .thenAnswer(values -> {
                    Book updatedBook = values.getArgument(0);
                    return mapToString(updatedBook);
                });
        assertEquals(expectedBookString, bookService.findById(TEST_BOOK_ID));
    }

    @Test
    void whenFindByIdShouldThrowNotFoundErrorBecauseOfNotExistingBook() {
        when(bookDao.findById(anyLong()))
                .thenReturn(Optional.empty());
        when(mapper.map(any(Book.class)))
                .thenAnswer(values -> {
                    Book updatedBook = values.getArgument(0);
                    return mapToString(updatedBook);
                });
        assertThrows(NotFoundException.class, () -> bookService.findById(TEST_BOOK_ID));
    }

    @Test
    void whenFindByAuthorIdShouldReturnBook() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book foundBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        List<Book> authorBooksList = List.of(foundBook);
        String expectedBooksString = authorBooksList.stream()
                .map(this::mapToString)
                .collect(Collectors.joining(System.lineSeparator()));
        when(authorDao.existById(anyLong()))
                .thenReturn(true);
        when(bookDao.findByAuthorId(anyLong()))
                .thenReturn(authorBooksList);
        when(mapper.map(ArgumentMatchers.<Book>anyList()))
                .thenAnswer(values -> {
                    List<Book> books = values.getArgument(0);
                    return books.stream()
                            .map(this::mapToString)
                            .collect(Collectors.joining(System.lineSeparator()));
                });
        assertEquals(expectedBooksString, bookService.findByAuthorId(TEST_AUTHOR_ID));
    }

    @Test
    void whenFindByAuthorIdShouldThrowNotFoundErrorBecauseOfNotExistingAuthor() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book foundBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        List<Book> authorBooksList = List.of(foundBook);
        when(bookDao.findByAuthorId(anyLong()))
                .thenReturn(authorBooksList);
        when(mapper.map(ArgumentMatchers.<Book>anyList()))
                .thenAnswer(values -> {
                    List<Book> books = values.getArgument(0);
                    return books.stream()
                            .map(this::mapToString)
                            .collect(Collectors.joining(System.lineSeparator()));
                });
        assertThrows(NotFoundException.class, () -> bookService.findByAuthorId(TEST_AUTHOR_ID));
    }

    @Test
    void whenFindByGenreIdShouldReturnBook() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book foundBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        List<Book> genreBooksList = List.of(foundBook);
        String expectedBooksString = genreBooksList.stream()
                .map(this::mapToString)
                .collect(Collectors.joining(System.lineSeparator()));
        when(genreDao.existById(anyLong()))
                .thenReturn(true);
        when(bookDao.findByGenreId(anyLong()))
                .thenReturn(genreBooksList);
        when(mapper.map(ArgumentMatchers.<Book>anyList()))
                .thenAnswer(values -> {
                    List<Book> books = values.getArgument(0);
                    return books.stream()
                            .map(this::mapToString)
                            .collect(Collectors.joining(System.lineSeparator()));
                });
        assertEquals(expectedBooksString, bookService.findByGenreId(TEST_GENRE_ID));
    }

    @Test
    void whenFindByGenreIdShouldThrowNotFoundErrorBecauseOfNotExistingGenre() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book foundBook = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, author, genre);
        List<Book> genreBooksList = List.of(foundBook);
        when(bookDao.findByGenreId(anyLong()))
                .thenReturn(genreBooksList);
        when(mapper.map(ArgumentMatchers.<Book>anyList()))
                .thenAnswer(values -> {
                    List<Book> books = values.getArgument(0);
                    return books.stream()
                            .map(this::mapToString)
                            .collect(Collectors.joining(System.lineSeparator()));
                });
        assertThrows(NotFoundException.class, () -> bookService.findByGenreId(TEST_GENRE_ID));
    }

    @Test
    void whenDeleteByIdShouldDelete() {
        when(bookDao.existById(anyLong()))
                .thenReturn(true);
        bookDao.deleteById(TEST_BOOK_ID);
        Mockito.verify(bookDao, Mockito.times(1))
                .deleteById(TEST_BOOK_ID);
    }

    @Test
    void whenDeleteByIdShouldThrowNotFoundErrorBecauseOfNotExistingBook() {
        when(bookDao.existById(anyLong()))
                .thenReturn(false);
        assertThrows(NotFoundException.class, () -> bookService.deleteById(TEST_BOOK_ID));
    }

    private String mapToString(Book book) {
        return book.getId() + " " + book.getTitle() + " " +
                book.getAuthor().getFullName() + " " + book.getGenre().getName();
    }
}