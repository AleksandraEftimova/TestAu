package webp.testau.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@Entity
public class Product {
    @Id
    private Long id; //kluc
    private String name; //isto taka unique
    private Double price;
    private Integer quantity;

//    @Transient
    @ManyToOne
    private Category category;

    @ManyToOne
    private Manufacturer manufacturer;

    @ManyToMany(mappedBy = "products")
    private List<ShoppingCart> carts;

    public Product(String name, Double price, Integer quantity, Category category, Manufacturer manufacturer) {
        this.id = (long) (Math.random() * 1000);
//        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.manufacturer = manufacturer;
    }

    public Product() {

    }
}
