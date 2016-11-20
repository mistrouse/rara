import models.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by pierrickgiuliani on 20/11/2016.
 */
public class ProductInBasketTest {

    ProductInBasket pib1 = new ProductInBasket(1l, 10, Person.find.byId(1l), Product.find.byId(1l));

    @Test
    public void TestSaveProductInBasket() {
        Assert.assertNotNull(ProductInBasket.find.byId(1l));
    }

    @Test
    public void TestGetOneProductInBasket() {
        Assert.assertNotEquals("Basket not found.", ProductInBasket.find.byId(1l));
    }

    @Test
    public void TestGetAllProductInBasket() {
        ProductInBasket pib2 = new ProductInBasket(2l, 1, Person.find.byId(1l), Product.find.byId(2l));
        Assert.assertEquals(2, ProductInBasket.find.all());
    }

    @Test
    public void TestDeleteProductInBasket() {
        int numberOfProductInBasket = ProductInBasket.find.all().size();
        Assert.assertEquals(numberOfProductInBasket, ProductInBasket.find.all().size());
        ProductInBasket.find.deleteById(1l);
        Assert.assertEquals(numberOfProductInBasket-1,ProductInBasket.find.all().size());
    }

    @Test
    public void TestUpdateProductInBasket(){
        Assert.assertEquals(pib1.getQuantity(), 10);
        pib1.setQuantity(11);
        Assert.assertEquals(pib1.getQuantity(), 11);
    }
}
