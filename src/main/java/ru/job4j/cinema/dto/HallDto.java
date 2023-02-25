package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.Hall;

import java.util.Collection;

/**
 * Класс реализует DTO объект для кинозалов {@link Hall}.
 * @author: Egor Bekhterev
 * @date: 24.02.2023
 * @project: job4j_cinema
 */
public class HallDto {

    private int id;
    private String name;
    /**
     * Поле содержит список рядов доступных в зале.
     */
    private Collection<Integer> rowCountCollection;
    /**
     * Поле содержит список мест доступных в зале.
     */
    private Collection<Integer> placeCountCollection;
    private String description;

    public HallDto(int id, String name, Collection<Integer> rowCountCollection,
                   Collection<Integer> placeCountCollection, String description) {
        this.id = id;
        this.name = name;
        this.rowCountCollection = rowCountCollection;
        this.placeCountCollection = placeCountCollection;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Integer> getRowCountCollection() {
        return rowCountCollection;
    }

    public void setRowCountCollection(Collection<Integer> rowCountCollection) {
        this.rowCountCollection = rowCountCollection;
    }

    public Collection<Integer> getPlaceCountCollection() {
        return placeCountCollection;
    }

    public void setPlaceCountCollection(Collection<Integer> placeCountCollection) {
        this.placeCountCollection = placeCountCollection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
