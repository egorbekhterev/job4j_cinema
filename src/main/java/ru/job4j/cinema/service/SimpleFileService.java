package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Реализация сервиса для файлов {@link FileDto}.
 * Сервис выполняет бизнес-логику по чтению содержимого файлов для вывода в представлении library/list.html.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Service
public class SimpleFileService implements FileService {

    /**
     * Поле для обращения к хранилищу файлов.
     */
    private final FileRepository fileRepository;

    public SimpleFileService(FileRepository sql2oFileRepository) {
        this.fileRepository = sql2oFileRepository;
    }

    /**
     * Считывает содержимое файла.
     * @param path - относительный путь к файлу.
     * @return содержимое файла в виде массива байтов.
     */
    private byte[] readFileBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает контейнер DTO-файла {@link FileDto}, найденного в таблице files по первому совпадению с ID.
     * @param id ID искомого файла.
     * @return контейнер файла {@link FileDto}
     */
    @Override
    public Optional<FileDto> getFileById(int id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }
}
