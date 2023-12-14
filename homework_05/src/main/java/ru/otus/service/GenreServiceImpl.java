package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.exception.ForeignKeyException;
import ru.otus.exception.NotFoundException;
import ru.otus.mapper.GenreStringMapper;
import ru.otus.model.Genre;

@Service
public class GenreServiceImpl implements GenreService {

    private static final long DEFAULT_ID = 0;

    private final GenreDao genreDao;

    private final BookDao bookDao;

    private final GenreStringMapper mapper;

    public GenreServiceImpl(GenreDao genreDao, BookDao bookDao, GenreStringMapper mapper) {
        this.genreDao = genreDao;
        this.bookDao = bookDao;
        this.mapper = mapper;
    }

    @Override
    public String create(String name) {
        Genre genre = new Genre(DEFAULT_ID, name);
        return mapper.map(genreDao.create(genre));
    }

    @Override
    public String update(long id, String newName) {
        Genre genre = genreDao.findById(id).orElseThrow(() -> new NotFoundException("Genre not found!"));
        return mapper.map(genreDao.update(genre));
    }

    @Override
    public String findAll() {
        return mapper.map(genreDao.findAll());
    }

    @Override
    public String findById(long id) {
        Genre genre = genreDao.findById(id).orElseThrow(() -> new NotFoundException("Genre not found!"));
        return mapper.map(genre);
    }

    @Override
    public void deleteById(long id) {
        if (bookDao.existByGenreId(id)) {
            throw new ForeignKeyException("There are books of this genre. Delete them first!");
        }
        if (!genreDao.existById(id)) {
            throw new NotFoundException("Genre not found!");
        }
        genreDao.deleteById(id);
    }
}
