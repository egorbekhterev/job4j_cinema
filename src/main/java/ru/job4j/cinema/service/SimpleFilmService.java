package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис собирает данные для представления FilmLibrary.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository sql2oGenreRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = sql2oGenreRepository;
    }

    /**
     * Получает строковое представление жанра фильма, используя GenreRepository.
     * Для экземпляра Film доступно только id жанра.
     * @param film - Экземпляр класса Film {@link Film}
     * @return Жанр фильма
     */
    private String getGenre(Film film) {
        var genreOptional = genreRepository.findById(film.getGenreId());
        if (genreOptional.isEmpty()) {
            return "No genre with this id is presented.";
        }
        return genreOptional.get().getName();
    }

    /**
     * Преобразует Film в FilmDto.
     * @param film - Экземпляр класса Film {@link Film}
     * @return экземпляр FilmDto {@link FilmDto}
     */
    private FilmDto transformFilmToFilmDto(Film film) {
        return new FilmDto(film.getName(), film.getDescription(), film.getYear(), film.getMinimalAge(),
                film.getDurationInMinutes(), film.getFileId(), getGenre(film));
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        var filmOptional = filmRepository.findById(id);
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(transformFilmToFilmDto(filmOptional.get()));
    }

    @Override
    public Collection<FilmDto> getAll() {
        return filmRepository.getAll().stream()
                .map(this::transformFilmToFilmDto).collect(Collectors.toList());
    }
}
