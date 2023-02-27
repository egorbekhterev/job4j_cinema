package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.HallDto;

import java.util.Optional;

/**
 * Интерфейс для сервисов, работающих с кинозалами {@link HallDto}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
public interface HallService {

    Optional<HallDto> findById(int id);
}
