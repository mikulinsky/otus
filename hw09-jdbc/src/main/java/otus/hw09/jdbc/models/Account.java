package otus.hw09.jdbc.models;

import otus.hw09.jdbc.annotation.Id;
import otus.hw09.jdbc.db.DBExecutor;

import java.math.BigInteger;
import java.sql.SQLException;

public class Account {
    @Id
    private BigInteger accountId;
    private String no;
    private String type;
    private Float rest;

    public Account(String no, String type, Float rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public Float getRest() {
        return rest;
    }

    public void setRest(Float rest) {
        this.rest = rest;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigInteger getAccountId() {
        return accountId;
    }

    public void save() throws SQLException, ClassNotFoundException {
        DBExecutor<Account> dbExecutor = new DBExecutor<>(Account.class);
        dbExecutor.insert(this);
    }

    public static Account findById(Integer id) throws SQLException, ClassNotFoundException {
        DBExecutor<Account> dbExecutor = new DBExecutor<>(Account.class);
        return dbExecutor.select(id);
    }

    public static void createTable() throws SQLException, ClassNotFoundException {
        DBExecutor<Account> dbExecutor = new DBExecutor<>(Account.class);
        dbExecutor.createTable();
    }
}
