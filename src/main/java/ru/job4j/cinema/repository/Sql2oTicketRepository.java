package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация репозитория для билетов {@link Ticket}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sql2oTicketRepository.class.getName());

    /**
     * Экземпляр ORM.
     */
    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Выполняет сохранение созданного билета {@link Ticket} в таблицу tickets. Устанавливает ID для этого билета.
     * При попытке купить существующий билет возвращает пустой Optional.
     * @param ticket экземпляр билета {@link Ticket}
     * @return контейнер билета {@link Ticket}
     */
    @Override
    public Optional<Ticket> save(Ticket ticket) {
        Optional<Ticket> rsl = Optional.empty();
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO tickets (session_id, row_number, place_number, user_id)
                    VALUES (:sessionId, :rowNumber, :placeNumber, :userId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            rsl = Optional.of(ticket);
        } catch (Sql2oException e) {
            LOGGER.error("Uniqueness error in the save(Ticket ticket) method.", e);
        }
        return rsl;
    }

    /**
     * Возвращает контейнер билета {@link Ticket}, найденного в таблице tickets по первому совпадению параметров.
     * @param placeNumber - место в зале
     * @param rowNumber - ряд в зале
     * @param sessionId - ID киносеанса
     * @return контейнер билета {@link Ticket}
     */
    @Override
    public Optional<Ticket> findBySessionAndRowAndPlace(int sessionId, int rowNumber, int placeNumber) {
        try (var connection = sql2o.open()) {
            var query = connection
                    .createQuery("SELECT * FROM tickets WHERE row_number = :rowNumber AND place_number = :placeNumber AND session_id = :sessionId");
            query.addParameter("rowNumber", rowNumber)
                    .addParameter("placeNumber", placeNumber)
                    .addParameter("sessionId", sessionId);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    /**
     * Удаляет билет {@link Ticket}, найденный в таблице tickets.
     * @param id - ID билета.
     * @return true - если количество измененных строк > 0, иначе false.
     */
    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM tickets WHERE id = :id")
                    .addParameter("id", id);
            var affectedRows = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    /**
     * Преобразует ResultSet из таблицы tickets в коллекцию.
     * @return коллекция билетов {@link Ticket}.
     */
    @Override
    public Collection<Ticket> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets");
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }
}
