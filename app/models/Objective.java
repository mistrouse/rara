package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by Gab on 10/11/2016.
 */
@Entity
public class Objective extends Model {

    public static Model.Finder<Long, Objective> find = new Model.Finder<Long, Objective>(Objective.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String description;
    @ManyToOne
    @JsonBackReference
    Person simpleUser;

    //Constructor
    public Objective(Long id, String name, String description, Person simpleUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.simpleUser = simpleUser;
        this.save();
    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Person getSimpleUser() {
        return simpleUser;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSimpleUser(Person simpleUser) {
        this.simpleUser = simpleUser;
    }

    @Override
    public String toString() {
        return "Objective{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", simpleUser=" + simpleUser +
                '}';
    }
}

