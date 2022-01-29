package bookescape.persistence;

import java.util.List;

public interface QueryProvider {
  List<List<String>> executeQuery(String query);
  List<List<String>> executeQueryOnTable(String tableName);

}
