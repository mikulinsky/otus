package otus.hw12.webserver.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}