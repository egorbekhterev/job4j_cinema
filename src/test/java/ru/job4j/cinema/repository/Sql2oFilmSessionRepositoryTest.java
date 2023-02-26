package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.FilmSession;

import java.util.ArrayList;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестовый класс для Sql2o {@link Sql2o} реализации репозитория киносеансов {@link FilmSession}
 * @author: Egor Bekhterev
 * @date: 26.02.2023
 * @project: job4j_cinema
 */
public class Sql2oFilmSessionRepositoryTest {

    /**
     * Статический объект класса для тестирования {@link Sql2oFilmSessionRepository}.
     */
    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    /**
     * Считывает настройки к тестовой БД из connection.properties. Создает пул соединений и клиент БД Sql2o.
     * После этого создает репозиторий {@link Sql2oFilmSessionRepository}.
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    /**
     * Проверяет наличие киносеанса по ID в репозитории.
     */
    @Test
    public void whenFindById() {
        var filmSession = sql2oFilmSessionRepository.findById(1).get();
        assertThat(filmSession.getFilmId()).isEqualTo(1);
        assertThat(filmSession.getHallId()).isEqualTo(3);
        assertThat(filmSession.getPrice()).isEqualTo(1500);
    }

    /**
     * Проверяет наличие всех киносеансов в репозитории.
     */
    @Test
    public void whenFindAll() {
        var actual = sql2oFilmSessionRepository.getAll();
        var expected = new ArrayList<>();
        for (int i = 1; i <= actual.size(); i++) {
            expected.add(sql2oFilmSessionRepository.findById(i).get());
        }
        assertThat(actual).isEqualTo(expected);
    }
}
