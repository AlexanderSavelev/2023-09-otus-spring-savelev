package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.exception.ForeignKeyException;
import ru.otus.exception.NotFoundException;
import ru.otus.mapper.AuthorStringMapper;
import ru.otus.model.Author;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final long DEFAULT_ID = 0;

    private final AuthorDao authorDao;

    private final BookDao bookDao;

    private final AuthorStringMapper mapper;

    public AuthorServiceImpl(AuthorDao authorDao,
                             BookDao bookDao,
                             AuthorStringMapper mapper) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.mapper = mapper;
    }

    @Override
    public String create(String fullName) {
        Author author = new Author(DEFAULT_ID, fullName);
        return mapper.map(authorDao.create(author));
    }

    @Override
    public String update(long id, String newFullName) {
        Author author = authorDao.findById(id).orElseThrow(() -> new NotFoundException("Author not found!"));
        author.setFullName(newFullName);
        return mapper.map(authorDao.update(author));
    }

    @Override
    public String findAll() {
        return mapper.map(authorDao.findAll());
    }

    @Override
    public String findById(long id) {
        Author author = authorDao.findById(id).orElseThrow(() -> new NotFoundException("Author not found!"));
        return mapper.map(author);
    }

    @Override
    public void deleteById(long id) {
        if (bookDao.existByAuthorId(id)) {
            throw new ForeignKeyException("There are books of this author. Delete them first!");
        }
        if (!authorDao.existById(id)) {
            throw new NotFoundException("Author not found!");
        }
        authorDao.deleteById(id);
    }
}
