package controllers;

import models.ProductInBasket;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by pierrickgiuliani on 16/11/2016.
 */
public class BasketController extends Controller {

    /**
     * GET all line of basket in the database
     * @return The information of all Line in different basket in JSON format
     */
    public Result baskets() {
        return ok(Json.toJson(ProductInBasket.find.all()));
    }

    /**
     *  GET the line of the basket in the database with his ID if it exist
     * @param id The id of the basket line
     * @return The information of a basket line with the price, the quantity, the buyer and the product buy in JSON format <br/>
     * If the basket exist, return <b>200 Ok</b><br/>
     * Else return <b>404 Not Found</b>
     */
    public Result basket(long id) {
        if(ProductInBasket.find.byId(id) == null) {
            return notFound("Basket not found.");
        }
        else {
            return ok(Json.toJson(ProductInBasket.find.byId(id)));
        }
    }
}
