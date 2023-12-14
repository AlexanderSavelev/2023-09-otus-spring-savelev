package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.exception.NotFoundException;
import ru.otus.mapper.BookStringMapper;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final long DEFAULT_ID = 0;

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    private final BookStringMapper mapper;

    public BookServiceImpl(BookDao bookDao,
                           AuthorDao authorDao,
                           GenreDao genreDao,
                           BookStringMapper mapper) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.mapper = mapper;
    }

    @Override
    public String create(String title, long authorId, long genreId) {
        Author author = authorDao.findById(authorId).orElseThrow(() ->
                new NotFoundException("Author not found!"));
        Genre genre = genreDao.findById(genreId).orElseThrow(() ->
                new NotFoundException("Genre not found!"));
        Book book = new Book(DEFAULT_ID, title, author, genre);
        return mapper.map(bookDao.create(book));
    }

    @Override
    public String update(long id, String newTitle, long newAuthorId, long newGenreId) {
        Author newAuthor = authorDao.findById(newAuthorId).orElseThrow(() ->
                new NotFoundException("Author not found!"));
        Genre newGenre = genreDao.findById(newGenreId).orElseThrow(() ->
                new NotFoundException("Genre not found!"));
        Book book = bookDao.findById(id).orElseThrow(() -> new NotFoundException("Book not found!"));
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setGenre(newGenre);
        return mapper.map(bookDao.update(book));
    }

    @Override
    public String findAll() {
        return mapper.map(bookDao.findAll());
    }

    @Override
    public String findById(long id) {
        Book book = bookDao.findById(id).orElseThrow(() -> new NotFoundException("Book not found!"));
        return mapper.map(book);
    }

    @Override
    public String findByAuthorId(long authorId) {
        if (!authorDao.existById(authorId)) {
            throw new NotFoundException("Author not found!");
        }
        List<Book> bookList = bookDao.findByAuthorId(authorId);
        return mapper.map(bookList);
    }

    @Override
    public String findByGenreId(long genreId) {
        if (!genreDao.existById(genreId)) {
            throw new NotFoundException("Genre not found!");
        }
        List<Book> bookList = bookDao.findByGenreId(genreId);
        return mapper.map(bookList);
    }

    @Override
    public void deleteById(long id) {
        if (!bookDao.existById(id)) {
            throw new NotFoundException("Book not found!");
        }
        bookDao.deleteById(id);
    }
}
