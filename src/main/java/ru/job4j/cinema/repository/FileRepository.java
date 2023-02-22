package ru.job4j.cinema.repository;

import java.io.File;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public interface FileRepository {

    Optional<File> findById(int id);
}
