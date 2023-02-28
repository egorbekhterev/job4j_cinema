package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import ru.job4j.cinema.model.User;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для контроллера пользователей {@link UserController}
 * @author: Egor Bekhterev
 * @date: 27.02.2023
 * @project: job4j_cinema
 */
public class UserControllerTest {

    private UserService userService;

    private UserController userController;

    private HttpServletRequest httpServletRequest;

    private HttpSession httpSession;

    /**
     * Создаёт моки зависимостей и тестируемый класс.
     */
    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        httpServletRequest = mock(HttpServletRequest.class);
        httpSession = mock(HttpSession.class);
    }

    /**
     * Проверяет получение представления страницы регистрации пользователя {@link User}.
     */
    @Test
    public void whenRequestForRegistrationPage() {
        var actual = userController.getRegistrationPage();
        assertThat(actual).isEqualTo("users/register");
    }

    /**
     * Проверяет, что по выполнению регистрации произведено перенаправление на страницу с расписанием киносеансов и
     * пользователь был зарегистрирован.
     */
    @Test
    public void whenRegistrationOfUserIsSuccessful() {
        var user = Optional.of(new User(0, "A A A", "a@example.com", "password"));
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(user);

        var model = new ConcurrentModel();
        var view = userController.register(model, user.get());
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/showtime");
        assertThat(actualUser).isEqualTo(user.get());
    }

    /**
     * Проверяет, что при регистрации пользователя с существующим email выполняется переход на страницу с ошибкой,
     * а model содержит описание для ошибки.
     */
    @Test
    public void whenUserAlreadyExistAndRedirectToErrorPage() {
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(model, new User());
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo("A user with this email already exists.");
    }

    /**
     * Проверяет получение представления страницы для входа в систему.
     */
    @Test
    public void whenRequestForLoginPage() {
        var actual = userController.getLoginPage();
        assertThat(actual).isEqualTo("users/login");
    }

    /**
     * Проверяет получение нужного представления при успешном входе в систему и сохранение пользовательской сессии.
     */
    @Test
    public void whenUserLoggingIsSuccessful() {
        var user = Optional.of(new User(0, "A A A", "a@example.com", "password"));

        when(userService.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(user);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpServletRequest.getSession().getAttribute("user")).thenReturn(user.get());

        var model = new ConcurrentModel();
        var view = userController.loginUser(user.get(), model, httpServletRequest);
        var actualUser = (User) httpServletRequest.getSession().getAttribute("user");

        assertThat(view).isEqualTo("redirect:/showtime");
        assertThat(actualUser).isEqualTo(user.get());
    }

    /**
     * Проверяет перевод на страницу входа в систему, при вводе несуществующих данных пользователя и содержание
     * в model описания ошибки.
     */
    @Test
    public void whenUserLoggingAndRedirectToErrorPage() {
        when(userService.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(new User(), model, httpServletRequest);
        var actualExceptionMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualExceptionMessage).isEqualTo("Email or password entered incorrectly.");
    }

    /**
     * Проверяет, что выполнено перенаправление на страницу входа в систему после выхода пользователя из сессии.
     */
    @Test
    public void whenLogoutRequestIsMade() {
        var view = userController.logout(httpSession);
        assertThat(view).isEqualTo("redirect:/users/login");
    }
}
