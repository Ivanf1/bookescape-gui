package bookescape.gui;

import javax.swing.JFrame;
import javax.swing.UIManager;

import bookescape.persistence.ArbitraryQuery;
import bookescape.persistence.QueryProvider;

public class MainGUI {

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    QueryProvider queryProvider = new ArbitraryQuery();
    JFrame frame = new ArbitraryQueryFrame(queryProvider);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

}
