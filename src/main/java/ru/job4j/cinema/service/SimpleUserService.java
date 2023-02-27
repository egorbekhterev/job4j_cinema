package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

/**
 * Реализация сервиса для пользователей {@link User}.
 * @author: Egor Bekhterev
 * @date: 25.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Service
public class SimpleUserService implements UserService {

    /**
     * Поле для обращения к хранилищу пользователей.
     */
    private final UserRepository userRepository;

    public SimpleUserService(UserRepository sql2oUserRepository) {
        this.userRepository = sql2oUserRepository;
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
        return userRepository.save(user);
    }

    /**
     * Возвращает контейнер пользователя {@link User}, найденного в таблице users по первому совпадению параметров.
     * @param email - электронный почтовый адрес пользователя
     * @param password - пароль от учетной записи
     * @return контейнер пользователя {@link User}
     */
    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
