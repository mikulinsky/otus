package otus.hw12.webserver.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw12.webserver.core.model.User;
import otus.hw12.webserver.core.service.DBServiceUser;

import java.io.BufferedReader;
import java.io.IOException;

public class UsersApiServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DBServiceUser dbServiceUser;
    private final Gson gson;

    public UsersApiServlet(DBServiceUser dbServiceUser, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = dbServiceUser.findById(extractIdFromRequest(request)).orElse(null);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(user));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json;charset=UTF-8");
        logger.info("get post request");
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);
        logger.info("Get user {}", user);
        if (user.getName().equals("")) {
            this.sendResponse("Empty login is forbidden", HttpServletResponse.SC_BAD_REQUEST, response);
            return;
        }

        if (dbServiceUser.findByName(user.getName()).isPresent()) {
            this.sendResponse("User " + user.getName() + " exists", HttpServletResponse.SC_BAD_REQUEST, response);
            return;
        }

        dbServiceUser.saveUser(user);
        logger.info("User created: {}", user);
        this.sendResponse("User " + user.getName() + " created", HttpServletResponse.SC_CREATED, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json;charset=UTF-8");
        logger.info("get put request");
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);
        logger.info("Get user {}", user);
        if (dbServiceUser.findByName(user.getName()).isEmpty()) {
            this.sendResponse("User " + user.getName() + " not exists", HttpServletResponse.SC_NOT_FOUND, response);
            return;
        }

        dbServiceUser.saveUser(user);
        logger.info("User updated: {}", user);
        this.sendResponse("User " + user.getName() + " updated", HttpServletResponse.SC_OK, response);
    }

    private void sendResponse(String msg, int statusCode, HttpServletResponse response) throws IOException {
        response.setStatus(statusCode);
        var apiResponse = new Response(msg, statusCode);
        response.getOutputStream().print(gson.toJson(apiResponse, Response.class));
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

}
