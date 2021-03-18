package otus.hw12.webserver.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import otus.hw12.webserver.core.service.DBServiceUser;
import otus.hw12.webserver.services.TemplateProcessor;
import otus.hw12.webserver.services.UserAuthService;
import otus.hw12.webserver.servlet.AuthorizationFilter;
import otus.hw12.webserver.servlet.LoginServlet;

import java.util.Arrays;

public class UsersWebServerWithFilterBasedSecurity extends UsersWebServerSimple {
    private final UserAuthService authService;
    private final DBServiceUser dbServiceUser;

    public UsersWebServerWithFilterBasedSecurity(int port,
                                                 UserAuthService authService,
                                                 DBServiceUser dbServiceUser,
                                                 Gson gson,
                                                 TemplateProcessor templateProcessor) {
        super(port, dbServiceUser, gson, templateProcessor);
        this.authService = authService;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService, dbServiceUser)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}