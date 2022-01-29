package bookescape.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bookescape.persistence.IDatabaseInfoProducer;
import bookescape.persistence.QueryProvider;

import java.awt.*;

import java.util.List;
import java.util.Map;

public class ArbitraryQueryFrame extends JFrame implements IArbitraryQueryFrame {
  private static final long serialVersionUID = -5148352205350265400L;
  
  private ArbitraryQueryQueryPanel queryPanel;
  private ArbitraryQueryQueryResultPanel resultPanel;
  private DatabaseInfoPanel databaseInfoPanel;
  private QueryProvider queryProvider;
  private IDatabaseInfoProducer databaseInfoProducer;
  private JPanel container;

  public ArbitraryQueryFrame(QueryProvider queryProvider, IDatabaseInfoProducer databaseInfoProducer) {
    this.queryProvider = queryProvider;
    this.databaseInfoProducer = databaseInfoProducer;
    this.resultPanel = new ArbitraryQueryQueryResultPanel(this);
    this.queryPanel = new ArbitraryQueryQueryPanel(this);
    this.databaseInfoPanel = new DatabaseInfoPanel(this);
    
    initLayout();
  }
  
  private void initLayout() {
    this.container = new JPanel();
    container.setLayout(new GridBagLayout());
    
    GridBagConstraints c = new GridBagConstraints();
    
    c.gridx = 0;
    c.gridy = 0;
    c.ipady = 20;
    container.add(databaseInfoPanel, c);

    c.gridx = 1;
    c.gridy = 0;
    c.fill = GridBagConstraints.BOTH;
    c.ipady = 0;
    container.add(queryPanel, c);

    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 2;
    container.add(resultPanel, c);

    this.setLayout(new GridLayout());
    this.add(container);
    this.setSize(1600, 960);
    this.setVisible(true);
  }
  
  @Override
  public void executeQuery(String query) {
    List<List<String>> res = queryProvider.executeQuery(query);
    resultPanel.updateResultTable(res);
    resultPanel.updateTableName(null);
  }
  
  @Override
  public void executeQueryOnTable(String tableName) {
    List<List<String>> res = queryProvider.executeQueryOnTable(tableName);
    resultPanel.updateResultTable(res);
    resultPanel.updateTableName(tableName);
  }
  
  @Override
  public void executeDeleteQuery(String tableName, Map<String, String> rowToDelete) {
    queryProvider.executeDeleteQuery(tableName, rowToDelete);
    executeQueryOnTable(tableName);
  }
  
  @Override
  public List<String> getDatabaseInfo() {
    return databaseInfoProducer.getInfo();
  }
  
}
