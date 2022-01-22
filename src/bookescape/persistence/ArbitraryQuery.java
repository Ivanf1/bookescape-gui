package bookescape.persistence;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ArbitraryQuery implements QueryProvider {
  
  public ArbitraryQuery() {
  }
  
  /**
   * Executes an arbitrary query. First row of result is
   * a list of column names
   * @returns @{@link List} of result rows
   */
  private static List<List<String>> executeArbitraryQuery(String query) {
    Connection connection = DatabaseDriverConnection.getConnection();
    try {
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(query);

      ResultSetMetaData rsMetadata = rs.getMetaData();
      Integer columnCount = rsMetadata.getColumnCount();

      List<List<String>> result = new ArrayList<>();
      
      // make heading with column names
      List<String> heading = new ArrayList<>();
      for (int i = 1; i <= columnCount; i++) {
        heading.add(rsMetadata.getColumnLabel(i));
      }
      result.add(heading);
      
      // add row by row
      while(rs.next()) {
        List<String> rowAsString = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
          rowAsString.add(rs.getString(i));
        }
        result.add(rowAsString);
      }
      
      return result;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  @Override
  public List<List<String>> executeQuery(String query) {
    return executeArbitraryQuery(query);
  }
  
  public static void printList(List<List<String>> list) {
    list.stream().forEach(l -> l.stream().forEach((s) -> System.out.println(s)));
  }
  
}
