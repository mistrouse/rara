package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.org.apache.bcel.internal.classfile.Code;
import play.api.libs.Codecs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Name of the class : Person
 *
 * Description   : Persistence for the Person
 *
 * Version       : 1.0
 *
 * Date          : 31/10/2016
 */
@Entity
public class Person extends Model {

    /**
     * Allows you search for a person from anywhere
     */
    public static Model.Finder<Long, Person> find = new Model.Finder<Long,Person>(Person.class);

    /**
     * Id of the person
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * Name of the person
     */
    String name;

    /**
     * Email of the person
     */
    @Column(unique=true)
    String email;

    /**
     * Pseudo of the person
     */
    String pseudo;

    /**
     * Siret of the person
     */
    String siret;

    /**
     * Password of the person
     */
    String password;

    /**
     * Role of the person.
     * 0=SU
     * 1=SC
     * 2=Admin
     */
    int role;

    /**
     * Number address for the localisation of the person
     */
    String numberAddress;

    /**
     * Street address for the localisation of the person
     */
    String streetAddress;

    /**
     * City address for the localisation of the person
     */
    String cityAddress;

    /**
     * Post code address for the localisation of the person
     */
    String postCodeAddress;

    /**
     * Token of the connexion for the person
     */
    String token;

    /**
     * List of the product sell by a SC
     */
    @OneToMany(mappedBy = "seller")
    @JsonManagedReference
    List<Product> productSell = new ArrayList<Product>();

    /**
     * List of the objective for a SU
     */
    @OneToMany(mappedBy = "simpleUser")
    @JsonManagedReference
    List<Objective> objectiveSU = new ArrayList<Objective>();

    /**
     * List of diary for a SU
     */
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    List<Diary> diaryUser = new ArrayList<Diary>();

    /**
     * List of all product in the basket of a SU
     */
    @OneToMany(mappedBy = "refPerson")
    @JsonManagedReference
    List<ProductInBasket> basket = new ArrayList<ProductInBasket>();

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
     * @param numberAddress The number of the adress
     * @param streetAddress The street adress
     * @param cityAddress The city adress
     * @param postCodeAddress The post code for the adress
     * @param productSell List of the product sell by a seller
     * @param objectiveSU List of the objectives for a SU
     * @param diaryUser List of the user diary
     * @param basket List of the product in the basket of a SU
     */
    public Person(Long id, String name, String email, String pseudo, String siret, String password, int role, String numberAddress, String streetAddress, String cityAddress, String postCodeAddress, String token, List<Product> productSell, List<Objective> objectiveSU, List<Diary> diaryUser, List<ProductInBasket> basket) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pseudo = pseudo;
        this.siret = siret;
        this.password = password;
        this.role = role;
        this.numberAddress = numberAddress;
        this.streetAddress = streetAddress;
        this.cityAddress = cityAddress;
        this.postCodeAddress = postCodeAddress;
        this.token = token;
        this.productSell = productSell;
        this.objectiveSU = objectiveSU;
        this.diaryUser=diaryUser;
        this.basket = basket;
        this.save();
    }

    /**
     * Get the product in the basket of a SU
     * @return List of a ProductInBasket with the quantity, price and the product
     */
    public List<ProductInBasket> getBasket() {
        return basket;
    }

    /**
     * Set the product in the basket of a SU
     * @param basket List of the product
     */
    public void setBasket(List<ProductInBasket> basket) {
        this.basket = basket;
    }

    public static Finder<Long, Person> getFind() {
        return find;
    }

    public static void setFind(Finder<Long, Person> find) {
        Person.find = find;
    }

    /**
     * Get the id of the person
     * @return Id of the person
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the name of the person
     * @return Name of th person
     */
    public String getName() {
        return name;
    }

    /**
     * Get the email of the person
     * @return Email of the person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the pseudo of a person
     * @return Pseudo of the person
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Get the password of a person
     * @return Password for a person
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the siret number for a SC
     * @return Siret number for a SC
     */
    public String getSiret() {
        return siret;
    }

    /**
     * Get the role of a person
     * @return 0=SU; 1=SC; 2=Admin
     */
    public int getRole() {
        return role;
    }

    /**
     * Get the token of a person for the connexion
     * @return Token of the person
     */
    public String getToken() {
        return token;
    }

    /**
     * Get the number address of a person
     * @return Number address of the person
     */
    public String getNumberAddress() {
        return numberAddress;
    }

    /**
     * Get the street address of a person
     * @return Street address of a person
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Get the city address of a person
     * @return City address of a person
     */
    public String getCityAddress() {
        return cityAddress;
    }

    /**
     * Get the post code address of a person
     * @return Post code address of a person
     */
    public String getPostCodeAddress() {
        return postCodeAddress;
    }

    /**
     * Get the list of the product sell by a SC
     * @return List of product sell by a SC
     */
    public List<Product> getProductSell() {
        return productSell;
    }

    /**
     * Get the list of the objective for a SU
     * @return List of objective for a SU
     */
    public List<Objective> getObjectiveSU() { return objectiveSU; }

    /**
     * Get the list of the diary for an SU
     * @return List of diary for a SU
     */
    public List<Diary> getDiaryUser() {
        return diaryUser;
    }

    /**
     * Set the id of the person
     * @param id of the person
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the name of the person
     * @param name of the person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the email of the person
     * @param email of the person
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the pseudo of the peron
     * @param pseudo of the person
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Set the password of the person
     * @param password of the person
     */
    public void setPassword(String password) {
        this.password = Codecs.sha1(password);
    }

    /**
     * Set the siret of the person
     * @param siret of the person
     */
    public void setSiret(String siret) {
        this.siret = siret;
    }

    /**
     * Set the role of the person
     * @param role 0=SU; 1=SC; 2=Admin
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * Set the token of the person
     * @param token of the person
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Set the number address of the person
     * @param numberAddress of the person
     */
    public void setNumberAddress(String numberAddress) {
        this.numberAddress = numberAddress;
    }

    /**
     * Set the street address of the person
     * @param streetAddress of the person
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Set the city address of the person
     * @param cityAddress of the person
     */
    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    /**
     * Set the post code address of the person
     * @param postCodeAddress of the person
     */
    public void setPostCodeAddress(String postCodeAddress) {
        this.postCodeAddress = postCodeAddress;
    }

    /**
     * Set the list of the product sell by a SC
     * @param productSell List of the product
     */
    public void setProductSell(List<Product> productSell) {
        this.productSell = productSell;
    }

    /** Set the list of the objective create by a SU
     * @param objectiveSU List of the objective
     */
    public void setObjectiveSU(List<Objective> objectiveSU) { this.objectiveSU = objectiveSU; }

    /**
     * Set the list of the diary for a SU
     * @param diaryUser List of the diary
     */
    public void setDiaryUser(List<Diary> diaryUser) {
        this.diaryUser =diaryUser;
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
                ", role=" + role +
                ", numberAddress='" + numberAddress + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", cityAddress='" + cityAddress + '\'' +
                ", postCodeAddress='" + postCodeAddress + '\'' +
                ", token='" + token + '\'' +
                ", productSell=" + productSell + '\''+
                ", objectiveSU=" + objectiveSU + '\''+
                ", diaryUser=" + diaryUser +
                ", basket=" + basket +
                '}';
    }
}