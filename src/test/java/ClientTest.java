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

}
