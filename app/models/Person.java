package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierrickgiuliani on 31/10/2016.
 */
@Entity
public class Person extends Model {

    public static Model.Finder<Long, Person> find = new Model.Finder<Long,Person>(Person.class);

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String name;
    @Column(unique=true)
    String email;
    String pseudo;
    String siret;
    String password;
    int role;
    String token;
    @OneToMany(mappedBy = "creator")
    @JsonManagedReference
    List<Task> taskRealised = new ArrayList<Task>();

    public Person() {
    }

    /**
     * Constructor of a Person who save a person in the database
     * @param id The ID of the Person
     * @param name The name of the Person
     * @param email The email of the Person
     * @param pseudo The pseudo of the Person
     * @param siret The number siret of the Person
     * @param password The password of the Person
     * @param role The role of the Person (0=SU; 1=SC; 2=Admin)
     * @param token The token of the Person
     * @param taskRealised List of the task realised by a Person
     */
    public Person(Long id, String name, String email, String pseudo, String siret, String password, int role, String token, List<Task> taskRealised) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pseudo = pseudo;
        this.siret = siret;
        this.password = password;
        this.role = role;
        this.token = token;
        this.taskRealised = taskRealised;
        super.save();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Task> getTaskRealised() {
        return taskRealised;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    public String getSiret() {
        return siret;
    }

    public int getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTaskRealised(List<Task> taskRealised) {
        this.taskRealised = taskRealised;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", siret='" + siret + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", role=" + role +
                ", taskRealised=" + taskRealised +
                '}';
    }

}
