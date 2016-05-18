import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void StylistList_emptyAtStart_0() {
    assertEquals(0, Stylist.all().size());
  }

  @Test
  public void testStylist_ConstructorObjectInstantiation_and_getPropertiesMethods() {
    Stylist newStylist = new Stylist("Alex");
    String expectedName = "Alex";
    assertEquals(true, newStylist instanceof Stylist);
    assertEquals(expectedName, newStylist.getName());
  }

  @Test
  public void equalsOverride_returnsTrueWhenStylistsAreSame_true() {
    Stylist firstStylist = new Stylist("Alex");
    Stylist secondStylist = new Stylist("Alex");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void test_allListofStylists_when_StylistsSaved() {
    Stylist newStylist = new Stylist("Alex");
    assertEquals(0, Stylist.all().size());
    newStylist.save();
    assertEquals(1, Stylist.all().size());
    assertTrue(Stylist.all().get(0).equals(newStylist));
    Stylist newStylist2 = new Stylist("Justin");
    newStylist2.save();
    assertEquals(2, Stylist.all().size());
    assertTrue(Stylist.all().get(1).equals(newStylist2));
    assertFalse(Stylist.all().get(1).equals(Stylist.all().get(0)));
  }

  @Test
  public void testStylist_find() {
    Stylist newStylist = new Stylist("Alex");
    Stylist newStylist2 = new Stylist("Justin");
    newStylist.save();
    newStylist2.save();
    assertTrue(Stylist.find(newStylist.getId()).equals(newStylist));
    assertTrue(Stylist.find(newStylist2.getId()).equals(newStylist2));
  }

  @Test
  public void testStylist_getClients() {
    Stylist newStylist = new Stylist("Alex");
    Stylist newStylist2 = new Stylist("Justin");
    newStylist.save();
    newStylist2.save();
    Client newClient = new Client("Smith, John", newStylist.getId());
    Client newClient2 = new Client("Smith, Josh", newStylist2.getId());
    newClient.save();
    newClient2.save();
    assertTrue(newStylist.getClients().get(0).getName().equals("Smith, John"));
    assertTrue(newStylist2.getClients().get(0).getName().equals("Smith, Josh"));
  }

}
