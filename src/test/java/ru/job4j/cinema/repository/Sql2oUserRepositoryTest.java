package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.User;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Тестовый класс для Sql2o {@link Sql2o} реализации репозитория пользователей {@link User}
 * @author: Egor Bekhterev
 * @date: 26.02.2023
 * @project: job4j_cinema
 */
public class Sql2oUserRepositoryTest {

    /**
     * Статический объект класса для тестирования {@link Sql2oUserRepository}.
     */
    private static Sql2oUserRepository sql2oUserRepository;

    /**
     * Считывает настройки к тестовой БД из connection.properties. Создает пул соединений и клиент БД Sql2o.
     * После этого создает репозиторий {@link Sql2oUserRepository}.
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    /**
     * Для выполнения изолированности тестирования удаляет пользователей после каждого теста.
     */
    @AfterEach
    public void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    /**
     * Проверяет полное совпадение свойств пользователя после добавления в таблицу.
     */
    @Test
    public void whenSaveThenGetSame() {
        var user = sql2oUserRepository.save(new User(0, "a", "a@example.com", "123456")).get();
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    /**
     * Проверяет наличие всех пользователей, добавленных в репозиторий.
     */
    @Test
    public void whenSaveSeveralThenGetAll() {
        var user1 = sql2oUserRepository.save(new User(0, "a", "a@example.com", "123")).get();
        var user2 = sql2oUserRepository.save(new User(0, "b", "b@example.com", "456")).get();
        var user3 = sql2oUserRepository.save(new User(0, "c", "c@example.com", "789")).get();
        var result = sql2oUserRepository.findAll();
        assertThat(result).isEqualTo(List.of(user1, user2, user3));
    }

    /**
     * Проверяет невозможность сохранения в репозиторий пользователя с уже существующими в таблице email.
     */
    @Test
    public void whenSaveUsersWithIdenticalEmail() {
        var user = sql2oUserRepository.save(new User(0, "a", "a@example.com", "123"));
        sql2oUserRepository.save(new User(0, "b", "a@example.com", "456"));
        var rsl = sql2oUserRepository.findByEmailAndPassword("a@example.com", "123");
        var result = sql2oUserRepository.findByEmailAndPassword("a@example.com", "456");
        assertThat(rsl).isEqualTo(user);
        assertThat(result).isEmpty();
    }

    /**
     * Проверяет отсутствие объектов в репозитории по умолчанию.
     */
    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oUserRepository.findAll()).isEqualTo(emptyList());
    }

    /**
     * Проверяет отстутствие объекта в репозитории после удаления.
     */
    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var user = sql2oUserRepository.save(new User(0, "a@a.ru", "a", "123")).get();
        var isDeleted = sql2oUserRepository.deleteById(user.getId());
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(isDeleted).isTrue();
        assertThat(savedUser).isEqualTo(empty());
    }

    /**
     * Проверяет, что при удалении несуществующего объекта из репозитория, метод delteByid() {@link Sql2oUserRepository},
     * возвращает False.
     */
    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oUserRepository.deleteById(0)).isFalse();
    }
}
