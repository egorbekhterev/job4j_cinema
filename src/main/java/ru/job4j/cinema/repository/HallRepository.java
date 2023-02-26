package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Hall;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс для хранилищ кинозалов {@link Hall}.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public interface HallRepository {

    Optional<Hall> findById(int id);
}
