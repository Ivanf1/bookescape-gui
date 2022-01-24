package bookescape.gui;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class DatabaseInfoPanel extends JPanel {
  private static final long serialVersionUID = 8992421403854713193L;

  private JTree databaseTree;
  private ArbitraryQueryFrame parent;

  public DatabaseInfoPanel(ArbitraryQueryFrame parent) {
    this.parent = parent;
    initLayout();
  }

  private void initLayout() {
    List<String> databaseInfo = parent.getDatabaseInfo();

    // create the root node
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(databaseInfo.get(0));

    // create the child nodes
    for (int i = 1; i < databaseInfo.size(); i++) {
      DefaultMutableTreeNode t = new DefaultMutableTreeNode(databaseInfo.get(i));
      root.add(t);
    }

    // create the tree by passing in the root node
    databaseTree = new JTree(root);
    
    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

    ImageIcon rootIcon = createImageIcon("images/database.png");
    Image rootImage = rootIcon.getImage();
    Image newRootImg = rootImage.getScaledInstance(12, 12,  java.awt.Image.SCALE_SMOOTH);
    rootIcon = new ImageIcon(newRootImg);
    renderer.setOpenIcon(rootIcon);

    ImageIcon leafIcon = createImageIcon("images/table.png");
    Image leafImage = leafIcon.getImage(); 
    Image newLeafImg = leafImage.getScaledInstance(12, 12,  java.awt.Image.SCALE_SMOOTH);
    leafIcon = new ImageIcon(newLeafImg);
    renderer.setLeafIcon(leafIcon);

    databaseTree.setCellRenderer(renderer);
    databaseTree.setRowHeight(20);

    this.add(databaseTree);
  }

  protected ImageIcon createImageIcon(String path) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL);
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }

}
