package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;

/**
 * Реализация сервиса для билетов {@link Ticket}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Service
public class SimpleTicketService implements TicketService {

    /**
     * Поле для обращения к хранилищу билетов.
     */
    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository sql2oTicketRepository) {
        this.ticketRepository = sql2oTicketRepository;
    }

    /**
     * Выполняет сохранение созданного билета {@link Ticket} в таблицу tickets. Устанавливает ID для этого билета.
     * При попытке купить существующий билет возвращает пустой Optional.
     * @param ticket экземпляр билета {@link Ticket}
     * @return контейнер билета {@link Ticket}
     */
    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
