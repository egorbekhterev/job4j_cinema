package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.File;

import java.util.Optional;

/**
 * Реализация репозитория для файлов {@link File}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Repository
public class Sql2oFileRepository implements FileRepository {

    /**
     * Экземпляр ORM.
     */
    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Возвращает контейнер файла {@link File}, найденного в таблице files по первому совпадению с ID.
     * @param id ID искомого файла.
     * @return контейнер файла {@link File}
     */
    @Override
    public Optional<File> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM files WHERE id = :id");
            var file = query.addParameter("id", id).executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }
    }
}
