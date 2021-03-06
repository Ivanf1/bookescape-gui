package bookescape.gui;

import java.util.List;

import bookescape.persistence.CustomQuery;

public interface ICustomQueryProvider {
  public void executeCustomQuery(String query);  
  public List<CustomQuery> getCustomQuery();

}
