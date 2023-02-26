package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс для хранилищ билетов {@link Ticket}.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public interface TicketRepository {

    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> findBySessionAndRowAndPlace(int sessionId, int rowNumber, int placeNumber);

    boolean deleteById(int id);

    Collection<Ticket> findAll();
}
