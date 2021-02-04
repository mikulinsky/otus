package otus.hw09.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw09.jdbc.models.Account;
import otus.hw09.jdbc.models.Client;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        try {
            logger.info("Create tables");
            Client.createTable();
            Account.createTable();

            logger.info("Insert data");
            Client client = new Client("test", 12);
            client.save();
            Account account = new Account("qwe", "wqe", 1.1F);
            account.save();

            logger.info("Client {} with id {} in database", client.getName(), client.getId());
            logger.info("Account {} with id {} in database", account.getType(), account.getAccountId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}