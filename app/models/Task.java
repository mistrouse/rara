package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by pierrickgiuliani on 31/10/2016.
 */
@Entity
public class Task extends Model {

    public static Finder<Long, Task> find = new Finder<Long,Task>(Task.class);

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;
    String name;
    @ManyToOne
    @JsonBackReference
    Person creator;

    public Task() {
    }

    public Task(Long id, String name, Person creator) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        super.save();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public Person getCreator() {
        return creator;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creator=" + creator +
                '}';
    }
}
