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
    this.queryInput = new JTextArea(24, 165);
    this.runQueryBtn = new JButton("Esegui");
    this.runSelectedQueryBtn = new JButton("Esegui selezione");
    
    this.runQueryBtn.addActionListener((e) -> {
      String queryString = "";
      // skip comment lines
      for (String line : queryInput.getText().split("\\n")) {
        if (!line.substring(0, 2).equals("--")) {
          queryString += line;
        }
      }
      parent.executeQuery(queryString);
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
    buttonContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
    this.runQueryBtn.setMargin(new Insets(5, 20, 5, 20));
    this.runSelectedQueryBtn.setMargin(new Insets(5, 20, 5, 20));
    buttonContainer.add(this.runQueryBtn);
    buttonContainer.add(this.runSelectedQueryBtn);
    
    this.setLayout(new BorderLayout());
    
    upperContainer.add(this.queryInputScrollPane);
    
    upperContainer.add(buttonContainer);

    this.add(upperContainer, BorderLayout.CENTER);
    this.add(buttonContainer, BorderLayout.SOUTH);
  }

}
