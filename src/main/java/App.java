import java.util.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import org.sql2o.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

  get("/", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    List<Stylist> stylists = Stylist.all();
    model.put("stylists", stylists);
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  // get("/stylists", (request, response) -> {
  //   HashMap<String, Object> model = new HashMap<String, Object>();
  //   List<Stylist> stylists = Stylist.all();
  //   model.put("stylists" ,Stylist.all());
  //   model.put("template", "templates/stylists.vtl");
  //   return new ModelAndView(model, layout);
  // }, new VelocityTemplateEngine());

  get("/clients", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("clients", Client.all());
    model.put("template", "templates/clients.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());


  post("/clients", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    List<Stylist> stylists = Stylist.all();
    int stylist2add2 = Integer.parseInt(request.queryParams("stylist2add2"));
    String clientName = request.queryParams("newClient");
    Client newClient = new Client(clientName, stylist2add2);
    newClient.save();
    model.put("clients", Client.all());
    model.put("template", "templates/clients.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/stylists", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    List<Stylist> stylists = Stylist.all();
    String newstylist = request.queryParams("newstylist");
    Stylist newStylist = new Stylist(newstylist);
    newStylist.save();
    model.put("stylists", Stylist.all());
    model.put("template", "templates/stylists.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  }
}
