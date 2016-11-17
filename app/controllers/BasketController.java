package controllers;

import models.Basket;
import models.Product;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by pierrickgiuliani on 16/11/2016.
 */
public class BasketController extends Controller {

    /**
     * GET all baskets in the database
     * @return The information of all Basket in JSON format
     */
    public Result baskets() {
        return ok(Json.toJson(Basket.find.all()));
    }

    /**
     *  GET the basket in the database with his ID if it exist
     * @param id The id of a Basket
     * @return The information of a Basket in JSON format <br/>
     * If the basket exist, return <b>200 Ok</b><br/>
     * Else return <b>404 Not Found</b>
     */
    public Result basket(long id) {
        if(Basket.find.byId(id) == null) {
            return notFound("Basket not found.");
        }
        else {
            return ok(Json.toJson(Basket.find.byId(id)));
        }
    }
}
