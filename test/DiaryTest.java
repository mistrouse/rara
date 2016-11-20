import models.Diary;
import models.Objective;
import models.Person;
import org.junit.Assert;
import org.junit.Test;

/**
 * Name of the class : DiaryTest
 *
 * Description   : Test for the class Diary
 *
 * Version       : 1.0
 *
 * Date          : 11/11/2016
 */
public class DiaryTest {

    Diary d1 = new Diary(1l, "Title1", "Description1", "Date1", Person.find.byId(1l), Objective.find
    .byId(1l));

    @Test
    public void TestSaveDiary() {
        Assert.assertNotNull(Diary.find.byId(1l));
    }

    @Test
    public void TestGetOneDiary() {
        Assert.assertNotEquals("Diary not found.", Diary.find.byId(1l));
    }

    @Test
    public void TestGetAllDiary() {
        Diary d2 = new Diary(2l, "Title2", "Description2", "Date2", Person.find.byId(1l), Objective.find.byId(1l));
        Assert.assertEquals(2, Diary.find.all());
    }

    @Test
    public void TestDeleteDiary() {
        int numberOfDiary = Diary.find.all().size();
        Assert.assertEquals(numberOfDiary, Diary.find.all().size());
        Diary.find.deleteById(1l);
        Assert.assertEquals(numberOfDiary-1,Diary.find.all().size());
    }

    @Test
    public void TestUpdateDiary(){
        Assert.assertEquals(d1.getTitle(), "Name");
        d1.setTitle("New name");
        Assert.assertEquals(d1.getTitle(), "New name");
    }
}
