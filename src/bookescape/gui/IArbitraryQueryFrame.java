package bookescape.gui;

import java.util.List;

public interface IArbitraryQueryFrame {
  public void executeQuery(String query);  
  public void executeQueryOnTable(String tableName);
  public List<String> getDatabaseInfo();
}
