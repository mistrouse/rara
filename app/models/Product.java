package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Name of the class : Product
 *
 * Description   : Persistence for the Product
 *
 * Version       : 1.0
 *
 * Date          : 31/10/2016
 */
@Entity
public class Product extends Model {

    /**
     * Allows you search for a product from anywhere
     */
    public static Model.Finder<Long, Product> find = new Model.Finder<Long,Product>(Product.class);

    /**
     * Id of the product
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * Name of the product
     */
    String name;

    /**
     * Description of the product
     */
    String description;

    /**
     * Price of the product
     */
    Double price;

    /**
     * Quantity of the product
     */
    int quantity;

    /**
     * Seller of the product
     */
    @ManyToOne
    @JsonBackReference
    Person seller;

    /**
     * List of basket where the product is located
     */
    @OneToMany(mappedBy = "refProduct")
    @JsonManagedReference
    List<ProductInBasket> basket = new ArrayList<ProductInBasket>();

    /**
     * Constructor of a Product who save a product in the database
     * @param id The ID of the Product
     * @param name The name of the Product
     * @param description The description of the Product
     * @param price The price of the Product
     * @param quantity The quantity of the Product
     * @param seller The seller who sell the Product
     * @param basket The basket where the product is located
     */
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

    /**
     * Get the product in the basket of a SU
     * @return List of a ProductInBasket with the quantity, price and the product
     */
    public List<ProductInBasket> getBasket() {
        return basket;
    }

    /**
     * Set the product in the basket of a SU
     * @param basket List of the product
     */
    public void setBasket(List<ProductInBasket> basket) {
        this.basket = basket;
    }

    /**
     * Get the ID of the product
     * @return ID of the product
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the name of the product
     * @return Name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the product
     * @return Description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the price of the product
     * @return Price of the product
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Get the quantity of the product
     * @return Quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get the Person who the sell the product
     * @return Seller of the product
     */
    public Person getSeller() {
        return seller;
    }

    /**
     * Set the id of the product
     * @param id of the product
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the name of the product
     * @param name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the description of the product
     * @param description of the product
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the price of the product
     * @param price of the product
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Set the quantity of the product
     * @param quantity of the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Set the seller who sell the product
     * @param seller the new seller
     */
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
