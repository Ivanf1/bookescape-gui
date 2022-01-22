package bookescape.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

import java.util.List;

public class ArbitraryQueryFrame extends JFrame {
  private static final long serialVersionUID = -5148352205350265400L;
  
  private ArbitraryQueryQueryPanel queryPanel;
  private ArbitraryQueryQueryResultPanel resultPanel;
  private QueryProvider queryProvider;
  private JPanel container;

  public ArbitraryQueryFrame(QueryProvider queryProvider) {
    this.queryProvider = queryProvider;
    this.resultPanel = new ArbitraryQueryQueryResultPanel();
    this.queryPanel = new ArbitraryQueryQueryPanel(this);
    
    this.container = new JPanel();
    container.setLayout(new GridLayout(2, 1));
    
    this.setSize(1600, 960);
    this.setVisible(true);
    container.add(queryPanel);
    container.add(resultPanel);
    this.add(container);
    //this.add(resultPanel);
  }
  
  public void executeQuery(String query) {
    List<List<String>> res = queryProvider.executeQuery(query);
    resultPanel.updateResultTable(res);
  }
  
}
