package otus.hw11.cache.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AddressDataSet address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhoneDataSet> phoneList;

    public Client() {
    }

    public Client(String name, AddressDataSet address) {
        this.address = address;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDataSet getAddress() {
        return this.address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhoneList() {
        return phoneList;
    }

    public void addPhone(String number) {
        if (this.phoneList == null) {
            this.phoneList = new ArrayList<>();
        }

        this.phoneList.add(new PhoneDataSet(number, this));
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phoneList=" + phoneList +
                '}';
    }
}
