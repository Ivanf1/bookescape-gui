package bookescape.gui;

import javax.swing.JFrame;
import javax.swing.UIManager;

import bookescape.persistence.ArbitraryQuery;
import bookescape.persistence.DatabaseInfoProducer;
import bookescape.persistence.IDatabaseInfoProducer;
import bookescape.persistence.QueryProvider;
import bookescape.utils.CustomQueryLoader;
import bookescape.utils.ICustomQueryProducer;

public class MainGUI {

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    QueryProvider queryProvider = new ArbitraryQuery();
    IDatabaseInfoProducer databaseInfoProducer = new DatabaseInfoProducer();
    ICustomQueryProducer customQueryProducer = new CustomQueryLoader();
    JFrame frame = new ArbitraryQueryFrame(queryProvider, databaseInfoProducer, customQueryProducer);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

}
