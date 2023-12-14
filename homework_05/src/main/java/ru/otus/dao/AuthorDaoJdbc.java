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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author create(Author author) {
        String sql = "INSERT INTO author(full_name) VALUES(:full_name)";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("full_name", author.getFullName());
        KeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(sql, params, holder);
        author.setId(holder.getKey().longValue());
        return author;
    }

    @Override
    public Author update(Author author) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("full_name", author.getFullName());
        String sql = "UPDATE author SET full_name = :full_name WHERE id = :id";
        jdbc.update(sql, params);
        return author;
    }

    @Override
    public boolean existById(long id) {
        String sql = "SELECT COUNT(*) FROM author WHERE id = " + id;
        Long count = jdbc.getJdbcOperations().queryForObject(sql, Long.class);
        return count != null && count > 0;
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("SELECT id, full_name FROM author ORDER BY id", new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            String sql = "SELECT id, full_name FROM author WHERE id = :id";
            return Optional.ofNullable(jdbc.queryForObject(sql, params, new AuthorRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        jdbc.update("DELETE FROM author WHERE id = :id", params);
    }

    protected static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String fullName = resultSet.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
