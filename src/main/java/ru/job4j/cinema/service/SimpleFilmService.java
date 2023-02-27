package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для фильмов {@link FilmDto}.
 * Сервис собирает данные для представления library/list.html.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Service
public class SimpleFilmService implements FilmService {

    /**
     * Поле для обращения к хранилищу фильмов.
     */
    private final FilmRepository filmRepository;

    /**
     * Поле для обращения к хранилищу жанров.
     */
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

    /**
     * Возвращает контейнер DTO-файла {@link FilmDto}, найденного в таблице films по первому совпадению с ID.
     * @param id ID искомого фильма.
     * @return контейнер фильма {@link FilmDto}
     */
    @Override
    public Optional<FilmDto> findById(int id) {
        var filmOptional = filmRepository.findById(id);
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(transformFilmToFilmDto(filmOptional.get()));
    }

    /**
     * Преобразует ResultSet из таблицы films в коллекцию.
     * @return коллекция DTO-фильмов {@link FilmDto}.
     */
    @Override
    public Collection<FilmDto> findAll() {
        return filmRepository.findAll().stream()
                .map(this::transformFilmToFilmDto).collect(Collectors.toList());
    }
}
