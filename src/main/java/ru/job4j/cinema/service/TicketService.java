package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

/**
 * Интерфейс для сервисов, работающих с билетами {@link Ticket}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
public interface TicketService {

    Optional<Ticket> save(Ticket ticket);
}
