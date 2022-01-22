package bookescape.libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bookescape.persistence.DatabaseDriverConnection;

public class LibroDAO {

  public static Libro getByIsbn(String isbn) {
    Connection connection = DatabaseDriverConnection.getConnection();
    try {
      PreparedStatement ps = connection.prepareStatement("SELECT * FROM libro l WHERE l.isbn_13 = ?");
      ps.setString(1, isbn);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        Libro l = new Libro();
        l.setIsbn13(rs.getString("isbn_13"));
        l.setTitolo(rs.getString("titolo"));
        l.setDescrizione(rs.getString("descrizione"));
        l.setDataPub(rs.getDate("data_pub"));
        l.setImg(rs.getString("img"));
        l.setNumeroVoti(rs.getInt("numero_voti"));
        l.setTotaleVoti(rs.getInt("totale_voti"));
        return l;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public static List<Libro> getByTitolo(String titolo) {
    Connection connection = DatabaseDriverConnection.getConnection();
    try {
      PreparedStatement ps = connection.prepareStatement("SELECT * FROM libro l WHERE l.titolo LIKE CONCAT('%', ?, '%')");
      ps.setString(1, titolo);
      ResultSet rs = ps.executeQuery();
      List<Libro> list = new ArrayList<>();
      while(rs.next()) {
        Libro l = new Libro();
        l.setIsbn13(rs.getString("isbn_13"));
        l.setTitolo(rs.getString("titolo"));
        l.setDescrizione(rs.getString("descrizione"));
        l.setDataPub(rs.getDate("data_pub"));
        l.setImg(rs.getString("img"));
        l.setNumeroVoti(rs.getInt("numero_voti"));
        l.setTotaleVoti(rs.getInt("totale_voti"));
        list.add(l);
      }
      return list;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

}
