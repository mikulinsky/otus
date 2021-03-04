package otus.hw10.hibernate.core.model;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    private Client client;

    private String number;

    public PhoneDataSet() {

    }

    public PhoneDataSet(String phone, Client client) {
        this.client = client;
        this.number = phone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
