package bookescape.persistence;

import java.sql.*;

import bookescape.config.PropertyProducer;

public final class DatabaseDriverConnection {
  private static String dbUrl;
  private static String user;
  private static String pass;
  
  private static Connection connection;
  
  public static Connection getConnection() {
    if (connection == null) {
      try {
        dbUrl = PropertyProducer.getProperty("DB_URL");
        user = PropertyProducer.getProperty("USER");
        pass = PropertyProducer.getProperty("PASS");

        connection = DriverManager.getConnection(dbUrl, user, pass);

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return connection;
  }

}
