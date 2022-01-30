package bookescape.gui;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import java.awt.*;

import bookescape.persistence.CustomQuery;

public class CustomQueryPanel extends JPanel {
  private static final long serialVersionUID = -118480577143220801L;

  private JTree databaseTree;
  private ICustomQueryProvider queryProvider;
  private List<CustomQuery> customQueries;

  public CustomQueryPanel(ICustomQueryProvider queryProvider) {
    this.queryProvider = queryProvider;
    initLayout();
  }

  private void initLayout() {
    this.customQueries = queryProvider.getCustomQuery();
    
    // create the root node
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Queries");

    // create the child nodes
    for (int i = 0; i < customQueries.size(); i++) {
      DefaultMutableTreeNode t = new DefaultMutableTreeNode(i + 1);
      root.add(t);
    }

    // create the tree by passing in the root node
    databaseTree = new JTree(root);

    databaseTree.addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) databaseTree.getLastSelectedPathComponent();

        /* if nothing is selected */
        if (node == null)
          return;

        /* retrieve the node that was selected */
        Integer queryIndex = (Integer) node.getUserObject();
        /* React to the node selection. */
        queryProvider.executeCustomQuery(customQueries.get(queryIndex - 1).prettyPrint());
      }
    });

    databaseTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

    ImageIcon rootIcon = createImageIcon("images/flash.png");
    Image rootImage = rootIcon.getImage();
    Image newRootImg = rootImage.getScaledInstance(12, 12, java.awt.Image.SCALE_SMOOTH);
    rootIcon = new ImageIcon(newRootImg);
    renderer.setOpenIcon(rootIcon);

    ImageIcon leafIcon = createImageIcon("images/query.png");
    Image leafImage = leafIcon.getImage();
    Image newLeafImg = leafImage.getScaledInstance(12, 12, java.awt.Image.SCALE_SMOOTH);
    leafIcon = new ImageIcon(newLeafImg);
    renderer.setLeafIcon(leafIcon);

    databaseTree.setCellRenderer(renderer);
    databaseTree.setRowHeight(20);
    
    JPanel treeContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
    treeContainer.add(databaseTree);

    this.add(treeContainer);
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
