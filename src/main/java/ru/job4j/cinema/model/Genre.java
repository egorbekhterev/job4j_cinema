package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Доменная модель данных, описывающая жанры.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public class Genre {

    private int id;
    private String name;

    public Genre(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genre genre = (Genre) o;
        return id == genre.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
