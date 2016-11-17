package controllers;

import models.Person;
import models.Product;
import models.ProductInBasket;
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
     * If the person is not a seller or an admin, return <b>404 Not Found</b> <br/>
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

        Person isSellerOrAdmin = Person.find.byId(Long.valueOf(id));
        if(isSellerOrAdmin.getRole() == 1 || isSellerOrAdmin.getRole() == 2){
            // Create the product in the database with the informations
            Product product = new Product(null, name, description, Double.parseDouble(price), Integer.parseInt(quantity), Person.find.byId(Long.valueOf(id)), null);
            return created("The product has been created");
        }
        else {
            return notFound("The person is not a seller or an admin");
        }
    }

    /**
     * Update a product with the data in the form
     * @param id The id of the product to update
     * @return The information of a Product in JSON format <br/>
     * If the product doesn't exist in the database, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b>
     */
    public Result productUpdate(long id) {
        // Get the product in the database, if not exist return 404 not found
        Product updateProduct = Product.find.byId(id);
        if(updateProduct == null) {
            return notFound("Product not found.");
        }
        // If exist, get the value of the form and do the update in the database
        else {
            // Get the value of the form
            final Map<String, String[]> values = request().body().asFormUrlEncoded();
            String newName = values.get("newName")[0];
            String newDescription = values.get("newDescription")[0];
            String newPrice = values.get("newPrice")[0];
            String newQuantity = values.get("newQuantity")[0];
            //String newImage = values.get("newImage")[0];

            // Test if not empty, make the update, else nothing
            if(!newName.isEmpty())
                updateProduct.setName(newName);
            if(!newDescription.isEmpty())
                updateProduct.setDescription(newDescription);
            if(!newPrice.isEmpty())
                updateProduct.setPrice(Double.parseDouble(newPrice));
            if(!newQuantity.isEmpty())
                updateProduct.setQuantity(Integer.parseInt(newQuantity));
            /*if(!newImage.isEmpty())
                updateProduct.setId(newImage);*/

            // Make change in the database
            updateProduct.save();
//            System.out.println("personUpdate(id) from PersonController -- updatePerson="+updatePerson);
            return ok(Json.toJson(Product.find.byId(id)));
        }
    }

    /**
     * DELETE a product in the database with his ID
     * @param id The id of a Product
     * @return If the product doesn't exist in the dababase, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b>
     */
    public Result productDelete(long id) {
        if(Product.find.byId(id) == null) {
            return notFound("Product not found.");
        }
        else {
            Product.find.deleteById(id);
            return ok("The product has been deleted");
        }
    }

    /**
     * Add a product in the basket of the buyer.
     * @param id The id of the product
     * @return If the product doesn't exist, return <b>404 Not Found</b> <br/>
     * Else return <b>200 Ok</b>
     */
    public Result addToBasket(long id) {
        // Get the product in the database, if not exist return 404 not found
        Product buyProduct = Product.find.byId(id);
        if(buyProduct == null) {
            return notFound("Product not found.");
        }
        // If exist, add the product in the buyer basket
        else {
            final Map<String, String[]> values = request().body().asFormUrlEncoded();
            String quantityPurchased = values.get("quantityPurchased")[0];
            String idBuyer = values.get("id_buyer")[0];
            // TEST BASKET
            Person buyer = Person.find.byId(Long.valueOf(idBuyer));
            buyer.getBasket().add(new ProductInBasket(null, buyProduct.getPrice(), Integer.parseInt(quantityPurchased), buyer, buyProduct));
            //TEST BASKET
            return ok(Json.toJson(buyProduct));
        }
    }

    public void initializeProduct() {
        Product p = new Product(null, "Téléphone", "Ceci est un téléphone...", 499.99, 2, Person.find.byId(2l), null);
        /*Product p1 = new Product(null, "Crayon", "Mine extra fine!", 9.99, 10, Person.find.byId(2l));
        Product p2 = new Product(null, "Cure dent", "Lot de 100 cure dent", 5.0, 10, Person.find.byId(3l));*/
    }
}