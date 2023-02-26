package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Genre;

import java.util.Optional;

/**
 * Реализация репозитория для жанров {@link Genre}.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Repository
public class Sql2oGenreRepository implements GenreRepository {

    /**
     * Экземпляр ORM.
     */
    private final Sql2o sql2o;

    public Sql2oGenreRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Возвращает контейнер жанра {@link Genre}, найденного в таблице genres по первому совпадению с ID.
     * @param id ID искомого жанра.
     * @return контейнер жанра {@link Genre}
     */
    @Override
    public Optional<Genre> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM genres WHERE id = :id");
            query.addParameter("id", id);
            var genre = query.executeAndFetchFirst(Genre.class);
            return Optional.ofNullable(genre);
        }
    }
}
