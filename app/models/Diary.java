package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.config.JsonConfig;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

/**
 * Name of the class : Diary
 *
 * Description   : Persistence for the Diary
 *
 * Version       : 1.0
 *
 * Date          : 11/11/2016
 */
@Entity
public class Diary extends Model {

    /**
     * Allows you search for a Diary from anywhere
     */
    public static Model.Finder<Long, Diary> find = new Model.Finder<Long, Diary>(Diary.class);


    /**
     * Id of the Diary
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * Title of the diary
     */
    String title;

    /**
     * Description of the diary
     */
    String description;

    /**
     * Date of the diary publication
     */
    String dateDiary;

    /**
     * Creator of the diary
     */
    @ManyToOne
    @JsonBackReference
    Person user;
<<<<<<< HEAD
    // A diary describes the achievement of an objective
    @ManyToOne
    @JsonBackReference
    Objective objective;

    public Diary(Long id, String title, String description, String dateDiary, Person user, Objective objective){
=======

    /**
     * Constructor of the diary
     * @param id The id of the diary
     * @param title The title of the diary
     * @param description The description of the diary
     * @param dateDiary The date of the diary publication
     * @param user The user who created the diary
     */
    public Diary(Long id, String title, String description, String dateDiary, Person user){
>>>>>>> origin/master
        this.id=id;
        this.title=title;
        this.description=description;
        this.dateDiary=dateDiary;
        this.user=user;
        this.objective=objective;
        this.save();
    }

    /**
     * Get the ID of the diary
     * @return The id of the diary
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the title of the diary
     * @return The title of the diary
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get description of the diary
     * @return The description of the diary
     */
    public String getDescription(){return description;}

    /**
     * Get date creation of the diary
     * @return The date of of the diary
     */
    public String getDateDiary(){return dateDiary;}
<<<<<<< HEAD
    public Person getUser(){return user;}
    public Objective getObjective(){return objective;}
=======
>>>>>>> origin/master

    /**
     * Get the person who created the diary
     * @return The person who created the diary
     */
    public Person getUser(){return user;}

    /**
     * Set the title of the diary
     * @param title2 of the diary
     */
    public void setTitle(String title2) {
        title=title2;
    }

    /**
     * Set the description of the diary
     * @param description2 of the diary
     */
    public void setDescription(String description2){description=description2;}

    /**
     * Set date of the diary
     * @param dateDiary1 of the diary
     */
    public void setDateDiary(String dateDiary1){dateDiary=dateDiary1;}

    /**
     * Set the user who created the diary
     * @param user2 creator of the diary
     */
    public void setUser(Person user2) {
        this.user = user2;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", dateDiary=" + dateDiary +
                ", objective='" + objective + '\'' +
                '}';
    }
}
