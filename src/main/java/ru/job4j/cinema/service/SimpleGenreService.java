package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@Service
public class SimpleGenreService implements GenreService {

    private final GenreRepository genreRepository;

    public SimpleGenreService(GenreRepository sql2oGenreRepository) {
        this.genreRepository = sql2oGenreRepository;
    }

    @Override
    public Optional<Genre> findById(int id) {
        return genreRepository.findById(id);
    }
}
