package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для домашней страницы.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Controller
public class IndexController {

    /**
     * GET запрос для домашней страницы.
     * @return возвращает представление для главной страницы.
     */
    @GetMapping({"/", "/index"})
    public String getIndex() {
        return "index";
    }
}
