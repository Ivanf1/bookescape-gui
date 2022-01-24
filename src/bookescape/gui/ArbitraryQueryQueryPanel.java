package bookescape.gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.*;

public class ArbitraryQueryQueryPanel extends JPanel {
  private static final long serialVersionUID = 6553205131744170641L;
  
  private JTextArea queryInput;
  private JButton runQueryBtn;
  private JButton runSelectedQueryBtn;
  
  private ArbitraryQueryFrame parent;
  
  public ArbitraryQueryQueryPanel(ArbitraryQueryFrame parent) {
    this.parent = parent;
    initLayout();
  }
  
  private void initLayout() {
    this.queryInput = new JTextArea(20, 100);
    this.runQueryBtn = new JButton("Esegui");
    this.runSelectedQueryBtn = new JButton("Esegui selezione");
    
    this.runQueryBtn.addActionListener((e) -> {
      parent.executeQuery(queryInput.getText());
    });

    this.runSelectedQueryBtn.addActionListener((e) -> {
      String selectedText = queryInput.getSelectedText();
      if (selectedText != null) {
        parent.executeQuery(selectedText);
      }
    });
    
    JPanel upperContainer = new JPanel();
    upperContainer.add(this.queryInput);
    upperContainer.add(this.runQueryBtn);
    upperContainer.add(this.runSelectedQueryBtn);
    
    this.setLayout(new BorderLayout());
    this.add(upperContainer, BorderLayout.NORTH);
  }

}
