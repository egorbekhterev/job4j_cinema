package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

/**
 * Тестовый класс для Sql2o {@link Sql2o} реализации репозитория файлов {@link File}
 * @author: Egor Bekhterev
 * @date: 26.02.2023
 * @project: job4j_cinema
 */
public class Sql2oFileRepositoryTest {

    /**
     * Статический объект класса для тестирования {@link Sql2oFileRepository}.
     */
    private static Sql2oFileRepository sql2oFileRepository;

    /**
     * Считывает настройки к тестовой БД из connection.properties. Создает пул соединений и клиент БД Sql2o.
     * После этого создает репозиторий {@link Sql2oFileRepository}.
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFileRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    /**
     * Проверяет наличие файла по ID в репозитории.
     */
    @Test
    void whenFindById() {
        var file = sql2oFileRepository.findById(1).get();
        assertThat(file.getName()).isEqualTo("Drunk");
    }
}
