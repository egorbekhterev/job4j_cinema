package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тест класса для контроллера {@link FilmLibraryController}
 * @author: Egor Bekhterev
 * @date: 27.02.2023
 * @project: job4j_cinema
 */
public class FilmLibraryControllerTest {

    private FilmService filmService;

    private FilmLibraryController filmLibraryController;

    /**
     * Создаёт мок зависимости и тестируемый класс.
     */
    @BeforeEach
    public void initServices() {
        filmService = mock(FilmService.class);
        filmLibraryController = new FilmLibraryController(filmService);
    }

    /**
     * Проверяет получение представления и наличие ожидаемого фильма в коллекции.
     */
    @Test
    public void whenRequestFilmListPageThenGetPageWithFilms() {
        var film = new FilmDto("Dune", "A noble family becomes embroiled in a war for control over"
                + " the galaxy`s most valuable asset while its heir\n"
                + "becomes troubled by visions of a dark future.", 2021, 3, 13, 155,
                "Science Fiction");
        var expectedFilms = List.of(film);
        when(filmService.findAll()).thenReturn(expectedFilms);

        var model = new ConcurrentModel();
        var view = filmLibraryController.getAll(model);
        var actualFilms = model.getAttribute("films");

        assertThat(view).isEqualTo("library/list");
        assertThat(actualFilms).isEqualTo(expectedFilms);
    }
}
