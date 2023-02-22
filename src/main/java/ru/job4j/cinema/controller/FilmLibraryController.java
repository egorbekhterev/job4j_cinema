package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.FilmService;

/**
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */

@Controller
@RequestMapping("/library")
public class FilmLibraryController {

    private final FilmService filmService;

    public FilmLibraryController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.getAll());
        return "library/list";
    }
}
