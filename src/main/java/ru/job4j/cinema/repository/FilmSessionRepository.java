package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс для хранилищ киносеансов {@link FilmSession}.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public interface FilmSessionRepository {

    Optional<FilmSession> findById(int id);

    Collection<FilmSession> getAll();
}
