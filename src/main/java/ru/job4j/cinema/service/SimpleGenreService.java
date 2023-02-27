package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.Optional;

/**
 * Реализация сервиса для жанров {@link Genre}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Service
public class SimpleGenreService implements GenreService {

    /**
     * Поле для обращения к хранилищу жанров.
     */
    private final GenreRepository genreRepository;

    public SimpleGenreService(GenreRepository sql2oGenreRepository) {
        this.genreRepository = sql2oGenreRepository;
    }

    /**
     * Возвращает контейнер жанра {@link Genre}, найденного в таблице genres по первому совпадению с ID.
     * @param id ID искомого жанра.
     * @return контейнер жанра {@link Genre}
     */
    @Override
    public Optional<Genre> findById(int id) {
        return genreRepository.findById(id);
    }
}
