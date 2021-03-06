import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("0NE ST0P BarberShop T00LS");
  }

  @Test
  public void stylistIsAdded() {
    goTo("http://localhost:4567/");
    click("a", withText("Stylists"));
    fill("#name").with("Alex");
    submit(".btn");
    assertThat(pageSource()).contains("Alex");
  }

  @Test
  public void stylist_and_client_Added() {
    goTo("http://localhost:4567/");
    click("a", withText("Stylists"));
    fill("#name").with("Alex");
    submit(".btn");
    click("a", withText("Alex"));
    fill("#name").with("Smith, Stanley");
    submit(".btn");
    assertThat(pageSource()).contains("Alex");
    assertThat(pageSource()).contains("Smith, Stanley");
  }

  @Test
  public void stylists_and_client_Added() {
    goTo("http://localhost:4567/");
    click("a", withText("Stylists"));
    fill("#name").with("Alex");
    submit(".btn");
    click("a", withText("Alex"));
    fill("#name").with("Smith, Stanley");
    submit(".btn");
    click("a", withText("Stylists"));
    fill("#name").with("Justin");
    submit(".btn");
    fill("#name").with("Doe, John");
    submit(".btn");
    click("a", withText("Clients"));
    assertThat(pageSource()).contains("Alex");
    assertThat(pageSource()).contains("Smith, Stanley");
    assertThat(pageSource()).contains("Justin");
    assertThat(pageSource()).contains("Doe, John");
  }

  @Test
  public void multiple_stylists_and_clients_Added() {
    goTo("http://localhost:4567/");
    click("a", withText("Stylists"));
    fill("#name").with("Alex");
    submit(".btn");
    click("a", withText("Alex"));
    fill("#name").with("Smith, Stanley");
    submit(".btn");
    click("a", withText("Stylists"));
    fill("#name").with("Justin");
    submit(".btn");
    fill("#name").with("Doe, John");
    submit(".btn");
    fill("#name").with("Johnson, John");
    submit(".btn");
    click("a", withText("Stylists"));
    click("a", withText("Alex"));
    fill("#name").with("Herfner, Rick");
    submit(".btn");
    click("a", withText("Clients"));
    assertThat(pageSource()).contains("Alex");
    assertThat(pageSource()).contains("Smith, Stanley");
    assertThat(pageSource()).contains("Justin");
    assertThat(pageSource()).contains("Doe, John");
    assertThat(pageSource()).contains("Johnson, John");
    assertThat(pageSource()).contains("Herfner, Rick");
  }

  @Test
  public void same_client_to_different_stylist_decline() {
    goTo("http://localhost:4567/");
    click("a", withText("Stylists"));
    fill("#name").with("Alex");
    submit(".btn");
    click("a", withText("Alex"));
    fill("#name").with("Smith, Stanley");
    submit(".btn");
    click("a", withText("Stylists"));
    fill("#name").with("Justin");
    submit(".btn");
    fill("#name").with("Doe, John");
    submit(".btn");
    fill("#name").with("Johnson, John");
    submit(".btn");
    click("a", withText("Stylists"));
    click("a", withText("Alex"));
    fill("#name").with("Herfner, Rick");
    submit(".btn");
    fill("#name").with("Smith, Stanley");
    submit(".btn");
    assertThat(pageSource()).contains("Client is already taken!");
    click("a", withText("Clients"));
    assertThat(pageSource()).contains("Alex");
    assertThat(pageSource()).contains("Smith, Stanley");
    assertThat(pageSource()).contains("Justin");
    assertThat(pageSource()).contains("Doe, John");
    assertThat(pageSource()).contains("Johnson, John");
    assertThat(pageSource()).contains("Herfner, Rick");
  }

}
