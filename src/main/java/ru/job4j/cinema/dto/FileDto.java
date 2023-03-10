package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.File;

/**
 * Класс реализует DTO объект для файлов {@link File}.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public class FileDto {

    private String name;

    /**
     * Поле содержит содержимое файла для передачи веб-клиенту.
     */
    private byte[] content;

    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
