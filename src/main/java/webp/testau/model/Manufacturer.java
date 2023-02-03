package webp.testau.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
//@Table(name="manufacturers")
public class Manufacturer {
    @Id
    private Long id;
    private String name;

//    @Column(name="manufacturer-address")
    private String address;

    public Manufacturer(String name, String address) {
        this.id = (long) (Math.random() * 1000);
//        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Manufacturer() {

    }
}
