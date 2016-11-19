package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Name of the class : Objective
 *
 * Description   : Persistence for the Objective
 *
 * Version       : 1.0
 *
 * Date          : 10/11/2016
 */
@Entity
public class Objective extends Model {

    /**
     * Allows you search for an Objective from anywhere
     */
    public static Model.Finder<Long, Objective> find = new Model.Finder<Long, Objective>(Objective.class);

    /**
     * ID of the objective
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    /**
     * Name of the objective
     */
    String name;

    /**
     * Description of the objective
     */
    String description;

    /**
     * Simple user who created the objective
     */
    @ManyToOne
    @JsonBackReference
    Person simpleUser;

    /**
     * Contructeur of the Objective
     * @param id The ID of the objective
     * @param name The name of the objective
     * @param description The description of the objective
     * @param simpleUser The SU who created the objective
     */
    public Objective(Long id, String name, String description, Person simpleUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.simpleUser = simpleUser;
        this.save();
    }

    /**
     * Get the ID of the objective
     * @return ID of the objective
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the name of the objective
     * @return Name of the objective
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the objective
     * @return Description of the objective
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the SU who created the objective
     * @return The SU who created the objective
     */
    public Person getSimpleUser() {
        return simpleUser;
    }

    /**
     * Set the ID of the objective
     * @param id of the objective
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the name of the objective
     * @param name of the objective
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the description of the objective
     * @param description of the objective
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the SU of the objective
     * @param simpleUser creator of the objective
     */
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

