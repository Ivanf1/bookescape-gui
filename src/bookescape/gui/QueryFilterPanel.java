package bookescape.gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.BorderLayout;

public class QueryFilterPanel extends JPanel {
  private static final long serialVersionUID = 8955887778063974040L;
  
  private JButton applyFilterBtn;
  private JPanel selectInputFiltersContainer;
  JLabel titleLabel = new JLabel("Filtra per");

  private IFilterQueryProvider queryProvider;
  
  private String tableName;
  private Set<String> selectedColumns = new HashSet<>();
  private Map<String, String> conditions = new HashMap<>();
  
  public QueryFilterPanel(IFilterQueryProvider queryProvider) {
    this.queryProvider = queryProvider;
    initLayout();
  }
  
  private void initLayout() {
    this.applyFilterBtn = new JButton("Applica filtri");
    this.applyFilterBtn.setMargin(new Insets(5, 20, 5, 20));
    this.selectInputFiltersContainer = new JPanel();
    this.selectInputFiltersContainer.setLayout(new BoxLayout(selectInputFiltersContainer, BoxLayout.PAGE_AXIS));
    this.selectInputFiltersContainer.setMaximumSize(new Dimension(10, 400));
    this.selectInputFiltersContainer.setBorder(new EmptyBorder(10, 0, 0, 0));
    
    this.applyFilterBtn.setEnabled(false);
    this.applyFilterBtn.addActionListener((e) -> {
      if (this.selectedColumns.isEmpty()) return;
      this.queryProvider.executeFilterQuery(tableName, selectedColumns, conditions);
    });
    
    JPanel container = new JPanel();
    container.setLayout(new BorderLayout());
    container.setBorder(new EmptyBorder(5, 10, 5, 10));
    
    container.add(titleLabel, BorderLayout.PAGE_START);
    container.add(selectInputFiltersContainer, BorderLayout.CENTER);
    container.add(applyFilterBtn, BorderLayout.PAGE_END);
    
    this.setLayout(new BorderLayout());
    
    this.add(container);
  }
  
  public void updateFilters(List<String> columns) {
    this.selectedColumns.clear();
    this.conditions.clear();
    this.selectInputFiltersContainer.removeAll();
    
    for (String column : columns) {
      selectedColumns.add(column);
      JCheckBox check = new JCheckBox(column, true);
      JTextField colFilter = new JTextField();
      colFilter.putClientProperty("colName", column);
      colFilter.setMaximumSize(new Dimension(200, 25));

      check.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          JCheckBox source = (JCheckBox) e.getItemSelectable();
          if (source.isSelected()) {
            selectedColumns.add(source.getText());
          } else {
            selectedColumns.remove(source.getText());
          }
          applyFilterBtn.setEnabled(true);
        }
      });
      
      colFilter.getDocument().addDocumentListener(new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
          update(e);
        }
        public void removeUpdate(DocumentEvent e) {
          update(e);
        }
        public void insertUpdate(DocumentEvent e) {
          update(e);
        }

        public void update(DocumentEvent e) {
          String text = colFilter.getText();
          if (text.isBlank()) {
            conditions.remove((String) colFilter.getClientProperty("colName"));
          } else {
            conditions.put((String) colFilter.getClientProperty("colName"), text);
          }
          applyFilterBtn.setEnabled(true);
        }
      }); 

      this.selectInputFiltersContainer.add(check);
      this.selectInputFiltersContainer.add(colFilter);
    }

    this.selectInputFiltersContainer.revalidate();
    this.selectInputFiltersContainer.repaint();
  }
  
  public void updateTableName(String tableName) {
    this.tableName = tableName;
    if (tableName == null) {
      this.applyFilterBtn.setEnabled(false);
      clearFilterContainer();
    }
  }
  
  private void clearFilterContainer() {
    this.selectedColumns.clear();
    this.conditions.clear();
    this.selectInputFiltersContainer.removeAll();
    
    this.selectInputFiltersContainer.revalidate();
    this.selectInputFiltersContainer.repaint();
  }


}
