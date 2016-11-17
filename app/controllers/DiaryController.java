package controllers;

import models.Diary;
import models.Person;
import java.util.Date;

import org.joda.time.DateTime;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Map;

/**
 * Created by Djeneba on 11/11/2016.
 */
public class DiaryController extends Controller {
    /**
     * GET all diaries in the database
     * @return The information of all Diaries in JSON format
     */
    public Result diaries() {
        //this.initializeDiary();
        return ok(Json.toJson(Diary.find.all()));
    }



    /**
     *  GET the diary in the database with his ID if it exist
     * @param id The id of a diary
     * @return The information of a diary in JSON format <br/>
     * If the diary exist, return <b>200 Ok</b><br/>
     * Else return <b>404 Not Found</b>
     */
    public Result diary(long id) {
        if(Diary.find.byId(id) == null) {
            return notFound("Diary not found.");
        }
        else {
            return ok(Json.toJson(Diary.find.byId(id)));
        }
    }
    /**
     * CREATE a diary in the database with the values of the FORM
     * @return The diary is created <br/>
     * If the person is not a user, return <b>404 Not Found</b> <br/>
     * Else return <b>201 Created</b>
     */
    public Result diaryCreate() {
        // Get the value of the POST and stock in variable the values
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String title = values.get("title")[0];
        String description = values.get("description")[0];
        DateTime date =DateTime.now();
        String dateDiary=date.toString("dd/MM/yy HH:mm");

        String id = values.get("id")[0];
        Person isUser = Person.find.byId(Long.valueOf(id));
        if(isUser.getRole() != 0) {
            return notFound("The person is not an user");
        }
        else {
            // Create the diary in the database with the informations
            Diary diary = new Diary(null, title, description,dateDiary, Person.find.byId(Long.valueOf(id)));
            return created("The diary has been created");

        }
    }
    /**
     * Update a diary with the data in the form
     * @param id The id of the diary to update
     * @return The information of a diary in JSON format <br/>
     * If the diary doesn't exist in the database, return <b>404 Not Found</b> <br/>
     */

    public Result diaryUpdate(long id) {
        // Get the diary in the database, if not exist return 404 not found
        Diary updateDiary = Diary.find.byId(id);
        if(updateDiary == null) {
            return notFound("Diary not found.");
        }
        // If exist, get the value of the form and do the update in the database
        else {
            // Get the value of the form
            final Map<String, String[]> values = request().body().asFormUrlEncoded();
            String newTitle = values.get("newTitle")[0];
            String newDescription = values.get("newDescription")[0];
            DateTime date =DateTime.now();
            String dateDiary=date.toString("dd/MM/yy HH:mm");
            // Test if not empty, make the update, else nothing
            if(!newTitle.isEmpty())
                updateDiary.setTitle(newTitle);
            if(!newDescription.isEmpty())
                updateDiary.setDescription(newDescription);
            updateDiary.setDateDiary(dateDiary);
            // Make change in the database
            updateDiary.save();
//            System.out.println("personUpdate(id) from PersonController -- updatePerson="+updatePerson);
            return ok(Json.toJson(Diary.find.byId(id)));
        }
    }

    /**
     * DELETE a diary in the database with his ID
     * @param id The id of a Diary
     * @return If the diary doesn't exist in the dababase, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b>
     */
    // Delete a diary may delete all comments on this diary
    // Have to delete all comment of this diary
    public Result diaryDelete(long id) {
        if(Diary.find.byId(id) == null) {
            return notFound("Diary not found.");
        }
        else {
            Diary.find.deleteById(id);
            return ok("The Diary has been deleted");
        }
    }

    public void initializeDiary() {
        // System.out.println(Person.find.byId(2l));
        //Diary d = new Diary(null, "Fete Nouvel an", "Ceci est une invitation...",Date.now(), Person.find.byId(2l));
    }
}