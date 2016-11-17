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
}
