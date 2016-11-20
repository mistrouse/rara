import models.Person;
import models.Product;
import org.junit.Assert;
import org.junit.Test;

/**
 * Name of the class : ProductTest
 *
 * Description   : Test for the class Product
 *
 * Version       : 1.0
 *
 * Date          : 08/11/2016
 */
public class ProductTest {

    Product p1 = new Product(1l, "Name1", "Description1", 9.99, 10, Person.find.byId(1l), null);

    @Test
    public void TestSaveProduct() {
        Assert.assertNotNull(Product.find.byId(1l));
    }

    @Test
    public void TestGetOneProduct() {
        Assert.assertNotEquals("Product not found.",Product.find.byId(1l));
    }

    @Test
    public void TestGetAllProduct() {
        Product p2 = new Product(2l, "Name2", "Description2", 9.99, 10, Person.find.byId(1l), null);
        Assert.assertEquals(2, Product.find.all());
    }

    @Test
    public void TestDeleteProduct() {
        int numberOfProduct = Product.find.all().size();
        Assert.assertEquals(numberOfProduct, Product.find.all().size());
        Product.find.deleteById(1l);
        Assert.assertEquals(numberOfProduct-1,Product.find.all().size());
    }

    @Test
    public void TestUpdateProduct(){
        Assert.assertEquals(p1.getName(), "Name1");
        p1.setName("New name");
        Assert.assertEquals(p1.getName(), "New name");
    }
}
