package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Тестовый класс для Sql2o {@link Sql2o} реализации репозитория билетов {@link Ticket}
 * @author: Egor Bekhterev
 * @date: 26.02.2023
 * @project: job4j_cinema
 */
public class Sql2oTicketRepositoryTest {

    /**
     * Статический объект класса для тестирования {@link Sql2oTicketRepository}.
     */
    private static Sql2oTicketRepository sql2oTicketRepository;

    /**
     * Считывает настройки к тестовой БД из connection.properties. Создает пул соединений и клиент БД Sql2o.
     * После этого создает репозиторий {@link Sql2oTicketRepository}.
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    /**
     * Для выполнения изолированности тестирования удаляет билеты после каждого теста.
     */
    @AfterEach
    public void clearTickets() {
        var tickets = sql2oTicketRepository.findAll();
        for (var ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getId());
        }
    }

    /**
     * Проверяет полное совпадение свойств билета после добавления в таблицу.
     */
    @Test
    public void whenSaveThenGetSame() {
        var ticket = sql2oTicketRepository.save(
                new Ticket(0, 1, 1, 1, 1)).get();
        var savedTicket = sql2oTicketRepository
                .findBySessionAndRowAndPlace(ticket.getSessionId(), ticket.getRowNumber(),
                        ticket.getPlaceNumber()).get();
        assertThat(savedTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    /**
     * Проверяет наличие всех билетов, добавленных в репозиторий.
     */
    @Test
    public void whenSaveSeveralThenGetAll() {
        var ticket1 = sql2oTicketRepository.save(new Ticket(0, 1, 1, 1, 1)).get();
        var ticket2 = sql2oTicketRepository.save(new Ticket(0, 2, 2, 2, 2)).get();
        var ticket3 = sql2oTicketRepository.save(new Ticket(0, 3, 3, 3, 3)).get();
        var result = sql2oTicketRepository.findAll();
        assertThat(result).isEqualTo(List.of(ticket1, ticket2, ticket3));
    }

    /**
     * Проверяет невозможность сохранения в репозиторий уже существующего билета.
     */
    @Test
    public void whenSaveUsersWithIdenticalSessionIdRowAndSeat() {
        var ticket = sql2oTicketRepository.save(new Ticket(0, 1, 1, 1, 1));
        sql2oTicketRepository.save(new Ticket(0, 1, 1, 1, 2));
        var rsl = sql2oTicketRepository.findBySessionAndRowAndPlace(1, 1, 1);
        assertThat(rsl).isEqualTo(ticket);
    }

    /**
     * Проверяет отсутствие объектов в репозитории по умолчанию.
     */
    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oTicketRepository.findAll()).isEqualTo(emptyList());
    }

    /**
     * Проверяет отстутствие объекта в репозитории после удаления.
     */
    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var ticket = sql2oTicketRepository.save(new Ticket(0, 1, 1, 1, 1)).get();
        var isDeleted = sql2oTicketRepository.deleteById(ticket.getId());
        var savedTicket = sql2oTicketRepository
                .findBySessionAndRowAndPlace(ticket.getSessionId(), ticket.getRowNumber(), ticket.getPlaceNumber());
        assertThat(isDeleted).isTrue();
        assertThat(savedTicket).isEqualTo(empty());
    }

    /**
     * Проверяет, что при удалении несуществующего объекта из репозитория, метод delteByid() {@link Sql2oTicketRepository},
     * возвращает False.
     */
    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oTicketRepository.deleteById(0)).isFalse();
    }
}
