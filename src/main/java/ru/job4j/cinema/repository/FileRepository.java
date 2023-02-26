package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.File;
import java.util.Optional;

/**
 * Интерфейс для хранилищ файлов {@link File}.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public interface FileRepository {

    Optional<File> findById(int id);
}
