package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тест класса для контроллера файлов {@link FileController}
 * @author: Egor Bekhterev
 * @date: 27.02.2023
 * @project: job4j_cinema
 */
public class FileControllerTest {

    private FileService fileService;

    private FileController fileController;

    /**
     * Создаёт мок зависимости и тестируемый класс.
     */
    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    /**
     * Проверяет наличие существующего файла по запросу и получаемый status_code.
     */
    @Test
    public void whenRequestAndFileIsFound() {
        var body = new byte[] {1, 2, 3};
        var file = Optional.of(new FileDto("file", body));
        when(fileService.getFileById(any(Integer.class))).thenReturn(file);

        var view = fileController.getById(any(Integer.class));
        assertThat(view.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(view.getBody()).isEqualTo(body);
    }

    /**
     * Проверяет, что при запросе к несуществующему файлу метод не имеет содержимого, при этом status_code = 404.
     */
    @Test
    public void whenRequestAndFileIsNotFound() {
        when(fileService.getFileById(any(Integer.class))).thenReturn(Optional.empty());

        var view = fileController.getById(any(Integer.class));
        assertThat(view.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(view.getBody()).isNull();
    }
}
