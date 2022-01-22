package bookescape.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;
import java.util.Vector;

public class ArbitraryQueryQueryResultPanel extends JPanel {
  private static final long serialVersionUID = 5129618759196085800L;
  
  private JTable resultTable;
  private DefaultTableModel dtm;
  JScrollPane tableSP;
  
  public ArbitraryQueryQueryResultPanel() {
    initLayout();
  }
  
  private void initLayout() {
    this.resultTable = new JTable();
    this.tableSP = new JScrollPane(resultTable);
    this.dtm = new DefaultTableModel(0, 0);

    tableSP.setPreferredSize(new Dimension(1500, 400));
    this.add(tableSP);
  }
  
  public void updateResultTable(List<List<String>> newData) {
    dtm.setRowCount(0);
    dtm.setColumnIdentifiers(new Vector<String>(newData.get(0)));
    resultTable.setModel(dtm);
    
    for (int i = 1; i <= newData.size()-1; i++) {
      dtm.addRow(new Vector<String>(newData.get(i)));
    }
  }
  
}
