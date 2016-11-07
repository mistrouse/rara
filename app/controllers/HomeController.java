package controllers;

import models.Person;
import models.Task;
import org.springframework.util.ResourceUtils;
import play.api.mvc.Cookie;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.api.mvc.Cookie;
import play.api.mvc.DiscardingCookie;

import java.util.Map;
import java.util.UUID;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok("Your new application is ready.");
    }

    /* ####################
       #       TASK       #
       ####################
     */
    // GET all the task in the database
    public Result tasks() {
        //Task t = new Task(null, "a", Person.find.byId(33l));
        return ok(Json.toJson(Task.find.all()));
    }

    // GET only one task in the database with his ID
    public Result task(Long id) {
        if(Task.find.byId(id) == null) {
            return notFound("Task not found.");
        }
        else {
            return ok(Json.toJson(Task.find.byId(id)));
        }
    }

    // UPDATE a task in the database with his ID and the values of the FORM
    public Result taskUpdate(Long id) {
        Task updateTask = Task.find.byId(id);
        if(updateTask == null) {
            return notFound("Task not found.");
        }
        // If exist, get the value of the form and do the update in the database
        else {
            // Get the value of the form
            final Map<String, String[]> values = request().body().asFormUrlEncoded();
            String newName = values.get("newName")[0];

            // Test if not empty, make the update, else nothing
            if(!newName.isEmpty())
                updateTask.setName(newName);

            // Make change in the database
            updateTask.save();
            return ok(Json.toJson(Task.find.byId(id)));
        }
    }

    // CREATE a task in the database with the values of the FORM
    public Result taskCreate() {
        // Get the value of the POST and stock in variable the values
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String name = values.get("name")[0];

        // Test if the task is already in the database before the save, if no return 201 created, else return 409 conflict
        Task isAlreadyExist = Task.find.where().like("name", "%"+name+"%").findUnique();
        if(isAlreadyExist == null) {
            // Create the task in the database with the information
            Task task = new Task(null, name, Person.find.byId(1l)); // #### A MODIFIER POUR RECUPERER L'ID DE LA PERSONNE EN COOKIE #####
            return created();
        }
        else {
            return status(409, "Task alreay exist in database");
        }
    }

    // DELETE a task in the the database with his ID
    public Result taskDelete(Long id) {
        if(Task.find.byId(id) == null) {
            return notFound("Task not found.");
        }
        else {
            Task.find.deleteById(id);
            return ok();
        }
    }
}
