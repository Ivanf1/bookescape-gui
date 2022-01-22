package bookescape.gui;

import java.util.List;

public interface QueryProvider {
  List<List<String>> executeQuery(String query);

}
