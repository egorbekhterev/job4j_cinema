package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FileDto;

import java.util.Optional;

/**
 * Интерфейс для сервисов, работающих с файлами {@link FileDto}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
public interface FileService {

    Optional<FileDto> getFileById(int id);
}
