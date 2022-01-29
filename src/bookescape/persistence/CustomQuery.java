package bookescape.persistence;

public class CustomQuery {
  private String comment;
  private String query;

  public CustomQuery(String comment, String query) {
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
  
  public String prettyPrint() {
    return comment + "\n" + query;
  }
  
}
