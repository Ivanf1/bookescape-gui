package bookescape.persistence;

import java.util.List;
import java.util.Map;

public interface QueryProvider {
  List<List<String>> executeQuery(String query);
  List<List<String>> executeQueryOnTable(String tableName);
  void executeDeleteQuery(String tableName, Map<String, String> rowToDelete);

}
