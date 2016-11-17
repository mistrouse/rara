package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.config.JsonConfig;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;


/**
 * Created by Djeneba on 11/11/2016.
 */

@Entity
public class Diary extends Model {
    public static Model.Finder<Long, Diary> find = new Model.Finder<Long, Diary>(Diary.class);


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String title;
    String description;
    String dateDiary;
    //Many diary may correspond to an user
    @ManyToOne
    @JsonBackReference
    Person user;
    public Diary(Long id, String title, String description, String dateDiary, Person user){
        this.id=id;
        this.title=title;
        this.description=description;
        this.dateDiary=dateDiary;
        this.user=user;
        this.save();
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription(){return description;}
    public String getDateDiary(){return dateDiary;}
    public Person getUser(){return user;}


    public void setTitle(String title2) {
        title=title2;
    }
    public void setDescription(String description2){description=description2;}
    public void setDateDiary(String dateDiary1){dateDiary=dateDiary1;}
    public void setUser(Person user2) {
        this.user = user2;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", dateDiary=" + dateDiary +
                '}';
    }
}
