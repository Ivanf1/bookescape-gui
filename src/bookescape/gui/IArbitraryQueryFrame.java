package bookescape.gui;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IArbitraryQueryFrame {
  public void executeQuery(String query);  
  public void executeQueryOnTable(String tableName);
  public void executeDeleteQuery(String tableName, Map<String, String> rowToDelete);
  void executeUpdateQuery(String tableName, Map<String, String> rowToUpdate, Set<String> columnsToUpdate);
  public List<String> getDatabaseInfo();

}
