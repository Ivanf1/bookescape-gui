package bookescape.gui;

import java.util.List;
import java.util.Map;

public interface IArbitraryQueryFrame {
  public void executeQuery(String query);  
  public void executeQueryOnTable(String tableName);
  public void executeDeleteQuery(String tableName, Map<String, String> rowToDelete);
  public List<String> getDatabaseInfo();
}
