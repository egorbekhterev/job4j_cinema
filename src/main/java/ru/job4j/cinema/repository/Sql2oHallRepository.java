package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;

import java.util.Optional;

/**
 * Реализация репозитория для кинозалов {@link Hall}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Repository
public class Sql2oHallRepository implements HallRepository {

    /**
     * Экземпляр ORM.
     */
    private final Sql2o sql2o;

    public Sql2oHallRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Возвращает контейнер кинозала {@link Hall}, найденного в таблице film_sessions по первому совпадению с ID.
     * @param id ID искомого кинозала.
     * @return контейнер кинозала {@link FilmSession}
     */
    @Override
    public Optional<Hall> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM halls WHERE id = :id");
            query.addParameter("id", id);
            var hall = query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetchFirst(Hall.class);
            return Optional.ofNullable(hall);
        }
    }
}
