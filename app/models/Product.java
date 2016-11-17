package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pierrickgiuliani on 08/11/2016.
 */
@Entity
public class Product extends Model {

    public static Model.Finder<Long, Product> find = new Model.Finder<Long,Product>(Product.class);

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String name;
    String description;
    Double price;
    int quantity;
    //@Lob
    //public byte[] photo;
    @ManyToOne
    @JsonBackReference
    Person seller;

    //TEST BASKET
    @OneToMany(mappedBy = "refProduct")
    @JsonManagedReference
    List<ProductInBasket> basket = new ArrayList<ProductInBasket>();
    //TEST BASKET

    public Product(Long id, String name, String description, Double price, int quantity, Person seller, List<ProductInBasket> basket) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.seller = seller;
        this.basket = basket;
        this.save();
    }

    //TEST BASKET

    public List<ProductInBasket> getBasket() {
        return basket;
    }

    public void setBasket(List<ProductInBasket> basket) {
        this.basket = basket;
    }

    //TEST BASKET

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    /*public byte[] getPhoto() {
        return photo;
    }*/

    public Person getSeller() {
        return seller;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /*public void setPhoto(byte[] photo) {
        this.photo = photo;
    }*/

    public void setSeller(Person seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", seller=" + seller +
                ", basket=" + basket +
                '}';
    }
}
