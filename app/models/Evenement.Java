package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * name of the class : Evenement
 *
 * Description   : Persistence for the Evenement
 *
 * Version       : 1.0
 *
 */
@Entity
public class Evenement extends Model {

    /**
     * Allows you search for a Evenement from anywhere
     */
    public static Model.Finder<Long, Evenement> find = new Model.Finder<Long,Evenement>(Evenement.class);

    /**
     * Id of the Evenement
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * name of the Evenement
     */
    String name;

    /**
     * Description of the Evenement
     */
    String description;

    /**
     * date of creation of the Evenement
     */
    Date creationDate;

    /**
     * categorie of the Evenement
     */
    int categorieEvenement;



   

    /**
     * Constructor of a Evenement who save a Evenement in the database
     * @param id The ID of the Evenement
     * @param name The name of the Evenement
     * @param description The description of the Evenement
     * @param creationDate The date of creation of the Evenement
     * @param categorieEvenement categorie of the Evenement
     */
    public Evenement(Long id, String name, String description, Date creationDate, int categorieEvenement) {
        this.id = id;
        this name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.categorieEvenement = categorieEvenement;
        this.save();
    }

  

    /**
     * Get the ID of the Evenement
     * @return ID of the Evenement
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the name of the Evenement
     * @return name of the Evenement
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the Evenement
     * @return Description of the Evenement
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the creationDate of the Evenement
     * @return creationDate of the Evenement
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Get the categorieEvenement of the Evenement
     * @return categorieEvenement of the Evenement
     */
    public int getCategorieEvenement() {
        return categorieEvenement;
    }


    /**
     * Set the id of the Evenement
     * @param id of the Evenement
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the name of the Evenement
     * @param name of the Evenement
     */
    public void setName(String name) {
        this name = name;
    }

    /**
     * Set the description of the Evenement
     * @param description of the Evenement
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the creationDate of the Evenement
     * @param creationDate of the Evenement
     */
    public void setCreationDate (Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Set the categorieEvenement of the Evenement
     * @param categorieEvenement of the Evenement
     */
    public void setcategorieEvenement(int categorieEvenement) {
        this.categorieEvenement = categorieEvenement;
    }

   
    
    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", categorieEvenement=" + categorieEvenement +
                '}';
    }
}