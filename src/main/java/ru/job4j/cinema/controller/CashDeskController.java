package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.model.FilmSession;

/**
 * Контроллер для онлайн-кассы.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Controller
@RequestMapping("/cashdesk")
public class CashDeskController {

    private final TicketService ticketService;

    private final FilmSessionService filmSessionService;

    private final HallService hallService;

    public CashDeskController(TicketService ticketService, FilmSessionService filmSessionService, HallService hallService) {
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
        this.hallService = hallService;
    }

    /**
     * GET-метод для отображения страницы покупки билетов. Создает страницу для киносеанса с соответствующим ID.
     * @param model - модель для сборки представления. С ее помощью извлекаются данные из сущностей FilmSession,
     *              HallDto, FilmSessionDto.
     * @param id - ID киносеанса {@link FilmSession}
     * @return путь к представлению.
     */
    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var filmSessionOptional = filmSessionService.findById(id);
        if (filmSessionOptional.isEmpty()) {
            model.addAttribute("message", "No film session with the given ID was found.");
            return "errors/404";
        }

        var hallId = filmSessionOptional.get().getHallId();
        var hallOptional = hallService.findById(hallId);
        model.addAttribute("rows", hallOptional.get().getRowCountCollection());
        model.addAttribute("places", hallOptional.get().getPlaceCountCollection());

        var filmSessionDtoOptional = filmSessionService.findByIdDto(id);
        model.addAttribute("filmSession", filmSessionDtoOptional.get());

        return "cashdesk/buy";
    }

    /**
     * POST-метод для покупки билета {@link Ticket} .
     * @param model - модель для сборки представления.  С ее помощью извлекаются данные из сущности {@link Ticket}.
     * @param ticket - объект Ticket, который будет сохранен в таблицу tickets.
     * @return путь к представлению.
     */
    @PostMapping("/buy")
    public String buy(Model model, @ModelAttribute Ticket ticket) {
        var savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            model.addAttribute("message", "It was not possible to purchase a ticket for a "
                    + "given seat. It is probably already booked. Go to the ticket booking page and try again.");
            return "errors/404";
        }
        model.addAttribute("ticket", ticket);
        return "cashdesk/successfulPurchase";
    }
}
