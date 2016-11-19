package controllers;

import models.ProductInBasket;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Name of the class : BasketController
 *
 * Description   : Controller to manage a Basket
 *
 * Version       : 1.0
 *
 * Date          : 16/11/2016
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
     *  GET the lines of the basket in the database with his ID if it exist
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

    /**
     * DELETE a ProductInBasket in the database with his ID
     * @param id The id of a ProductInBasket
     * @return If the ProductInBasket doesn't exist in the dababase, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b>
     */
    public Result productInBasketDelete(Long id) {
        if(ProductInBasket.find.byId(id) == null) {
            return notFound("ProductInBasket not found.");
        }
        else {
            ProductInBasket.find.deleteById(id);
            return ok("The ProductInBasket has been deleted");
        }
    }


}
