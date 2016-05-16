import java.util.*;
import org.sql2o.*;

public class Client {

  private int id;
  private String name;
  private int stylist_id;

  public Client(String name, int stylist_id){
  	this.name = name;
    this.stylist_id = stylist_id;
  }

  public int getId(){
  	return id;
  }

  public String getName(){
  	return name;
  }

  public int getStylistId() {
    return stylist_id;
  }

  public static List<Client> all() {
    String sql = "SELECT id, name, stylist_id FROM clients";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  @Override
  public boolean equals(Object otherClient){
    if(!(otherClient instanceof Client)){
      return false;
    }else {
      Client newClient = (Client) otherClient;
      return this.getName().equals(newClient.getName()) &&
           this.getId() == newClient.getId() &&
           this.getStylistId() == newClient.getStylistId();
    }
  }

  public void save() {
    if(Client.all().contains(this));
    else{
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO clients(name, stylist_id) VALUES (:name, :stylist_id)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("name", this.name)
          .addParameter("stylist_id", this.stylist_id)
          .executeUpdate()
          .getKey();
      }
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where id=:id";
      Client client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
      return client;
    }
  }

  public Stylist getStylist() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists where id=:id";
      Stylist stylist = con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }

  public List<Stylist> getStylists() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists where stylist_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Stylist.class);
    }
  }
}
