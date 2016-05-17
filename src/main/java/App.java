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
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/stylists", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("stylists" ,Stylist.all());
    model.put("template", "templates/stylists.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/stylists", (request, response) -> {
    String name = request.queryParams("name");
    Stylist newStylist = new Stylist(name);
    newStylist.save();
    response.redirect("/stylists");
    return null;
  });

  get("/stylists/:id", (request,response) ->{
    HashMap<String, Object> model = new HashMap<String, Object>();
    Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
    model.put("stylist", stylist);
    model.put("allClients", Client.all());
    model.put("template", "templates/stylist.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/clients", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("stylists" ,Stylist.all());
    model.put("clients" ,Client.all());
    model.put("template", "templates/clients.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/clients", (request, response) -> {
    String name = request.queryParams("name");
    Stylist newStylist = new Stylist(name);
    newStylist.save();
    response.redirect("/stylists");
    return null;
  });

  post("/add_client", (request, response) -> {
    String name = request.queryParams("name");
    int stylist_id = Integer.parseInt(request.queryParams("stylist_id"));
    if(Client.exists(name)){
      response.redirect("/clientexists");
    }
    else{
      Client client = new Client(name, stylist_id);
      client.save();
      response.redirect("/stylists/" + stylist_id);
    }
    return null;
  });

  get("/clientexists", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("template", "templates/clientexists.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());
  }
}
