package bookescape.gui;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class QueryResultPanel extends JPanel {
  private static final long serialVersionUID = 5129618759196085800L;

  private JTable resultTable;
  private DefaultTableModel dtm;
  private JScrollPane tableSP;
  private JPopupMenu rightClickPopup;
  private JButton insertBtn;
  private JButton cancelBtn;
  private JButton confirmBtn;
  
  private IArbitraryQueryProvider queryProvider;

  private String tableName;
  private static enum EditingState {
    UPDATE,
    INSERT
  }
  private EditingState editingState = null;
  
  // keeps track of all the changed columns for every changed row
  // key: row index, value: indexes of changed columns
  private Map<Integer, HashSet<String>> changedRowsMap = new HashMap<>();

  public QueryResultPanel(IArbitraryQueryProvider queryProvider) {
    this.queryProvider = queryProvider;
    initLayout();
  }

  private void initLayout() {
    this.resultTable = new JTable();
    this.tableSP = new JScrollPane(resultTable);
    this.dtm = new DefaultTableModel(0, 0);
    this.resultTable.setModel(dtm);

    this.insertBtn = new JButton("Nuovo");
    this.cancelBtn = new JButton("Annulla");
    this.confirmBtn = new JButton("Conferma");
    this.insertBtn.setMargin(new Insets(5, 20, 5, 20));
    this.cancelBtn.setMargin(new Insets(5, 20, 5, 20));
    this.confirmBtn.setMargin(new Insets(5, 20, 5, 20));
    JPanel buttonContainer = new JPanel();
    buttonContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
    buttonContainer.add(insertBtn);
    buttonContainer.add(cancelBtn);
    buttonContainer.add(confirmBtn);

    tableSP.setPreferredSize(new Dimension(1200, 400));
    tableSP.setMaximumSize(new Dimension(1200, 400));
    tableSP.setMinimumSize(new Dimension(1200, 400));

    rightClickPopup = new JPopupMenu();
    JMenuItem delete = new JMenuItem("Elimina");
    rightClickPopup.add(delete);

    insertBtn.setEnabled(false);
    cancelBtn.setEnabled(false);
    confirmBtn.setEnabled(false);

    this.insertBtn.addActionListener((e) -> {
      editingState = EditingState.INSERT;
      insertBtn.setEnabled(false);
      cancelBtn.setEnabled(true);
      confirmBtn.setEnabled(true);

      DefaultTableModel dtm = (DefaultTableModel) resultTable.getModel();

      dtm.addRow((Object[]) null);
      
      resultTable.setEditingColumn(0);
      resultTable.setColumnSelectionInterval(0, 0);
      resultTable.setRowSelectionInterval(dtm.getRowCount() - 1 , dtm.getRowCount() - 1);
      resultTable.requestFocus();
    });
    
    this.cancelBtn.addActionListener((e) -> {
      if (editingState == EditingState.INSERT) {
        dtm.removeRow(dtm.getRowCount() - 1);
      }
      confirmBtn.setEnabled(false);
      cancelBtn.setEnabled(false);
      editingState = null;
      // reset table
      queryProvider.executeQueryOnTable(tableName);
      // clean changes hashset
      changedRowsMap.clear();
    });
    
    this.confirmBtn.addActionListener((e) -> {
      confirmBtn.setEnabled(false);
      cancelBtn.setEnabled(false);
      insertBtn.setEnabled(false);
      
      if (editingState == EditingState.INSERT) {
        int columnCount = resultTable.getColumnCount();
        int row = resultTable.getRowCount() - 1;

        Map<String, String> colsMap = new HashMap<>();
        for (int i = 0; i < columnCount; i++) {
          colsMap.put(resultTable.getColumnName(i), (String) resultTable.getValueAt(row, i));
        }
        editingState = null;

        queryProvider.executeInsertQuery(tableName, colsMap);
        // refresh table
        queryProvider.executeQueryOnTable(tableName);

      } else if (editingState == EditingState.UPDATE) {
        editingState = null;
        // execute update query for every changed row
        int columnCount = resultTable.getColumnCount();
        for (var entry : changedRowsMap.entrySet()) {
          Map<String, String> colsMap = new HashMap<>();

          for (int i = 0; i < columnCount; i++) {
            colsMap.put(resultTable.getColumnName(i), (String) resultTable.getValueAt(entry.getKey(), i));
          }
          
          queryProvider.executeUpdateQuery(tableName, colsMap, entry.getValue());
        }
        
        // refresh table
        queryProvider.executeQueryOnTable(tableName);
      }

    });

    // execute delete query
    delete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        int columnCount = resultTable.getColumnCount();

        Map<String, String> colsMap = new HashMap<>();

        for (int i = 0; i < columnCount; i++) {
          colsMap.put(resultTable.getColumnName(i), (String) resultTable.getValueAt(resultTable.getSelectedRow(), i));
        }
        queryProvider.executeDeleteQuery(tableName, colsMap);
      }
    });

    // listener to toggle popup on right click
    // on table's row
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

    // listener to handle change of values in rows
    // from user input
    resultTable.getModel().addTableModelListener(new TableModelListener() {
      public void tableChanged(TableModelEvent evt) {
        if (editingState == EditingState.INSERT) {
          // if current state is insert and the row that changed
          // is not the last (new row) then delete last row
          if (evt.getFirstRow() != dtm.getRowCount() - 1) {
            editingState = EditingState.UPDATE;
            dtm.removeRow(dtm.getRowCount() - 1);
          }
        }
        if (editingState == EditingState.UPDATE) {
          int colIdx = evt.getColumn();
          // if all columns changed ignore the event
          if (colIdx == TableModelEvent.ALL_COLUMNS) return;
          cancelBtn.setEnabled(true);
          confirmBtn.setEnabled(true);

          // add edited row with changed columns
          HashSet<String> columnsForEditedRow = changedRowsMap.get(evt.getFirstRow());
          // if first time editing this row, initialize Set
          if (columnsForEditedRow == null) {
            columnsForEditedRow = new HashSet<String>();
          }
          // add current editing column to set
          columnsForEditedRow.add(resultTable.getColumnName(evt.getColumn()));
          // add set to corresponding row in map
          changedRowsMap.put(evt.getFirstRow(), columnsForEditedRow);
        }
      }
    });

    this.setLayout(new BorderLayout());

    this.add(tableSP, BorderLayout.CENTER);
    this.add(buttonContainer, BorderLayout.SOUTH);
  }

  public void updateResultTable(List<List<String>> newData) {
    DefaultTableModel dtm = (DefaultTableModel) resultTable.getModel();
    // clear table
    dtm.setRowCount(0);
    // set table header
    dtm.setColumnIdentifiers(new Vector<String>(newData.get(0)));

    // add row by row
    for (int i = 1; i <= newData.size() - 1; i++) {
      dtm.addRow(new Vector<String>(newData.get(i)));
    }
  }

  public void updateTableName(String tableName) {
    this.tableName = tableName;
    this.insertBtn.setEnabled(tableName != null);
  }

}
