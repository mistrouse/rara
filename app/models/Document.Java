package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * name of the class : Document
 *
 * motclef   : Persistence for the Document
 *
 * Version       : 1.0
 *
 */
@Entity
public class Document extends Model {

    /**
     * Allows you search for a Document from anywhere
     */
    public static Model.Finder<Long, Document> find = new Model.Finder<Long,Document>(Document.class);

    /**
     * Id of the Document
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * title of the Document
     */
    String title;

    /**
     * motclef of the Document
     */
    String motclef;

    /**
     * date of creation of the Document
     */
    Date creationDate;

    /**
     * categorie of the Document
     */
    int categorieDocument;

    
      /**
     * User of the Document
     */
    @ManyToOne
    @JsonBackReference
    User userDocument;

   

    /**
     * Constructor of a Document who save a Document in the database
     * @param id The ID of the Document
     * @param title The title of the Document
     * @param motclef The motclef of the Document
     * @param creationDate The date of creation of the Document
     * @param categorieDocument categorie of the Document
     * @param userDocument User of the Document
     */
    public Document(Long id, String title, String motclef, Date creationDate, int categorieDocument, User userDocument) {
        this.id = id;
        this title = title;
        this.motclef = motclef;
        this.creationDate = creationDate;
        this.categorieDocument = categorieDocument;
        this.userDocument = userDocument;
        this.save();
    }

  

    /**
     * Get the ID of the Document
     * @return ID of the Document
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the title of the Document
     * @return title of the Document
     */
    public String geTitle() {
        return title;
    }

    /**
     * Get the motclef of the Document
     * @return motclef of the Document
     */
    public String getMotclef() {
        return motclef;
    }

    /**
     * Get the creationDate of the Document
     * @return creationDate of the Document
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Get the categorieDocument of the Document
     * @return categorieDocument of the Document
     */
    public int getCategorieDocument() {
        return categorieDocument;
    }

    /**
     * Get the Person created the Document 
     * @return user of the Document
     */
    public User getUserDocument() {
        return userDocument;
    }

    /**
     * Set the id of the Document
     * @param id of the Document
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the title of the Document
     * @param title of the Document
     */
    public void setTitle(String title) {
        this title = title;
    }

    /**
     * Set the motclef of the Document
     * @param motclef of the Document
     */
    public void setMotclef(String motclef) {
        this.motclef = motclef;
    }

    /**
     * Set the creationDate of the Document
     * @param creationDate of the Document
     */
    public void setCreationDate (Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Set the categorieDocument of the Document
     * @param categorieDocument of the Document
     */
    public void setCategorieDocument(int categorieDocument) {
        this.categorieDocument = categorieDocument;
    }

    /**
     * Set the userDocument who create the Document
     * @param userDocument the new userDocument
     */
    public void setUserDocument(User userDocument) {
        this userDocument = userDocument;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", motclef='" + motclef + '\'' +
                ", creationDate=" + creationDate +
                ", categorieDocument=" + categorieDocument +
                ", userDocument=" + userDocument +
                '}';
    }
}