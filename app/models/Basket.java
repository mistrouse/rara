package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierrickgiuliani on 14/11/2016.
 */
@Entity
public class Basket extends Model {

    public static Model.Finder<Long, Basket> find = new Model.Finder<Long,Basket>(Basket.class);

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    //TEST BASKET


    public Basket(Long id) {
        this.id = id;
        this.save();
    }

    public static Finder<Long, Basket> getFind() {
        return find;
    }

    public Long getId() {
        return id;
    }

    public static void setFind(Finder<Long, Basket> find) {
        Basket.find = find;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                '}';
    }

    //TEST BASKET
}
