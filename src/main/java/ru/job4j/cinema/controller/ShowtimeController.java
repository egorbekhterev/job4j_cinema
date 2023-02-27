package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.service.FilmSessionService;

/**
 * Контроллер для расписания киносеансов.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Controller
@RequestMapping("/showtime")
public class ShowtimeController {

    private final FilmSessionService filmSessionService;

    public ShowtimeController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    /**
     * GET-метод для отображения объектов {@link FilmSessionDto} в расписании киносеансов.
     * @param model - модель для сборки представления из списка киносеансов.
     * @return путь к представлению.
     */
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("shows", filmSessionService.findAll());
        return "showtime/list";
    }
}
