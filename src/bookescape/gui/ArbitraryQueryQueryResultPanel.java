package bookescape.gui;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ArbitraryQueryQueryResultPanel extends JPanel {
  private static final long serialVersionUID = 5129618759196085800L;

  private JTable resultTable;
  private DefaultTableModel dtm;
  private JScrollPane tableSP;
  private JPopupMenu rightClickPopup;
  private IArbitraryQueryFrame parent;
  
  private String tableName;

  public ArbitraryQueryQueryResultPanel(IArbitraryQueryFrame parent) {
    this.parent = parent;
    initLayout();
  }

  private void initLayout() {
    this.resultTable = new JTable();
    this.tableSP = new JScrollPane(resultTable);
    this.dtm = new DefaultTableModel(0, 0);

    tableSP.setPreferredSize(new Dimension(1500, 400));

    rightClickPopup = new JPopupMenu();
    JMenuItem delete = new JMenuItem("Elimina");
    rightClickPopup.add(delete);

    delete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        int columnCount = resultTable.getColumnCount();

        Map<String, String> colsMap = new HashMap<>();

        for (int i = 0; i < columnCount; i++) {
          colsMap.put(resultTable.getColumnName(i), (String) resultTable.getValueAt(resultTable.getSelectedRow(), i));
        }
        parent.executeDeleteQuery(tableName, colsMap);
      }
    });

    resultTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        int r = resultTable.rowAtPoint(e.getPoint());
        if (r >= 0 && r < resultTable.getRowCount()) {
          resultTable.setRowSelectionInterval(r, r);
        } else {
          resultTable.clearSelection();
        }

        int rowindex = resultTable.getSelectedRow();
        if (rowindex < 0)
          return;
        if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
          delete.setEnabled(tableName != null);
          rightClickPopup.show(e.getComponent(), e.getX(), e.getY());
        }
      }
    });

    this.add(tableSP);
  }

  public void updateResultTable(List<List<String>> newData) {
    dtm.setRowCount(0);
    dtm.setColumnIdentifiers(new Vector<String>(newData.get(0)));
    resultTable.setModel(dtm);

    for (int i = 1; i <= newData.size() - 1; i++) {
      dtm.addRow(new Vector<String>(newData.get(i)));
    }
  }
  
  public void updateTableName(String tableName) {
    this.tableName = tableName;
  }

}
