package controllers;

import models.Objective;
import models.Person;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Map;

/**
 * Name of the class : ObjectiveController
 *
 * Description   : Controller to manage an Objective
 *
 * Version       : 1.0
 *
 * Date          : 10/11/2016
 */
public class ObjectiveController extends Controller {

    /**
     * GET all objectives in the database
     * @return The information of all Objective in JSON format
     */
    public Result objectives() {
        this.initializeObjective();
        return ok(Json.toJson(Objective.find.all()));
    }

    /**
     *  GET the objective in the database with his ID if it exists
     * @param id The id of an Objective
     * @return The information of an objective in JSON format <br/>
     * If the objective exists, return <b>200 Ok</b><br/>
     * Else return <b>404 Not Found</b>
     */
    public Result objective(long id) {
        if(Objective.find.byId(id) == null) {
            return notFound("Objective not found.");
        }
        else {
            return ok(Json.toJson(Objective.find.byId(id)));
        }
    }

    /**
     * CREATE an objective in the database with the values of the FORM
     * @return The objective is created <br/>
     * If the person is not a simple user, return <b>404 Not Found</b> <br/>
     * Else return <b>201 Created</b>
     */
    public Result objectiveCreate() {
        // Get the value of the POST and stock in variable the values
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String name = values.get("name")[0];
        String description = values.get("description")[0];
        String id = values.get("id")[0];

        Person isSU = Person.find.byId(Long.valueOf(id));
        if(isSU.getRole() != 0) {
            return notFound("The person is not a simple user");
        }
        else {
            // Create the objective in the database with the information
            Objective objective = new Objective(null, name, description, Person.find.byId(Long.valueOf(id)));
            return created("The objective has been created");
        }
    }

    /**
     * Update an objective with the data in the form
     * @param id The id of the objective to update
     * @return The information of an objective in JSON format <br/>
     * If the objective doesn't exist in the database, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b>
     */
    public Result objectiveUpdate(long id) {
        // Get the objective in the database, if not exist return 404 not found
        Objective updateObjective = Objective.find.byId(id);
        if(updateObjective == null) {
            return notFound("Objective not found.");
        }
        // If exist, get the value of the form and do the update in the database
        else {
            // Get the value of the form
            final Map<String, String[]> values = request().body().asFormUrlEncoded();
            String newName = values.get("newName")[0];
            String newDescription = values.get("newDescription")[0];

            // Test if not empty, make the update, else nothing
            if(!newName.isEmpty())
                updateObjective.setName(newName);
            if(!newDescription.isEmpty())
                updateObjective.setDescription(newDescription);

            // Make change in the database
            updateObjective.save();
            return ok(Json.toJson(Objective.find.byId(id)));
        }
    }

    /**
     * DELETE an objective in the database with his ID
     * @param id The id of an objective
     * @return If the objective doesn't exist in the database, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b>
     */
    public Result objectiveDelete(long id) {
        if(Objective.find.byId(id) == null) {
            return notFound("Objective not found.");
        }
        else {
            Objective.find.deleteById(id);
            return ok("The objective has been deleted");
        }
    }

    public void initializeObjective() {
        System.out.println(Person.find.byId(2l));
        Objective o = new Objective(null, "Apprendre à coder en Scala", "Je veux connaître Scala pour faire plaisir à M. Castelltort", Person.find.byId(2l));
    }

}

