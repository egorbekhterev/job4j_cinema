package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.HallDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Реализация сервиса для кинозалов {@link HallDto}.
 * Сервис собирает данные для представления cashdesk/buy.html.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Service
public class SimpleHallService implements HallService {

    /**
     * Поле для обращения к хранилищу кинозалов.
     */
    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository sql2oHallRepository) {
        this.hallRepository = sql2oHallRepository;
    }

    /**
     * Метод создает коллекцию Integer-объектов из максимального количества рядов в кинозале, для того, чтобы
     * реализовать выбор ряда в виде списка в представлении.
     * @param hall - экземпляр класса Hall {@link Hall}
     * @return коллекция Integer-объектов.
     */
    private Collection<Integer> getRowCountCollection(Hall hall) {
        return IntStream.rangeClosed(1, hall.getRowCount()).boxed().toList();
    }

    /**
     * Метод создает коллекцию Integer-объектов из максимального количества места в ряду в кинозале, для того, чтобы
     * реализовать выбор места в виде списка в представлении.
     * @param hall - экземпляр класса Hall {@link Hall}
     * @return коллекция Integer-объектов.
     */
    private Collection<Integer> getPlaceCountCollection(Hall hall) {
        return IntStream.rangeClosed(1, hall.getPlaceCount()).boxed().toList();
    }

    /**
     * Преобразует Hall в HallDto.
     * @param hall - Экземпляр класса Hall {@link Hall}
     * @return экземпляр HallDto {@link HallDto}
     */
    private HallDto transformToHallDto(Hall hall) {
        return new HallDto(hall.getId(), hall.getName(), getRowCountCollection(hall), getPlaceCountCollection(hall),
                hall.getDescription());
    }

    /**
     * Возвращает контейнер DTO-кинозала {@link HallDto}, найденного в таблице halls по первому совпадению с ID.
     * @param id ID искомого кинозала.
     * @return контейнер DTO-кинозала {@link HallDto}
     */
    @Override
    public Optional<HallDto> findById(int id) {
        var hallOptional = hallRepository.findById(id);
        if (hallOptional.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(transformToHallDto(hallOptional.get()));
    }
}
