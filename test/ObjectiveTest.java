import models.Objective;
import models.Person;
import org.junit.Assert;
import org.junit.Test;

/**
 * Name of the class : ObjectiveTest
 *
 * Description   : Test for the class Objective
 *
 * Version       : 1.0
 *
 * Date          : 10/11/2016
 */
public class ObjectiveTest {

    Objective obj1 = new Objective(1l, "Name1", "Description1", Person.find.byId(1l));

    @Test
    public void TestSaveObjective() {
        Assert.assertNotNull(Objective.find.byId(1l));
    }

    @Test
    public void TestGetOneObjective() {
        Assert.assertNotEquals("Objective not found.", Objective.find.byId(1l));
    }

    @Test
    public void TestGetAllObjective() {
        Objective obj2 = new Objective(2l, "Name2", "Description2", Person.find.byId(1l));
        Assert.assertEquals(2, Objective.find.all());
    }

    @Test
    public void TestDeleteObjective() {
        int numberOfObjective = Objective.find.all().size();
        Assert.assertEquals(numberOfObjective, Objective.find.all().size());
        Objective.find.deleteById(1l);
        Assert.assertEquals(numberOfObjective-1,Objective.find.all().size());
    }

    @Test
    public void TestUpdateObjective(){
        Assert.assertEquals(obj1.getName(), "Name");
        obj1.setName("New name");
        Assert.assertEquals(obj1.getName(), "New name");
    }
}
