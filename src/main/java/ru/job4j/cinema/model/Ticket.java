package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Доменная модель данных, описывающая билеты.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public class Ticket {

    private int id;
    private int sessionId;
    private int rowNumber;
    private int placeNumber;
    private int userId;

    public Ticket(int sessionId, int rowNumber, int placeNumber, int userId) {
        this.sessionId = sessionId;
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id && sessionId == ticket.sessionId && rowNumber == ticket.rowNumber
                && placeNumber == ticket.placeNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionId, rowNumber, placeNumber);
    }
}
