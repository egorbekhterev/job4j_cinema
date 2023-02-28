package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.service.FilmSessionService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для контроллера {@link ShowtimeController}
 * @author: Egor Bekhterev
 * @date: 27.02.2023
 * @project: job4j_cinema
 */
public class ShowtimeControllerTest {

    private FilmSessionService filmSessionService;
    private ShowtimeController showtimeController;

    /**
     * Создаёт мок зависимости и тестируемый класс.
     */
    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        showtimeController = new ShowtimeController(filmSessionService);
    }

    /**
     * Проверяет получение представления и наличие ожидаемого киносеанса в коллекции.
     */
    @Test
    public void whenRequestFilmSessionListPageThenGetPageWithFilmSessions() {
        var filmSession = new FilmSessionDto(0, "Dune", "Cinema Hall 1", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2), 500);
        var expectedFilmSessions = List.of(filmSession);
        when(filmSessionService.findAll()).thenReturn(expectedFilmSessions);

        var model = new ConcurrentModel();
        var view = showtimeController.getAll(model);
        var actualFilmSessions = model.getAttribute("shows");

        assertThat(view).isEqualTo("showtime/list");
        assertThat(actualFilmSessions).isEqualTo(expectedFilmSessions);
    }
}
