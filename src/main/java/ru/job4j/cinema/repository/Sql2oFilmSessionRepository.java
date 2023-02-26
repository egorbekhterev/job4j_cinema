package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

/**
 * /**
 * Реализация репозитория для киносеансов {@link FilmSession}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {

    /**
     * Экземпляр ORM.
     */
    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Возвращает контейнер киносеанса {@link FilmSession}, найденного в таблице film_sessions по первому совпадению с ID.
     * @param id ID искомого киносеанса.
     * @return контейнер киносеанса {@link FilmSession}
     */
    @Override
    public Optional<FilmSession> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id");
            query.addParameter("id", id);
            var filmSession = query.setColumnMappings(FilmSession.COLUMN_MAPPING)
                    .executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(filmSession);
        }
    }

    /**
     * Преобразует ResultSet из таблицы film_sessions в коллекцию.
     * @return коллекция киносеансов {@link FilmSession}.
     */
    @Override
    public Collection<FilmSession> getAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions");
            return query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetch(FilmSession.class);
        }
    }
}
