package controllers;

import models.Person;
import models.Product;
import play.api.libs.Codecs;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Map;

/**
 * Created by pierrickgiuliani on 08/11/2016.
 */
public class ProductController extends Controller {

    /**
     * GET all products in the database
     * @return The information of all Product in JSON format
     */
    public Result products() {
        //this.initializeProduct();
        return ok(Json.toJson(Product.find.all()));
    }

    /**
     *  GET the product in the database with his ID if it exist
     * @param id The id of a Product
     * @return The information of a Product in JSON format <br/>
     * If the product exist, return <b>200 Ok</b><br/>
     * Else return <b>404 Not Found</b>
     */
    public Result product(long id) {
        if(Product.find.byId(id) == null) {
            return notFound("Product not found.");
        }
        else {
            return ok(Json.toJson(Product.find.byId(id)));
        }
    }

    /**
     * CREATE a product in the database with the values of the FORM
     * @return The product is created <br/>
     * If the person is not a seller, return <b>404 Not Found</b> <br/>
     * Else return <b>201 Created</b>
     */
    public Result productCreate() {
        // Get the value of the POST and stock in variable the values
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String name = values.get("name")[0];
        String description = values.get("description")[0];
        String price = values.get("price")[0];
        String quantity = values.get("quantity")[0];
        String id = values.get("id")[0];

        Person isSeller = Person.find.byId(Long.valueOf(id));
        if(isSeller.getRole() != 1) {
            return notFound("The person is not a seller");
        }
        else {
            // Create the product in the database with the informations
            Product product = new Product(null, name, description, Double.parseDouble(price), Integer.parseInt(quantity), Person.find.byId(Long.valueOf(id)));
            return created("The product has been created");

        }
    }

    public Result productUpdate(long id) {
        return TODO;
    }

    public Result productDelete(long id) {
        return TODO;
    }

    public void initializeProduct() {
        System.out.println(Person.find.byId(2l));
        Product p = new Product(null, "Téléphone", "Ceci est un téléphone...", 12.12, 12, Person.find.byId(2l));
    }
}
