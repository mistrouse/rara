package controllers;

import models.Person;
import play.api.libs.Codecs;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static play.data.Form.form;

/**
 * Controller to manage a Person
 * Created by pierrickgiuliani on 03/11/2016.
 */
public class PersonController extends Controller {
    /*
                           #################################
                           #             PERSON            #
                           #################################
     */

    /**
     * GET all person in the database
     * @return The information of all Person in JSON format
     */
    public Result persons() {
        //this.initializePerson();
        return ok(Json.toJson(Person.find.all()));
    }

    /**
     *  GET the person in the database with his ID if it exist
     * @param id The id of a Person
     * @return The information of a Person in JSON format <br/>
     * If the person exist, return <b>200 Ok</b><br/>
     * Else return <b>404 Not Found</b>
     */
    public Result person(Long id) {
        if(Person.find.byId(id) == null) {
            return notFound("Person not found.");
        }
        else {
            return ok(Json.toJson(Person.find.byId(id)));
        }
    }

    /**
     *  UPDATE a person in the database with his ID with the value of the FROM. If a value of FORM is empty, the value is not updated
     * @param id The id of a Person
     * @return The information of a Person in JSON format <br/>
     * If the person doesn't exist in the database, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b>
     */
    public Result personUpdate(Long id) {
        // Get the person in the database, if not exist return 404 not found
        Person updatePerson = Person.find.byId(id);
        if(updatePerson == null) {
            return notFound("Person not found.");
        }
        // If exist, get the value of the form and do the update in the database
        else {
            // Get the value of the form
            final Map<String, String[]> values = request().body().asFormUrlEncoded();
            String newName = values.get("newName")[0];
            String newPseudo = values.get("newPseudo")[0];
            String newNumberAddress = values.get("newNumberAddress")[0];
            String newStreetAddress = values.get("newStreetAddress")[0];
            String newPostCodeAddress = values.get("newPostCodeAddress")[0];
            String newCityAddress = values.get("newCityAddress")[0];


//            System.out.println("personUpdate(id) from PersonController -- updatePerson="+updatePerson);

            // Test if not empty, make the update, else nothing
            if(!newName.isEmpty())
                updatePerson.setName(newName);
            if(!newPseudo.isEmpty())
                updatePerson.setPseudo(newPseudo);
            if(!values.get("newSiret")[0].isEmpty()) {
                updatePerson.setSiret(values.get("newSiret")[0]);
            }
            if(!values.get("newPassword")[0].isEmpty())
                updatePerson.setPassword(values.get("newPassword")[0]);
            if(!newNumberAddress.isEmpty())
                updatePerson.setNumberAddress(newNumberAddress);
            if(!newStreetAddress.isEmpty())
                updatePerson.setStreetAddress(newStreetAddress);
            if(!newPostCodeAddress.isEmpty())
                updatePerson.setPostCodeAddress(newPostCodeAddress);
            if(!newCityAddress.isEmpty())
                updatePerson.setCityAddress(newCityAddress);

            // Make change in the database
            updatePerson.save();
//            System.out.println("personUpdate(id) from PersonController -- updatePerson="+updatePerson);
            return ok(Json.toJson(Person.find.byId(id)));
        }
    }

    /**
     * CREATE a person in the database with the values of the FORM
     * @return The person is created <br/>
     * If the person already exist in the database, return <b>409 Conflict</b> <br/>
     * Else return <b>201 Created</b>
     */
    public Result personCreate() {
        // Get the value of the POST and stock in variable the values
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String name = values.get("name")[0];
        String pseudo = values.get("pseudo")[0];
        String email = values.get("email")[0];
        String siret = null;
        String numberAddress = values.get("numberAddress")[0];
        String streetAddress = values.get("streetAddress")[0];
        String cityAddress = values.get("cityAddress")[0];
        String postCodeAddress = values.get("postCodeAddress")[0];
        int role = 0; // 0 = SP
        if(!values.get("siret")[0].isEmpty()) {
            siret = values.get("siret")[0];
            role = 1; // SV
        }
        String password = Codecs.sha1(values.get("password")[0]);
        if(!values.get("admin")[0].isEmpty()) {
//            System.out.println(values.get("admin")[0]);
            if(values.get("admin")[0].equals("yes")) {
                role = 2;
            }
        }

        // Test if the user is already in the database before the save, if no return 201 created, else return 409 conflict
        Person isAlreadyExist = Person.find.where().like("email", "%"+email+"%").findUnique();
        System.out.println("personCreate() FROM PersonController.java -- isAlreadyExist="+isAlreadyExist);
        if(isAlreadyExist == null) {
            // Create the person in the database with the informations
            Person person = new Person(null, name, email, pseudo, siret, password, role, numberAddress, streetAddress, cityAddress, postCodeAddress, null, null,null);
            System.out.println("personCreate() FROM PersonController.java -- person="+person);
            return created();
        }
        else {
            return status(409, "Email already exist in the database.");
        }
    }

    /**
     * DELETE a person in the database with his ID
     * @param id The id of a Person
     * @return If the person doesn't exist in the dababase, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b>
     */
    public Result personDelete(Long id) {
        if(Person.find.byId(id) == null) {
            return notFound("Person not found.");
        }
        else {
            Person.find.deleteById(id);
            return ok("The person has been deleted");
        }
    }

    /**
     *  Check if the user exist in the database with his email and password
     * @return If the user doesn't exist, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b> and set a cookie with the token and another with the id of the Person
     */
    public Result login() {
        // Get attribute from the form
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String email = values.get("email")[0];
        String password = Codecs.sha1(values.get("password")[0]);

        // Check if exist in the database
        List<Person> isExist = Person.find.where().like("email", "%"+email+"%").like("password", "%"+password+"%").findList();
        if(isExist.size() == 0) {
            return notFound("Wrong email or password");
        }
        else {
            // Generate random token
            String token = UUID.randomUUID().toString();
            // Set his token in the database
            isExist.get(0).setToken(token);
            isExist.get(0).save();
            return ok(Json.toJson(isExist.get(0)))
                    .withCookies(new Http.Cookie("token", token, null, "/", "localhost", false, false))
                    .withCookies(new Http.Cookie("id", isExist.get(0).getId().toString(), null, "/", "localhost", false, false));
        }

    }

    /**
     * Log out the person. Delete his cookies and set the value of his token in the database to null
     * @return If the person doesn't exist, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b>
     */
    public Result logout() {
        // Get attribute from the form
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String id = values.get("id")[0];
        String token = values.get("token")[0];

        Person isExist = Person.find.where().like("id", "%"+id+"%").like("token", "%"+token+"%").findUnique();
        if(isExist != null) {
            response().discardCookie("token");
            response().discardCookie("id");
            return ok();
        }
        else {
            return notFound("Your connection is expired or invalid. Impossible to log out");
        }
    }

    /**
     * Check if the user session is valid or not
     * @param id The ID of the person
     * @param token The token of the person
     * @return If the user has a valid session, return <b>200 Ok</b> <br/>
     * Else return <b>404 Not Found</b>
     */
    public Result isConnected(int id, String token) {
        Person isExist = Person.find.where().like("id", "%"+id+"%").like("token", "%"+token+"%").findUnique();
//        System.out.println("isConnected(id, token) FROM PersonController.java -- isExist="+isExist);
        if(isExist != null) {
            return ok(Json.toJson(isExist));
        }
        else {
            return notFound("Your connection is expired or invalid. Please log in again");
        }
    }

    /**
     * Return all products sell by a seller
     * @param id The id of the seller
     * @return <b>Erreur 404 Not Found</b> if the seller doesn't exist
     * Else <b>200 Ok</b> with the lists of the products
     */
    public Result getAllProductsForSeller(long id) {
        if(Person.find.byId(id) == null) {
            return notFound("Person not found.");
        }
        else {
            return ok(Json.toJson(Person.find.byId(id).getProductSell()));
        }
    }

    /**
     * Retrieve all seller of the database
     * @return All seller in the database, if there are no seller, return <b>404 NotFound</b>,
     * Else <b>200 Ok</b>
     */
    public Result getAllSeller() {
        return ok(Json.toJson(Person.find.where().eq("role", 1).findList()));
    }
    /**
     * Return all diaries of user
     * @param id The id of the user
     * @return <b>Erreur 404 Not Found</b> if the user doesn't exist
     * Else <b>200 Ok</b> with the lists of the diaries
     */
    public Result getAllDiaryForUser(long id) {
        if(Person.find.byId(id) == null) {
            return notFound("Person not found.");
        }
        else {
            return ok(Json.toJson(Person.find.byId(id).getDiaryUser()));
        }
    }
    public void initializePerson() {
        Person SU = new Person(null, "SimpleUser", "SU@a.com", "SU", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person SC = new Person(null, "SimpleSeller", "SC@a.com", "SC", "11111111111111", Codecs.sha1("pierrick34$"), 1, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person jsuisseller = new Person(null, "J'suisSeller", "seller@a.com", "Seller", "11111111111111", Codecs.sha1("pierrick34$"), 1, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person decatlhon = new Person(null, "decatlhon", "decatlhon@a.com", "decatlhon", "11111111111111", Codecs.sha1("pierrick34$"), 1, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person ADMIN = new Person(null, "Admin", "admin@a.com", "Admin", null, Codecs.sha1("pierrick34$"), 2, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person a = new Person(null, "a", "a@a.com", "a", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person b = new Person(null, "b", "b@b.com", "b", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person c = new Person(null, "c", "c@c.com", "c", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person d = new Person(null, "d", "d@d.com", "d", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person e = new Person(null, "e", "e@e.com", "e", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person f = new Person(null, "f", "f@f.com", "f", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person g = new Person(null, "g", "g@g.com", "g", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person h = new Person(null, "h", "h@h.com", "h", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person i = new Person(null, "i", "i@i.com", "i", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person j = new Person(null, "j", "j@j.com", "j", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person k = new Person(null, "k", "k@k.com", "k", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person l = new Person(null, "l", "l@l.com", "l", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person m = new Person(null, "m", "m@m.com", "m", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person n = new Person(null, "n", "n@n.com", "n", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person o = new Person(null, "o", "o@o.com", "o", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person p = new Person(null, "p", "p@p.com", "p", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person q = new Person(null, "q", "q@q.com", "q", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person r = new Person(null, "r", "r@r.com", "r", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person s = new Person(null, "s", "s@s.com", "s", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person t = new Person(null, "t", "t@t.com", "t", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person u = new Person(null, "u", "u@u.com", "u", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person v = new Person(null, "v", "v@v.com", "v", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person w = new Person(null, "w", "w@w.com", "w", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person x = new Person(null, "x", "x@x.com", "x", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person y = new Person(null, "y", "y@y.com", "y", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
        Person z = new Person(null, "z", "z@z.com", "z", null, Codecs.sha1("pierrick34$"), 0, "2", "rue emile pereire", "Béziers", "34500", null, null,null);
    }
}
