package bookescape.gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.*;

public class ArbitraryQueryQueryPanel extends JPanel {
  private static final long serialVersionUID = 6553205131744170641L;
  
  private JTextArea queryInput;
  private JButton runQueryBtn;
  private JButton runSelectedQueryBtn;
  private JScrollPane queryInputScrollPane;
  
  private IArbitraryQueryFrame parent;
  
  public ArbitraryQueryQueryPanel(IArbitraryQueryFrame parent) {
    this.parent = parent;
    initLayout();
  }
  
  private void initLayout() {
    this.queryInput = new JTextArea(24, 142);
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
    
    this.queryInput.setLineWrap(true);
    this.queryInput.setWrapStyleWord(true);
    this.queryInputScrollPane = new JScrollPane(queryInput);
    
    JPanel upperContainer = new JPanel();
    upperContainer.setLayout(new GridBagLayout());
    
    JPanel buttonContainer = new JPanel();
    buttonContainer.setLayout(new GridLayout(2, 1));
    this.runQueryBtn.setMargin(new Insets(5, 20, 5, 20));
    this.runSelectedQueryBtn.setMargin(new Insets(5, 20, 5, 20));
    buttonContainer.add(this.runQueryBtn);
    buttonContainer.add(this.runSelectedQueryBtn);
    
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.ipadx = 50;
    upperContainer.add(this.queryInputScrollPane, c);
    
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 0.5;
    c.gridheight = 2;
    upperContainer.add(buttonContainer, c);

    this.add(upperContainer);
  }

}
