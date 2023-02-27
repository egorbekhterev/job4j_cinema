package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

/**
 * Тестовый класс для Sql2o {@link Sql2o} реализации репозитория фильмов {@link Film}
 * @author: Egor Bekhterev
 * @date: 26.02.2023
 * @project: job4j_cinema
 */
public class Sql2oFilmRepositoryTest {

    /**
     * Статический объект класса для тестирования {@link Sql2oFilmRepository}.
     */
    private static Sql2oFilmRepository sql2oFilmRepository;

    /**
     * Считывает настройки к тестовой БД из connection.properties. Создает пул соединений и клиент БД Sql2o.
     * После этого создает репозиторий {@link Sql2oFilmRepository}.
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    /**
     * Проверяет наличие фильма по ID в репозитории.
     */
    @Test
    public void whenFindById() {
        var film = sql2oFilmRepository.findById(1).get();
        assertThat(film.getName()).isEqualTo("Drunk");
        assertThat(film.getYear()).isEqualTo(2020);
        assertThat(film.getDurationInMinutes()).isEqualTo(117);
    }

    /**
     * Проверяет наличие всех фильмов в репозитории.
     */
    @Test
    public void whenFindAll() {
        var actual = sql2oFilmRepository.findAll();
        assertThat(actual).isEqualTo(List.of(sql2oFilmRepository.findById(1).get(),
                sql2oFilmRepository.findById(2).get(), sql2oFilmRepository.findById(3).get(),
                sql2oFilmRepository.findById(4).get(), sql2oFilmRepository.findById(5).get()));
    }
}
