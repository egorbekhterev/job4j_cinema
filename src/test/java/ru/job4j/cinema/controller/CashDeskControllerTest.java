package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.HallDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для контроллера онлайн-кассы {@link CashDeskController}
 * @author: Egor Bekhterev
 * @date: 27.02.2023
 * @project: job4j_cinema
 */
public class CashDeskControllerTest {

    private TicketService ticketService;

    private CashDeskController cashDeskController;

    private FilmSessionService filmSessionService;

    private HallService hallService;

    /**
     * Создаёт моки зависимостей и тестируемый класс.
     */
    @BeforeEach
    public void initServices() {
        ticketService = mock(TicketService.class);
        filmSessionService = mock(FilmSessionService.class);
        hallService = mock(HallService.class);
        cashDeskController = new CashDeskController(ticketService, filmSessionService, hallService);
    }

    /**
     * Проверяет получение страницы покупки билетов и наличие всех атрибутов модели.
     */
    @Test
    public void whenRequestTicketPurchasePageThenGetIt() {
        var list = List.of(1, 2, 3);
        var filmSession = Optional.of(new FilmSession(0, 1, 1, LocalDateTime.now(),
                LocalDateTime.now().plusHours(2), 500));
        var hallDto = Optional.of(new HallDto(0, "Hall", list, list,
                "Simple Hall"));
        var filmSessionDto = Optional.of(new FilmSessionDto(0, "Dune", "Hall",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2), 500));

        when(filmSessionService.findById(any(Integer.class))).thenReturn(filmSession);
        when(hallService.findById(any(Integer.class))).thenReturn(hallDto);
        when(filmSessionService.findByIdDto(any(Integer.class))).thenReturn(filmSessionDto);

        var model = new ConcurrentModel();
        var view = cashDeskController.getById(model, any(Integer.class));
        var actualFilmSessionDto = model.getAttribute("filmSession");
        var actualRows = model.getAttribute("rows");
        var actualPlaces = model.getAttribute("places");

        assertThat(view).isEqualTo("cashdesk/buy");
        assertThat(actualFilmSessionDto).isEqualTo(filmSessionDto.get());
        assertThat(actualRows).isEqualTo(list);
        assertThat(actualPlaces).isEqualTo(list);
    }

    /**
     * Проверяет, что при отсутствии киносеанса {@link FilmSession} будет вызвано представление, содержащее
     * соответствующую ошибку.
     */
    @Test
    public void whenEmptyFilmSessionAndRedirectToErrorPage() {
        when(filmSessionService.findById(any(Integer.class))).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = cashDeskController.getById(model, any(Integer.class));
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo("No film session with the given ID was found.");
    }

    /**
     * Проверяет получение страницы с результатом об успешной покупке билетов и наличие атрибута модели.
     */
    @Test
    public void whenBoughtTicketAndItIsSuccessful() {
        var ticket = Optional.of(new Ticket(0, 1, 1, 1, 1));

        when(ticketService.save(ticket.get())).thenReturn(ticket);

        var model = new ConcurrentModel();
        var view = cashDeskController.buy(model, ticket.get());
        var actualTicket = model.getAttribute("ticket");

        assertThat(view).isEqualTo("cashdesk/successfulPurchase");
        assertThat(actualTicket).isEqualTo(ticket.get());
    }

    /**
     * Проверяет, что при попытке купить занятное место будет вызвано представление, содержащее
     * соответствующую ошибку.
     */
    @Test
    public void whenBoughtBookedTicketAndRedirectToErrorPage() {
        var ticket1 = Optional.of(new Ticket(0, 1, 1, 1, 1));
        var ticket2 = Optional.of(new Ticket(0, 1, 1, 1, 2));

        when(ticketService.save(ticket1.get())).thenReturn(ticket1);
        when(ticketService.save(ticket2.get())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        cashDeskController.buy(model, ticket1.get());
        var view = cashDeskController.buy(model, ticket2.get());
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo("It was not possible to purchase a ticket for a given seat. "
                + "It is probably already booked. Go to the ticket booking page and try again.");
    }
}
