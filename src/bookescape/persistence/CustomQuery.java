package bookescape.persistence;

public class CustomQuery {
  private String name;
  private String comment;
  private String query;

  public CustomQuery(String comment, String query) {
    this.name = "";
    this.comment = comment;
    this.query = query;
  }

  public CustomQuery(String name, String comment, String query) {
    this.name = name;
    this.comment = comment;
    this.query = query;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String prettyPrint() {
    return comment + "\n" + query;
  }
  
}
