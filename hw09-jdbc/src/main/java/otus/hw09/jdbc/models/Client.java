package otus.hw09.jdbc.models;

import otus.hw09.jdbc.annotation.Id;
import otus.hw09.jdbc.db.DBExecutor;

import java.math.BigInteger;
import java.sql.SQLException;

public class Client {
    @Id
    private BigInteger id;
    private String name;
    private Integer age;

    public Client(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public BigInteger getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void save() throws SQLException, ClassNotFoundException {
        DBExecutor<Client> dbExecutor = new DBExecutor<>(Client.class);
        dbExecutor.insert(this);
    }

    public static Client findById(Integer id) throws SQLException, ClassNotFoundException {
        DBExecutor<Client> dbExecutor = new DBExecutor<>(Client.class);
        return dbExecutor.select(id);
    }

    public static void createTable() throws SQLException, ClassNotFoundException {
        DBExecutor<Client> dbExecutor = new DBExecutor<>(Client.class);
        dbExecutor.createTable();
    }
}
