package otus.hw12.webserver.servlet;

public class Response {
    private String msg;

    private int statusCode;

    Response(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
