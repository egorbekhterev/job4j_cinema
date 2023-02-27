package ru.job4j.cinema.filter;

import net.jcip.annotations.ThreadSafe;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;
import ru.job4j.cinema.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Класс-фильтр для связи авторизованного пользователя с другими страницами веб-приложения.
 * @author: Egor Bekhterev
 * @date: 25.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Component
@Order(2)
public class SessionFilter extends HttpFilter {

    /**
     * Добавляет {@link User} в пользовательскую сессию. Если в {@link HttpSession} объект отсутствует, создается
     * объект User с анонимным пользователем.
     * @param session - пользовательская сессия.
     * @param request - запрос. Добавляем пользователя в model.
     */
    private void addUserToSession(HttpSession session, HttpServletRequest request) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setFullName("Guest");
        }
        request.setAttribute("user", user);
    }

    /**
     * Получает {@link HttpSession}. Добавляет пользователя в сессию. Вызывает следующий в цепочке фильтр.
     * @param request - запрос.
     * @param response - ответ.
     * @param chain- фильтр из цепочки фильтров.
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var session = request.getSession();
        addUserToSession(session, request);
        chain.doFilter(request, response);
    }
}
