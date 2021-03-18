package otus.hw09.jdbc.models;

import com.google.gson.Gson;
import otus.hw09.jdbc.annotation.Id;

import java.util.Random;

public class Account {
    @Id
    private long id;
    private String no;
    private String type;
    private Double rest;

    public Account() {
        this.id = new Random().nextLong();
        this.no = "no";
        this.type = "type";
        this.rest = Double.MAX_VALUE;
    }

    public Account(String no, String type, Double rest) {
        this.id = new Random().nextLong();
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public Account(long id, String no, String type, Double rest) {
        this.id = id;
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public Double getRest() {
        return rest;
    }

    public void setRest(Double rest) {
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

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        var gson = new Gson();
        return gson.toJson(this);
    }
}
