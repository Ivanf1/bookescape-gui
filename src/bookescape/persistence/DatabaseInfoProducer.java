package bookescape.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInfoProducer implements IDatabaseInfoProducer {

  public static List<String> makeInfo() {
    Connection connection = DatabaseDriverConnection.getConnection();
    try {
      DatabaseMetaData metaData = connection.getMetaData();
      List<String> databaseInfo = new ArrayList<>();

      String[] types = { "TABLE" };

      ResultSet tables = metaData.getTables(null, null, "%", types);

      // Retrieving database name
      tables.first();
      String databaseName = tables.getString("TABLE_CAT");
      databaseInfo.add(databaseName);
      tables.first();

      // Retrieving the columns in the database
      while (tables.next()) {
        databaseInfo.add(tables.getString("TABLE_NAME"));
      }
      return databaseInfo;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  @Override
  public List<String> getInfo() {
    return makeInfo();
  }
  
}
