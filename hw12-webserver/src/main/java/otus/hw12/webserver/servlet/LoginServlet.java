package otus.hw12.webserver.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import otus.hw12.webserver.core.model.User;
import otus.hw12.webserver.core.model.UserRole;
import otus.hw12.webserver.core.service.DBServiceUser;
import otus.hw12.webserver.services.TemplateProcessor;
import otus.hw12.webserver.services.UserAuthService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class LoginServlet extends HttpServlet {

    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final int MAX_INACTIVE_INTERVAL = 30;
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private static final HashMap<UserRole, String> urlByUserRole = new HashMap<>(){{
        put(UserRole.USER, "/users");
        put(UserRole.ADMIN, "/admin");
    }};


    private final TemplateProcessor templateProcessor;
    private final UserAuthService userAuthService;
    private final DBServiceUser dbServiceUser;

    public LoginServlet(TemplateProcessor templateProcessor, UserAuthService userAuthService, DBServiceUser dbServiceUser) {
        this.userAuthService = userAuthService;
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, Collections.emptyMap()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);

        if (userAuthService.authenticate(name, password)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
            Optional<User> user = dbServiceUser.findByName(name);
            if (user.isPresent()) {
                UserRole role = user.get().getRole();
                response.sendRedirect(urlByUserRole.get(role));
            } else {
                response.setStatus(SC_UNAUTHORIZED); // при условии, что пользователь прошел аутентификацию, недостижимое состояние
            }
        } else {
            response.setStatus(SC_UNAUTHORIZED);
        }

    }

}