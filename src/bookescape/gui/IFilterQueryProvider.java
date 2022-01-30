package bookescape.gui;

import java.util.Map;
import java.util.Set;

public interface IFilterQueryProvider {
  void executeFilterQuery(String tableName, Set<String> selectedColumns, Map<String, String> conditions);

}
