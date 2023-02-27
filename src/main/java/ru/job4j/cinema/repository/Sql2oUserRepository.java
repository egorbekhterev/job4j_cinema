package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация репозитория для пользователей {@link User}.
 * @author: Egor Bekhterev
 * @date: 25.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Repository
public class Sql2oUserRepository implements UserRepository {

    /**
     * Экземпляр ORM.
     */
    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Выполняет сохранение данных пользователя {@link User} в таблицу users.
     * Устанавливает ID для этого пользователя.
     * При попытке зарегистрировать пользователя с существующим email'ом возвращает пустой Optional.
     * @param user экземпляр пользователя {@link User}
     * @return контейнер пользователя {@link User}
     */
    @Override
    public Optional<User> save(User user) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO users (full_name, email, password) VALUES (:fullName, :email, :password)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("fullName", user.getFullName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Sql2oException e) {
            return Optional.empty();
        }
    }

    /**
     * Возвращает контейнер пользователя {@link User}, найденного в таблице users по первому совпадению параметров.
     * @param email - электронный почтовый адрес пользователя
     * @param password - пароль от учетной записи
     * @return контейнер пользователя {@link User}
     */
    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = sql2o.open()) {
            var query = connection
                    .createQuery("SELECT * FROM users WHERE email = :email AND password = :password")
                    .addParameter("email", email)
                    .addParameter("password", password);
            var user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    /**
     * Удаляет пользователя {@link User}, найденного в таблице users.
     * @param id - ID пользователя.
     * @return true - если количество измененных строк > 0, иначе false.
     */
    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users WHERE id = :id")
                    .addParameter("id", id);
            var affectedRows = query.setColumnMappings(User.COLUMN_MAPPING).executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    /**
     * Преобразует ResultSet из таблицы users в коллекцию.
     * @return коллекция пользователей {@link User}.
     */
    @Override
    public Collection<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users");
            return query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
        }
    }
}
