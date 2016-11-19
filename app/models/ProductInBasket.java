package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Name of the class : ProductInBasket
 *
 * Description   : Persistence for the ProductInBasket
 *
 * Version       : 1.0
 *
 * Date          : 16/11/2016.
 */
@Entity
public class ProductInBasket extends Model {

    /**
     * Allows you search for a product in basket from anywhere
     */
    public static Model.Finder<Long, ProductInBasket> find = new Model.Finder<Long,ProductInBasket>(ProductInBasket.class);

    /**
     * The ID of the ProductInBasket
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * The quantity of the ProductInBasket
     */
    private int quantity;

    /**
     * Person (SU) who buy the Product
     */
    @ManyToOne
    @JsonBackReference
    Person refPerson;

    /**
     * Product buy by the SU
     */
    @ManyToOne
    @JsonBackReference
    Product refProduct;

    /**
     * Constructor of the ProductInBasket
     * @param id The ID of the ProductInBasket
     * @param quantity The quantity of the ProductInBasket
     * @param refPerson The person who buy the product
     * @param refProduct The product buy by the person
     */
    public ProductInBasket(Long id, int quantity, Person refPerson, Product refProduct) {
        this.id = id;
        this.quantity = quantity;
        this.refPerson = refPerson;
        this.refProduct = refProduct;
        this.save();
    }

    public static Finder<Long, ProductInBasket> getFind() {
        return find;
    }

    /**
     * Get the ID of the ProductInBasket
     * @return ID of the ProductInBasket
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the quantity of the ProductInBasket
     * @return Quantity of the ProductInBasket
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get the person who buy the product
     * @return The person (SU) who buy the product
     */
    public Person getRefPerson() {
        return refPerson;
    }

    /**
     * Get the product buy
     * @return The product buy
     */
    public Product getRefProduct() {
        return refProduct;
    }

    public static void setFind(Finder<Long, ProductInBasket> find) {
        ProductInBasket.find = find;
    }

    /**
     * Set the ID of the ProductInBasket
     * @param id of the ProductInBasket
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the quantity of the ProductInBasket
     * @param quantity of the ProductInBasket
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.save();
    }

    /**
     * Set the person who buy the product
     * @param refPerson buyer of the ProductInBasket
     */
    public void setRefPerson(Person refPerson) {
        this.refPerson = refPerson;
    }

    /**
     * Set the product buy by a SU
     * @param refProduct product of the ProductInBasket
     */
    public void setRefProduct(Product refProduct) {
        this.refProduct = refProduct;
    }

    @Override
    public String toString() {
        return "ProductInBasket{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", refPerson=" + refPerson +
                ", refProduct=" + refProduct +
                '}';
    }
}