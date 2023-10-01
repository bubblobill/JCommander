import filetree.FileTreeModel;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.dnd.DropTarget;

public class WorkPane extends JComponent {

    public WorkPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField pathField = new JTextField(32);
        panel.add(pathField, BorderLayout.NORTH);

        TreeModel model = new FileTreeModel();
        JTree tree = new JTree();
        tree.setModel(model);
        tree.setEditable(true);
        panel.add(new JScrollPane(tree), BorderLayout.CENTER);

        add(panel);

        setLayout(new FlowLayout());
        setPreferredSize(panel.getPreferredSize());
    }
}
