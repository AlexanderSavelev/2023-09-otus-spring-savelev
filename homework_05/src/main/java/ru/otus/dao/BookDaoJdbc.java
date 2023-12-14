package ru.otus.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book(title, author_id, genre_id) VALUES(:title, :author_id, :genre_id)";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId())
                .addValue("genre_id", book.getGenre().getId());
        KeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(sql, params, holder);
        book.setId(holder.getKey().longValue());
        return book;
    }

    @Override
    public Book update(Book book) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId())
                .addValue("genre_id", book.getGenre().getId());
        String sql = "UPDATE book SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id";
        jdbc.update(sql, params);
        return book;
    }

    @Override
    public boolean existById(long id) {
        Long count = jdbc.getJdbcOperations().queryForObject("SELECT COUNT(*) FROM book WHERE id = " + id,
                Long.class);
        return count != null && count > 0;
    }

    @Override
    public boolean existByAuthorId(long authorId) {
        String sql = "SELECT COUNT(*) FROM book WHERE author_id = " + authorId;
        Long count = jdbc.getJdbcOperations().queryForObject(sql, Long.class);
        return count != null && count > 0;
    }

    @Override
    public boolean existByGenreId(long genreId) {
        String sql = "SELECT COUNT(*) FROM book WHERE genre_id = " + genreId;
        Long count = jdbc.getJdbcOperations().queryForObject(sql, Long.class);
        return count != null && count > 0;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT b.id, b.title, b.author_id, b.genre_id, " +
                "a.full_name AS author_full_name, g.name AS genre_name " +
                "FROM book AS b " +
                "LEFT JOIN author AS a ON b.author_id = a.id " +
                "LEFT JOIN genre AS g ON b.genre_id = g.id " +
                "ORDER BY b.id";
        return jdbc.query(sql, new BookRowMapper());
    }

    @Override
    public Optional<Book> findById(long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            String sql = "SELECT b.id, b.title, b.author_id, b.genre_id, " +
                    "a.full_name AS author_full_name, g.name AS genre_name " +
                    "FROM book AS b " +
                    "LEFT JOIN author AS a ON b.author_id = a.id " +
                    "LEFT JOIN genre AS g ON b.genre_id = g.id " +
                    "WHERE b.id = :id";
            return Optional.ofNullable(jdbc.queryForObject(sql, params, new BookRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findByAuthorId(long authorId) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("author_id", authorId);
        String sql = "SELECT b.id, b.title, b.author_id, b.genre_id, " +
                "a.full_name AS author_full_name, g.name AS genre_name " +
                "FROM book AS b " +
                "LEFT JOIN author AS a ON b.author_id = a.id " +
                "LEFT JOIN genre AS g ON b.genre_id = g.id " +
                "WHERE b.author_id = :author_id";
        return jdbc.query(sql, params, new BookRowMapper());
    }

    @Override
    public List<Book> findByGenreId(long genreId) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("genre_id", genreId);
        String sql = "SELECT b.id, b.title, b.author_id, b.genre_id, " +
                "a.full_name AS author_full_name, g.name AS genre_name " +
                "FROM book AS b " +
                "LEFT JOIN author AS a ON b.author_id = a.id " +
                "LEFT JOIN genre AS g ON b.genre_id = g.id " +
                "WHERE b.genre_id = :genre_id";
        return jdbc.query(sql, params, new BookRowMapper());
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        jdbc.update("DELETE FROM book WHERE id = :id", params);
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Author author = new Author(resultSet.getLong("author_id"),
                    resultSet.getString("author_full_name"));
            Genre genre = new Genre(resultSet.getLong("genre_id"),
                    resultSet.getString("genre_name"));
            Book book = new Book(resultSet.getLong("id"), resultSet.getString("title"),
                    author, genre);
            return book;
        }
    }
}
