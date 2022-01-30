package bookescape.gui;

import javax.swing.JFrame;
import javax.swing.UIManager;

import bookescape.persistence.QueryProvider;
import bookescape.persistence.DatabaseInfoProducer;
import bookescape.persistence.IDatabaseInfoProducer;
import bookescape.persistence.IQueryProvider;
import bookescape.utils.CustomQueryLoader;
import bookescape.utils.ICustomQueryProducer;

public class MainGUI {

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    IQueryProvider queryProvider = new QueryProvider();
    IDatabaseInfoProducer databaseInfoProducer = new DatabaseInfoProducer();
    ICustomQueryProducer customQueryProducer = new CustomQueryLoader();
    JFrame frame = new QueryFrame(queryProvider, databaseInfoProducer, customQueryProducer);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

}
