package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Тестовый класс для контроллера домашней страницы {@link IndexController}.
 * @author: Egor Bekhterev
 * @date: 26.02.2023
 * @project: job4j_cinema
 */
public class IndexControllerTest {

    /**
     * Проверяет получение домашней страницы.
     */
    @Test
    public void whenGetIndex() {
        var actual = new IndexController().getIndex();
        assertThat(actual).isEqualTo("index");
    }
}
