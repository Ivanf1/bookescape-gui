package bookescape.persistence;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IQueryProvider {
  List<List<String>> executeQuery(String query);
  List<List<String>> executeQueryOnTable(String tableName);
  void executeDeleteQuery(String tableName, Map<String, String> rowToDelete);
  void executeUpdateQuery(String tableName, Map<String, String> rowToUpdate, Set<String> columnsToUpdate);
  List<List<String>> executeFilterQuery(String tableName, Set<String> selectedColumns, Map<String, String> conditions);
  public void executeInsertQuery(String tableName, Map<String, String> rowToInsert);

}
