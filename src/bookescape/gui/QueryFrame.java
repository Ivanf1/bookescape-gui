package bookescape.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bookescape.persistence.CustomQuery;
import bookescape.persistence.IDatabaseInfoProducer;
import bookescape.persistence.IQueryProvider;
import bookescape.utils.ICustomQueryProducer;

import java.awt.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class QueryFrame extends JFrame implements IArbitraryQueryProvider, ICustomQueryProvider, IFilterQueryProvider {
  private static final long serialVersionUID = -5148352205350265400L;
  
  private QueryInputPanel queryPanel;
  private QueryResultPanel resultPanel;
  private DatabaseInfoPanel databaseInfoPanel;
  private CustomQueryPanel customQueryPanel;
  private QueryFilterPanel queryFilterPanel;
  private IQueryProvider queryProvider;
  private IDatabaseInfoProducer databaseInfoProducer;
  private ICustomQueryProducer customQueryProducer;
  private JPanel container;

  public QueryFrame(IQueryProvider queryProvider, IDatabaseInfoProducer databaseInfoProducer, ICustomQueryProducer customQueryProducer) {
    this.queryProvider = queryProvider;
    this.databaseInfoProducer = databaseInfoProducer;
    this.customQueryProducer = customQueryProducer;
    this.resultPanel = new QueryResultPanel(this);
    this.queryPanel = new QueryInputPanel(this);
    this.databaseInfoPanel = new DatabaseInfoPanel(this);
    this.customQueryPanel = new CustomQueryPanel(this);
    this.queryFilterPanel = new QueryFilterPanel(this);
    
    initLayout();
  }
  
  private void initLayout() {
    this.container = new JPanel();
    container.setLayout(new GridBagLayout());
    
    GridBagConstraints c = new GridBagConstraints();
    
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.gridy = 0;
    c.ipady = 20;
    container.add(databaseInfoPanel, c);

    c.gridx = 2;
    c.gridy = 0;
    c.ipady = 20;
    container.add(customQueryPanel, c);

    c.gridx = 1;
    c.gridy = 0;
    c.ipady = 0;
    container.add(queryPanel, c);

    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 2;
    container.add(resultPanel, c);

    c.gridx = 2;
    c.gridwidth = 1;
    container.add(queryFilterPanel, c);

    this.setLayout(new GridLayout());
    this.add(container);
    this.setSize(1600, 980);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }
  
  @Override
  public void executeQuery(String query) {
    List<List<String>> res = queryProvider.executeQuery(query);
    resultPanel.updateTableName(null);
    queryFilterPanel.updateTableName(null);
    if (res != null) {
      resultPanel.updateResultTable(res);
    }
  }
  
  @Override
  public void executeQueryOnTable(String tableName) {
    List<List<String>> res = queryProvider.executeQueryOnTable(tableName);
    resultPanel.updateResultTable(res);
    resultPanel.updateTableName(tableName);
    queryFilterPanel.updateFilters(res.get(0));
    queryFilterPanel.updateTableName(tableName);
  }
  
  @Override
  public void executeDeleteQuery(String tableName, Map<String, String> rowToDelete) {
    queryProvider.executeDeleteQuery(tableName, rowToDelete);
    executeQueryOnTable(tableName);
  }
  
  @Override
  public void executeUpdateQuery(String tableName, Map<String, String> rowToUpdate, Set<String> columnsToUpdate) {
    queryProvider.executeUpdateQuery(tableName, rowToUpdate, columnsToUpdate);
  }
  
  @Override
  public List<String> getDatabaseInfo() {
    return databaseInfoProducer.getInfo();
  }

  @Override
  public List<CustomQuery> getCustomQuery() {
    return customQueryProducer.getCustomQueries();
  }
  
  @Override
  public void executeCustomQuery(String query) {
    queryPanel.setQueryInput(query);
    queryFilterPanel.updateTableName(null);
  }
  
  @Override
  public void executeFilterQuery(String tableName, Set<String> selectedColumns, Map<String, String> conditions) {
    List<List<String>> res = queryProvider.executeFilterQuery(tableName, selectedColumns, conditions);
    resultPanel.updateResultTable(res);
    resultPanel.updateTableName(tableName);
    queryFilterPanel.updateTableName(tableName);
  }
  
}
