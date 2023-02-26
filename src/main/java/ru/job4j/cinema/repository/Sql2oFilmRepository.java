package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация репозитория для фильмов {@link Film}.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Repository
public class Sql2oFilmRepository implements FilmRepository {

    /**
     * Экземпляр ORM.
     */
    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Возвращает контейнер фильма {@link Film}, найденного в таблице films по первому совпадению с ID.
     * @param id ID искомого фильма.
     * @return контейнер фильма {@link Film}
     */
    @Override
    public Optional<Film> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films WHERE id = :id");
            query.addParameter("id", id);
            var film = query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    /**
     * Преобразует ResultSet из таблицы films в коллекцию.
     * @return коллекция фильмов {@link Film}.
     */
    @Override
    public Collection<Film> getAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }
}
