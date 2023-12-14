package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private final static long DEFAULT_ID = 0;

    private final static long TEST_BOOK_ONE_ID = 1;

    private final static long TEST_BOOK_TWO_ID = 2;

    private final static long TEST_BOOK_THREE_ID = 3;

    private final static long TEST_BOOK_FOUR_ID = 4;

    private final static long TEST_AUTHOR_ONE_ID = 1;

    private final static long TEST_AUTHOR_TWO_ID = 2;

    private final static long TEST_AUTHOR_THREE_ID = 3;

    private final static long TEST_AUTHOR_FOUR_ID = 4;

    private final static long TEST_GENRE_ONE_ID = 1;

    private final static long TEST_GENRE_TWO_ID = 2;

    private final static long TEST_GENRE_THREE_ID = 3;

    private final static long TEST_GENRE_FOUR_ID = 4;

    private final static String TEST_BOOK_ONE_TITLE = "TestBookOne";

    private final static String TEST_BOOK_TWO_TITLE = "TestBookTwo";

    private final static String TEST_BOOK_THREE_TITLE = "TestBookThree";

    private final static String TEST_BOOK_FOUR_TITLE = "TestBookFour";

    private final static String TEST_AUTHOR_ONE_FULLNAME = "TestAuthorOne";

    private final static String TEST_AUTHOR_TWO_FULLNAME = "TestAuthorTwo";

    private final static String TEST_AUTHOR_THREE_FULLNAME = "TestAuthorThree";

    private final static String TEST_AUTHOR_FOUR_FULLNAME = "TestAuthorFour";

    private final static String TEST_GENRE_ONE_NAME = "TestGenreOne";

    private final static String TEST_GENRE_TWO_NAME = "TestGenreTwo";

    private final static String TEST_GENRE_THREE_NAME = "TestGenreThree";

    private final static String TEST_GENRE_FOUR_NAME = "TestGenreFour";

    @Autowired
    private BookDaoJdbc bookDao;

    @Test
    void whenCreateShouldReturnNewBook() {
        Author author = new Author(TEST_AUTHOR_ONE_ID, TEST_AUTHOR_ONE_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ONE_ID, TEST_GENRE_ONE_NAME);
        Book newBook = new Book(DEFAULT_ID, TEST_BOOK_FOUR_TITLE, author, genre);
        Book expectedBook = new Book(TEST_BOOK_FOUR_ID, TEST_BOOK_FOUR_TITLE, author, genre);
        newBook = bookDao.create(newBook);
        assertEquals(expectedBook, newBook);
    }

    @Test
    void whenUpdateShouldReturnUpdatedBook() {
        Author author = new Author(TEST_AUTHOR_TWO_ID, TEST_AUTHOR_TWO_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_TWO_ID, TEST_GENRE_TWO_NAME);
        Book expectedBook = new Book(TEST_BOOK_ONE_ID, TEST_BOOK_TWO_TITLE, author, genre);
        Book updatedBook = bookDao.update(expectedBook);
        assertEquals(expectedBook, updatedBook);
    }

    @Test
    void whenExistByIdShouldReturnTrue() {
        assertTrue(bookDao.existById(TEST_BOOK_ONE_ID));
    }

    @Test
    void whenExistByIdShouldReturnFalse() {
        assertFalse(bookDao.existById(TEST_BOOK_FOUR_ID));
    }

    @Test
    void whenExistByAuthorIdShouldReturnTrue() {
        assertTrue(bookDao.existByAuthorId(TEST_AUTHOR_ONE_ID));
    }

    @Test
    void whenExistByAuthorIdShouldReturnFalse() {
        assertFalse(bookDao.existByAuthorId(TEST_AUTHOR_FOUR_ID));
    }

    @Test
    void whenExistByGenreIdShouldReturnTrue() {
        assertTrue(bookDao.existByGenreId(TEST_GENRE_ONE_ID));
    }

    @Test
    void whenExistByGenreIdShouldReturnFalse() {
        assertFalse(bookDao.existByGenreId(TEST_GENRE_FOUR_ID));
    }

    @Test
    void whenFindAllShouldReturnAllBooks() {
        Author authorOne = new Author(TEST_AUTHOR_ONE_ID, TEST_AUTHOR_ONE_FULLNAME);
        Author authorTwo = new Author(TEST_AUTHOR_TWO_ID, TEST_AUTHOR_TWO_FULLNAME);
        Author authorThree = new Author(TEST_AUTHOR_THREE_ID, TEST_AUTHOR_THREE_FULLNAME);
        Genre genreOne = new Genre(TEST_GENRE_ONE_ID, TEST_GENRE_ONE_NAME);
        Genre genreTwo = new Genre(TEST_GENRE_TWO_ID, TEST_GENRE_TWO_NAME);
        Genre genreThree = new Genre(TEST_GENRE_THREE_ID, TEST_GENRE_THREE_NAME);
        Book bookOne = new Book(TEST_BOOK_ONE_ID, TEST_BOOK_ONE_TITLE, authorOne, genreOne);
        Book bookTwo = new Book(TEST_BOOK_TWO_ID, TEST_BOOK_TWO_TITLE, authorTwo, genreTwo);
        Book bookThree = new Book(TEST_BOOK_THREE_ID, TEST_BOOK_THREE_TITLE, authorThree, genreThree);
        List<Book> expectedBooksList = List.of(bookOne, bookTwo, bookThree);
        assertIterableEquals(expectedBooksList, bookDao.findAll());
    }

    @Test
    void whenFindByIdShouldReturnBook() {
        Author author = new Author(TEST_AUTHOR_ONE_ID, TEST_AUTHOR_ONE_FULLNAME);
        Genre genre = new Genre(TEST_GENRE_ONE_ID, TEST_GENRE_ONE_NAME);
        Book expectedBook = new Book(TEST_BOOK_ONE_ID, TEST_BOOK_ONE_TITLE, author, genre);
        assertEquals(Optional.of(expectedBook), bookDao.findById(TEST_BOOK_ONE_ID));
    }

    @Test
    void whenFindByIdShouldReturnEmpty() {
        assertEquals(Optional.empty(), bookDao.findById(TEST_BOOK_FOUR_ID));
    }

    @Test
    void whenFindByAuthorIdShouldReturnBook() {
        Author authorOne = new Author(TEST_AUTHOR_ONE_ID, TEST_AUTHOR_ONE_FULLNAME);
        Genre genreOne = new Genre(TEST_GENRE_ONE_ID, TEST_GENRE_ONE_NAME);
        Book bookOne = new Book(TEST_BOOK_ONE_ID, TEST_BOOK_ONE_TITLE, authorOne, genreOne);
        List<Book> expectedAuthorBooksList = List.of(bookOne);
        assertIterableEquals(expectedAuthorBooksList, bookDao.findByAuthorId(TEST_AUTHOR_ONE_ID));
    }

    @Test
    void whenFindByGenreIdShouldReturnBook() {
        Author authorOne = new Author(TEST_AUTHOR_ONE_ID, TEST_AUTHOR_ONE_FULLNAME);
        Genre genreOne = new Genre(TEST_GENRE_ONE_ID, TEST_GENRE_ONE_NAME);
        Book bookOne = new Book(TEST_BOOK_ONE_ID, TEST_BOOK_ONE_TITLE, authorOne, genreOne);
        List<Book> expectedGenreBooksList = List.of(bookOne);
        assertIterableEquals(expectedGenreBooksList, bookDao.findByGenreId(TEST_GENRE_ONE_ID));
    }

    @Test
    void whenDeleteByIdShouldDelete() {
        Author authorTwo = new Author(TEST_AUTHOR_TWO_ID, TEST_AUTHOR_TWO_FULLNAME);
        Author authorThree = new Author(TEST_AUTHOR_THREE_ID, TEST_AUTHOR_THREE_FULLNAME);
        Genre genreTwo = new Genre(TEST_GENRE_TWO_ID, TEST_GENRE_TWO_NAME);
        Genre genreThree = new Genre(TEST_GENRE_THREE_ID, TEST_GENRE_THREE_NAME);
        Book bookTwo = new Book(TEST_BOOK_TWO_ID, TEST_BOOK_TWO_TITLE, authorTwo, genreTwo);
        Book bookThree = new Book(TEST_BOOK_THREE_ID, TEST_BOOK_THREE_TITLE, authorThree, genreThree);
        List<Book> expectedBooksList = List.of(bookTwo, bookThree);
        bookDao.deleteById(TEST_BOOK_ONE_ID);
        assertIterableEquals(expectedBooksList, bookDao.findAll());
    }
}