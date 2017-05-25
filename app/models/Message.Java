package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * name of the class : Message
 *
 * Description   : Persistence for the Message
 *
 * Version       : 1.0
 */
@Entity
public class Message extends Model {

    /**
     * Allows you search for a Message from anywhere
     */
    public static Model.Finder<Long, Message> find = new Model.Finder<Long,Message>(Message.class);

    /**
     * Id of the Message
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * title of the Message
     */
    String title;

    /**
     * Description of the Message
     */
    String description;

    /**
     * date of creation of the message
     */
    Date creationDate;

    /**
     * categorie of the Message
     */
    int categorieMessage;

    
      /**
     * User of the message
     */
    @ManyToOne
    @JsonBackReference
    User userMessage;

   

    /**
     * Constructor of a Message who save a Message in the database
     * @param id The ID of the Message
     * @param title The title of the Message
     * @param description The description of the Message
     * @param creationDate The date of creation of the Message
     * @param categorieMessage categorie of the Message
     * @param userMessage User of the message
     */
    public Message(Long id, String title, String description, Date creationDate, int categorieMessage, User userMessage) {
        this.id = id;
        this title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.categorieMessage = categorieMessage;
        this.userMessage = userMessage;
        this.save();
    }

  

    /**
     * Get the ID of the Message
     * @return ID of the Message
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the title of the Message
     * @return title of the Message
     */
    public String geTitle() {
        return title;
    }

    /**
     * Get the description of the Message
     * @return Description of the Message
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the creationDate of the Message
     * @return creationDate of the Message
     */
    public Date getcreationDate() {
        return creationDate;
    }

    /**
     * Get the categorieMessage of the Message
     * @return categorieMessage of the Message
     */
    public int getcategorieMessage() {
        return categorieMessage;
    }

    /**
     * Get the Person created the Message 
     * @return user of the Message
     */
    public User getUserMessage() {
        return userMessage;
    }

    /**
     * Set the id of the Message
     * @param id of the Message
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the title of the Message
     * @param title of the Message
     */
    public void se title(String title) {
        this title = title;
    }

    /**
     * Set the description of the Message
     * @param description of the Message
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the creationDate of the Message
     * @param creationDate of the Message
     */
    public void setCreationDate (Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Set the categorieMessage of the Message
     * @param categorieMessage of the Message
     */
    public void setcategorieMessage(int categorieMessage) {
        this.categorieMessage = categorieMessage;
    }

    /**
     * Set the userMessage who create the Message
     * @param userMessage the new userMessage
     */
    public void setUserMessage(User userMessage) {
        this userMessage = userMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", categorieMessage=" + categorieMessage +
                ", userMessage=" + userMessage +
                '}';
    }
}