package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.dto.FilmDto;

/**
 * Контроллер для кинотеки.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Controller
@RequestMapping("/library")
public class FilmLibraryController {

    private final FilmService filmService;

    public FilmLibraryController(FilmService filmService) {
        this.filmService = filmService;
    }

    /**
     * GET-метод для отображения объектов {@link FilmDto} в кинотеке.
     * @param model - модель для сборки представления из списка фильмов.
     * @return путь к представлению.
     */
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "library/list";
    }
}
