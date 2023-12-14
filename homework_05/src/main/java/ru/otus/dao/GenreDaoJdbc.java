package ru.otus.dao;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre create(Genre genre) {
        String sql = "INSERT INTO genre(name) VALUES(:name)";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", genre.getName());
        KeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(sql, params, holder);
        genre.setId(holder.getKey().longValue());
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("name", genre.getName());
        String sql = "UPDATE genre SET name = :name WHERE id = :id";
        jdbc.update(sql, params);
        return genre;
    }

    @Override
    public boolean existById(long id) {
        String sql = "SELECT COUNT(*) FROM genre WHERE id = " + id;
        Long count = jdbc.getJdbcOperations().queryForObject(sql, Long.class);
        return count != null && count > 0;
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query("SELECT id, name FROM genre ORDER BY id", new GenreDaoJdbc.GenreRowMapper());
    }

    @Override
    public Optional<Genre> findById(long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            String sql = "SELECT id, name FROM genre WHERE id = :id";
            return Optional.ofNullable(jdbc.queryForObject(sql, params, new GenreDaoJdbc.GenreRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        jdbc.update("DELETE FROM genre WHERE id = :id", params);
    }

    protected static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
