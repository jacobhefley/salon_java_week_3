import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void ClientList_emptyAtStart_0() {
    assertEquals(0, Client.all().size());
  }

  @Test
  public void testClient_ConstructorObjectInstantiation_and_getPropertiesMethods() {
    Client newClient = new Client("Smith, John", 1);
    String expectedName = "Smith, John";
    int expectedStylistID = 1;
    assertEquals(true, newClient instanceof Client);
    assertEquals(expectedName, newClient.getName());
    assertEquals(expectedStylistID, newClient.getStylistId());
  }

  @Test
  public void equalsOverride_returnsTrueWhenClientsAreSame_true() {
    Client firstClient = new Client("Smith, John", 1);
    Client secondClient = new Client("Smith, John", 1);
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void test_allListofClients_when_ClientsSaved() {
    Client newClient = new Client("Smith, John", 99);
    assertEquals(0, Client.all().size());
    newClient.save();
    assertEquals(1, Client.all().size());
    assertTrue(Client.all().get(0).equals(newClient));
    Client newClient2 = new Client("Smith, Josh", 55);
    newClient2.save();
    assertEquals(2, Client.all().size());
    assertTrue(Client.all().get(1).equals(newClient2));
    assertFalse(Client.all().get(1).equals(Client.all().get(0)));
  }

  @Test
  public void testClient_find() {
    Client newClient = new Client("Smith, John", 99);
    Client newClient2 = new Client("Smith, Josh", 55);
    newClient.save();
    newClient2.save();
    assertTrue(Client.find(newClient.getId()).equals(newClient));
    assertTrue(Client.find(newClient2.getId()).equals(newClient2));
  }

  @Test
  public void testClient_existsAfterSave() {
    Client newClient = new Client("Smith, John", 99);
    Client newClient2 = new Client("Smith, Josh", 55);
    newClient.save();
    assertTrue(Client.exists("Smith, John"));
    assertFalse(Client.exists("Smith, Josh"));
  }



}
