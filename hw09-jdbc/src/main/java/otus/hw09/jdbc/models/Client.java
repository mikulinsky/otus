package otus.hw09.jdbc.models;

import com.google.gson.Gson;
import otus.hw09.jdbc.annotation.Id;

import java.util.Random;

public class Client {
    @Id
    private long id;
    private String name;
    private Integer age;

    public Client() {
        this.id = new Random().nextLong();
        this.name = "name";
        this.age = -1;
    }

    public Client(String name, Integer age) {
        this.id = new Random().nextLong();
        this.name = name;
        this.age = age;
    }

    public Client(long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
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

    @Override
    public String toString() {
        var gson = new Gson();
        return gson.toJson(this);
    }

}
