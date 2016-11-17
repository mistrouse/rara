package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierrickgiuliani on 16/11/2016.
 */
@Entity
public class ProductInBasket extends Model {

    public static Model.Finder<Long, ProductInBasket> find = new Model.Finder<Long,ProductInBasket>(ProductInBasket.class);

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    //TEST BASKET
    private Double price;
    private int quantity;

    @ManyToOne
    @JsonBackReference
    Person refPerson;

    @ManyToOne
    @JsonBackReference
    Product refProduct;

    public ProductInBasket(Long id, Double price, int quantity, Person refPerson, Product refProduct) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.refPerson = refPerson;
        this.refProduct = refProduct;
        this.save();
        System.out.println(this);
    }

    public static Finder<Long, ProductInBasket> getFind() {
        return find;
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Person getRefPerson() {
        return refPerson;
    }

    public Product getRefProduct() {
        return refProduct;
    }

    public static void setFind(Finder<Long, ProductInBasket> find) {
        ProductInBasket.find = find;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setRefPerson(Person refPerson) {
        this.refPerson = refPerson;
    }

    public void setRefProduct(Product refProduct) {
        this.refProduct = refProduct;
    }

    @Override
    public String toString() {
        return "ProductInBasket{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", refPerson=" + refPerson +
                ", refProduct=" + refProduct +
                '}';
    }

    //TEST BASKET
}