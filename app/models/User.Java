package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.eleven.org.apache.bcel.internal.classfile.Code;
import play.api.libs.Codecs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Name of the class : User
 *
 * *
 * Version       : 1.0
 *
 */
@Entity
public class User extends Model {

    /**
     * Allows you search for a User from anywhere
     */
    public static Model.Finder<Long, User> find = new Model.Finder<Long,User>(User.class);

    /**
     * Id of the User
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * Name of the User
     */
    String name;

    /**
     * Email of the User
     */
    @Column(unique=true)
    String email;

    /**
     * Birthday of the User
     */
    Date birthday;

    /**
     * Phone of the User
     */
    String phone;

    /**
     * Password of the User
     */
    String password;

    /**
     * Role of the User.
     * 0=eleve
     * 1=prof
     * 2=Admin
     */
    int role;

    /**
     * Number address for the localisation of the User
     */
    String numberAddress;

    /**
     * Street address for the localisation of the User
     */
    String streetAddress;

    /**
     * City address for the localisation of the User
     */
    String cityAddress;

    /**
     * Post code address for the localisation of the User
     */
    String postCodeAddress;

    /**
     * Token of the connexion for the User
     */
    String token;

    /**
     * List of the Message posted by a prof
     */
    @OneToMany(mappedBy = "prof")
    @JsonManagedReference
    List<Message> MessagePostedP = new ArrayList<Message>();
	
	    /**
     * List of the Message posted by  eleve
     */
    @OneToMany(mappedBy = "eleve")
    @JsonManagedReference
    List<Message> MessagePostedE = new ArrayList<Message>();
	
		    /**
     * List of the Message posted by  admin
     */
    @OneToMany(mappedBy = "admin")
    @JsonManagedReference
    List<Message> MessagePostedA = new ArrayList<Message>();
	
	

    /**
     * List of the document for user
     */
    @OneToMany(mappedBy = "User")
    @JsonManagedReference
    List<Document> DocumentUser = new ArrayList<Document>();
	
	

    /**
     * List of event for User
     */
    @OneToMany(mappedBy = "User")
    @JsonManagedReference
    List<Evenement> EvenementUser = new ArrayList<Evenement>();



    /**
     * Constructor of a User who save a User in the database
     * @param id The ID of the User
     * @param name The name of the User
     * @param email The email of the User
     * @param Birthday The birthday of the User
     * @param Phone of the User
     * @param password The password of the User
     * @param role The role of the User (0=eleve; 1=prof; 2=Admin)
     * @param token The token of the User
     * @param numberAddress The number of the adress
     * @param streetAddress The street adress
     * @param cityAddress The city adress
     * @param postCodeAddress The post code for the adress
     * @param MessagePostedP List of the Message posted by prof
	 * @param MessagePostedE List of the Message posted by eleve
	 * @param MessagePostedA List of the Message posted by admin
	 * @param DocumentUser List of the document for user
	 * @param EvenementUser List of event for User    
     */
	 

	 
    public User(Long id, String name, String email, Date birthday, String phone, String password, int role, String numberAddress, String streetAddress, String cityAddress, String postCodeAddress,
	String token, List<Message> MessagePostedP,  List<Message> MessagePostedE, List<Message> MessagePostedA, List<Document> DocumentUser, List<Evenement> EvenementUser) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.numberAddress = numberAddress;
        this.streetAddress = streetAddress;
        this.cityAddress = cityAddress;
        this.postCodeAddress = postCodeAddress;
        this.token = token;
		this.MessagePostedP = MessagePostedP;
		this.MessagePostedE = MessagePosteE;
		this.MessagePostedA = MessagePostedA;
        this.DocumentUser = DocumentUser;
        this.EvenementUser=EvenementUser;
        this.save();
    }

  
    /**
     * Get the id of the User
     * @return Id of the User
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the name of the User
     * @return Name of th User
     */
    public String getName() {
        return name;
    }

    /**
     * Get the email of the User
     * @return Email of the User
     */
    public String getEmail() {
        return email;
    }

   

    /**
     * Get the password of a User
     * @return Password for a User
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the phone number for a User
     * @return Phone number for a User
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Get the role of a User
     * @return 0=eleve; 1=prof; 2=Admin
     */
    public int getRole() {
        return role;
    }

    /**
     * Get the token of a User for the connexion
     * @return Token of the User
     */
    public String getToken() {
        return token;
    }
	
	 /**
     * Get the birthday of a User for the connexion
     * @return Birthday of the User
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Get the number address of a User
     * @return Number address of the User
     */
    public String getNumberAddress() {
        return numberAddress;
    }

    /**
     * Get the street address of a User
     * @return Street address of a User
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Get the city address of a User
     * @return City address of a User
     */
    public String getCityAddress() {
        return cityAddress;
    }

    /**
     * Get the post code address of a User
     * @return Post code address of a User
     */
    public String getPostCodeAddress() {
        return postCodeAddress;
    }
	
		
	  /**
     * Get the list of the Message posted by a prof
     * @return List of Message posted by a prof
     */
    public List<Message> getMessagePostedP() {
        return MessagePostedP;
    }
	
	
	  /**
     * Get the list of the Message posted by a eleve
     * @return List of Message posted by a eleve
     */
    public List<Message> getMessagePostedE() {
        return MessagePostedE;
    }
	
	  /**
     * Get the list of the Message posted by a admin
     * @return List of Message posted by a admin
     */
    public List<Message> getMessagePostedA() {
        return MessagePostedA;
    }

	
	  /**
     * Get the list of the document posted by User
     * @return List of document posted by a prof
     */
    public List<Document> getDocumentUser() {
        return DocumentUser;
    }
	
		  /**
     * Get the list of the evenement posted by User
     * @return List of evenement posted by a User
     */
    public List<Evenement> getEvenementUser() {
        return EvenementUser;
    }
	
		

    /**
     * Set the id of the User
     * @param id of the User
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the name of the User
     * @param name of the User
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the email of the User
     * @param email of the User
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the phone of the user
     * @param phone of the User
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Set the password of the User
     * @param password of the User
     */
    public void setPassword(String password) {
        this.password = Codecs.sha1(password);
    }

    /**
     * Set the Birthday of the User
     * @param Birthday of the User
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Set the role of the User
     * @param role 0=eleve; 1=prof; 2=Admin
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * Set the token of the User
     * @param token of the User
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Set the number address of the User
     * @param numberAddress of the User
     */
    public void setNumberAddress(String numberAddress) {
        this.numberAddress = numberAddress;
    }

    /**
     * Set the street address of the User
     * @param streetAddress of the User
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Set the city address of the User
     * @param cityAddress of the User
     */
    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    /**
     * Set the post code address of the User
     * @param postCodeAddress of the User
     */
    public void setPostCodeAddress(String postCodeAddress) {
        this.postCodeAddress = postCodeAddress;
    }
	

    /**
     * Set the list of the evenement posted by a User
     * @param EvenementUser List of the evenement
     */
    public void setEvenementUser(List<Evenement> EvenementUser) {
        this.EvenementUser = EvenementUser;
    }

    /** Set the list of the Document posted by User
     * @param DocumentUser List of the Document
     */
    public void setDocumentUser(List<Document> DocumentUser) { 
		this.DocumentUser = DocumentUser; 
	}

	
    /**
     * Set the list of the message posted by admin
     * @param MessagePostedA List of the Message
     */
    public void setMessagePostedA(List<Message> MessagePostedA) {
        this.MessagePostedA =MessagePostedA;
    }

	/**
     * Set the list of the message posted by eleve
     * @param MessagePostedA List of the Message
     */
    public void setMessagePostedE(List<Message> MessagePostedE) {
        this.MessagePostedE =MessagePostedE;
    }
	
	/**
     * Set the list of the message posted by prof
     * @param MessagePostedP List of the Message
     */
    public void setMessagePostedP(List<Message> MessagePostedP) {
        this.MessagePostedP =MessagePostedP;
    }
	
	
	
	public User(Long id, String name, String email, Date birthday, String phone, String password, int role, String numberAddress, String streetAddress, String cityAddress, String postCodeAddress,
	String token, List<Message> MessagePostedP,  List<Message> MessagePostedE, List<Message> MessagePostedA, List<Document> DocumentUser, List<Evenement> EvenementUser) {
		
		
	
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", numberAddress='" + numberAddress + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", cityAddress='" + cityAddress + '\'' +
                ", postCodeAddress='" + postCodeAddress + '\'' +
                ", token='" + token + '\'' +
                ", MessagePostedP=" + MessagePostedP + '\''+
				", MessagePostedP=" + MessagePostedE + '\''+
				", MessagePostedP=" + MessagePostedA + '\''+
                ", DocumentUser=" + DocumentUser + '\''+
                ", EvenementUser=" + EvenementUser +
                '}';
    }
}