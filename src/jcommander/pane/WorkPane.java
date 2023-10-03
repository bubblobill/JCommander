import directorylist.DirectoryListModel;
import filetree.FileTreeModel;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.util.Stack;

public class WorkPane extends JComponent {

    private final Stack<TreeSelectionModel> undoHistory = new Stack<>();
    private final Stack<TreeSelectionModel> redoHistory = new Stack<>();

    private final JTextField pathField;

    private final FileTreeModel fileSystemModel;
    private final JTree tree;

    private final DirectoryListModel directoryModel;
    private final JList<File> list;

    public WorkPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        pathField = new JTextField(32);
        panel.add(pathField, BorderLayout.NORTH);

        fileSystemModel = new FileTreeModel();
        tree = new JTree();
        tree.setModel(fileSystemModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setEditable(true);
        tree.setScrollsOnExpand(true);

        directoryModel = new DirectoryListModel(File.listRoots()[0]);
        list = new JList<>(directoryModel);
//        list.setCellRenderer((list, file, index, isSelected, cellHasFocus) -> {
//            JPanel cell = new JPanel();
//            JLabel text = new JLabel(file.getName());
//            text.setHorizontalAlignment(SwingConstants.LEFT);
//            cell.add(text);
//            cell.setBackground(Color.LIGHT_GRAY);
//            return cell;
//        });
        list.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setDragEnabled(true);

        JSplitPane views = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tree), new JScrollPane(list));

        panel.add(views, BorderLayout.CENTER);

        add(panel);

        setLayout(new FlowLayout());
        setPreferredSize(panel.getPreferredSize());
    }

    public void refreshTree() {
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            return;
        }
        fileSystemModel.refreshPath(path);
    }

    public void selectPrevious() {
        TreeSelectionModel selection = undoHistory.pop();
        redoHistory.push(selection);
        // TODO: setup selection in tree
    }

    public void selectNext() {
        TreeSelectionModel selection = redoHistory.pop();
        undoHistory.push(selection);
        // TODO: setup selection in tree
    }
}
