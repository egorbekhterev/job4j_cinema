package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс для хранилищ пользователей {@link User}.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean deleteById(int id);

    Collection<User> findAll();
}
