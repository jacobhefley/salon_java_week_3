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

  // @Test
  // public void stylistIsAddedTest() {
  //   goTo("http://localhost:4567/");
  //   click("a", withText("Alex"));
  //   fill("#name").with("House");
  //   submit(".btn");
  //   assertThat(pageSource()).contains("House");
  // }

  @Test
  public void stylistIsAddedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Stylists"));
    fill("#name").with("Alex");
    submit(".btn");
    assertThat(pageSource()).contains("Alex");
  }

}
