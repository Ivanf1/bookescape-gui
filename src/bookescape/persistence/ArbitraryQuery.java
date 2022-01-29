package bookescape.persistence;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

public class ArbitraryQuery implements QueryProvider {

  public ArbitraryQuery() {
  }

  /**
   * Executes an arbitrary query. First row of result is a list of column names.
   * To use only with queries that return a result.
   * 
   * @return {@link List} of result rows
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
      while (rs.next()) {
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

  /**
   * Executes a delete query
   */
  @Override
  public void executeDeleteQuery(String tableName, Map<String, String> rowToDelete) {
    Connection connection = DatabaseDriverConnection.getConnection();
    try {
      List<String> primaryKeys = getPrimaryKeysFromTable(connection, tableName);
      if (primaryKeys == null)
        return;

      // create a template query with where clause
      String query = "DELETE FROM " + tableName + " WHERE ";
      List<String> preparedKeys = new ArrayList<>();

      for (String primaryKey : primaryKeys) {
        preparedKeys.add(primaryKey + " = ?");
      }

      String whereClause = String.join(" AND ", preparedKeys);
      query += whereClause;

      // create prepared statement
      PreparedStatement s = connection.prepareStatement(query);

      // set values for where clause
      for (int i = 0; i < primaryKeys.size(); i++) {
        s.setString(i + 1, rowToDelete.get(primaryKeys.get(i)));
      }

      s.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Executes an update query
   */
  @Override
  public void executeUpdateQuery(String tableName, Map<String, String> rowToUpdate, Set<String> columnsToUpdate) {
    Connection connection = DatabaseDriverConnection.getConnection();
    try {
      List<String> primaryKeys = getPrimaryKeysFromTable(connection, tableName);
      if (primaryKeys == null)
        return;

      // create a template query
      String query = "UPDATE " + tableName;

      // add set clause with columns to update
      List<String> preparedSets = new ArrayList<>();
      for (String columnToUpdate : columnsToUpdate) {
        preparedSets.add(columnToUpdate + " = ?");
      }
      String setClause = String.join(", ", preparedSets);
      query += " SET " + setClause;

      // add where clause with primary keys
      List<String> preparedKeys = new ArrayList<>();
      for (String primaryKey : primaryKeys) {
        preparedKeys.add(primaryKey + " = ?");
      }
      String whereClause = String.join(" AND ", preparedKeys);
      query += " WHERE " + whereClause;

      // create prepared statement
      PreparedStatement s = connection.prepareStatement(query);

      // set values for set clause
      int i = 0;
      for (String columnToUpdate : columnsToUpdate) {
        s.setString(i + 1, rowToUpdate.get(columnToUpdate));
        i++;
      }

      // set values for where clause
      for (int j = 0; j < primaryKeys.size(); j++, i++) {
        s.setString(i + 1, rowToUpdate.get(primaryKeys.get(j)));
      }

      s.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * @return List of primary keys for table {@code tableName}
   * @throws SQLException
   */
  private List<String> getPrimaryKeysFromTable(Connection connection, String tableName) throws SQLException {
    DatabaseMetaData meta = connection.getMetaData();
    try (ResultSet tables = meta.getTables(null, null, tableName, new String[] { "TABLE" })) {

      if (!tables.next())
        return null;

      String catalog = tables.getString("TABLE_CAT");
      String schema = tables.getString("TABLE_SCHEM");
      try (ResultSet primaryKeys = meta.getPrimaryKeys(catalog, schema, tableName)) {
        List<String> primaryKeysList = new ArrayList<>();
        while (primaryKeys.next()) {
          primaryKeysList.add(primaryKeys.getString("COLUMN_NAME"));
        }
        return primaryKeysList;
      }
    }
  }

  private void executeQueryNoResult(String query) {
    Connection connection = DatabaseDriverConnection.getConnection();
    try {
      PreparedStatement s = connection.prepareStatement(query);
      s.execute(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<List<String>> executeQuery(String query) {
    String lowerCaseQueryIncipit = query.substring(0, 10).toLowerCase();
    if (lowerCaseQueryIncipit.startsWith("create") 
        || lowerCaseQueryIncipit.startsWith("insert")
        || lowerCaseQueryIncipit.startsWith("update")) {
      executeQueryNoResult(query);
      return null;
    }
    return executeArbitraryQuery(query);
  }

  @Override
  public List<List<String>> executeQueryOnTable(String tableName) {
    String query = "SELECT * FROM " + tableName + ";";
    return executeArbitraryQuery(query);
  }

}
