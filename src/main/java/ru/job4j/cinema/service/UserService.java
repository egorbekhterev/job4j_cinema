package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import java.util.Optional;

/**
 * Интерфейс для сервисов, работающих с пользователями {@link User}.
 * @author: Egor Bekhterev
 * @date: 25.02.2023
 * @project: job4j_cinema
 */
public interface UserService {

    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);
}
