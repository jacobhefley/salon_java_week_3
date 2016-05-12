import java.util.*;
import org.sql2o.*;

public class Stylist {

  private String name;
  private int client_id;
  private int id;

  @Override
  public boolean equals(Object otherStylist){
    if(!(otherStylist instanceof Stylist)){
      return false;
    }else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getName().equals(newStylist.getName()) &&
      this.getId() == newStylist.getId();
    }
  }
  public Stylist(String name){
  	this.name = name;
    this.id = 0;
  }
  public Stylist(String name, int id){
  	this.name = name;
    this.id = id;
  }

  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  public static List<Stylist> all() {
    String sql = "SELECT id, name FROM stylists";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists where id=:id";
      Stylist stylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }
 public List<Client> getClients() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM clients where stylist_id=:id";
    return con.createQuery(sql)
      .addParameter("id", this.id)
      .executeAndFetch(Client.class);
  }
}
}
