import models.Person;
import org.junit.Assert;
import org.junit.Test;

/**
 * Name of the class : PersonTest
 *
 * Description   : Test for the class Person
 *
 * Version       : 1.0
 *
 * Date          : 03/11/2016
 */
public class PersonTest {

    Person p = new Person(1l, "Name", "email@a.com", "pseudo", null, "password", 0, "1", "Street address", "City address", "34000", null, null, null, null, null);

    @Test
    public void TestSavePerson() {
        Assert.assertNotNull(Person.find.byId(1l));
    }

    @Test
    public void TestGetOnePerson() {
        Assert.assertNotEquals("Person not found.",Person.find.byId(1l));
    }

    @Test
    public void TestGetAllPerson() {
        Person p2 = new Person(2l, "Name2", "email2@a.com", "pseudo2", null, "password2", 0, "1", "Street address2", "City address2", "34000", null, null, null, null, null);
        Assert.assertEquals(2, Person.find.all());
    }

    @Test
    public void TestDeletePerson() {
        int numberOfPerson = Person.find.all().size();
        Assert.assertEquals(numberOfPerson, Person.find.all().size());
        Person.find.deleteById(1l);
        Assert.assertEquals(numberOfPerson-1,Person.find.all().size());
    }

    @Test
    public void TestUpdatePerson(){
        Assert.assertEquals(p.getName(), "Name");
        p.setName("New name");
        Assert.assertEquals(p.getName(), "New name");
    }
}
