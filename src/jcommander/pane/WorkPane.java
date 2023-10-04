package jcommander.pane;

import jcommander.pane.directorylist.DirectoryListModel;
import jcommander.pane.directorylist.FileCellRenderer;
import jcommander.pane.filetree.FileTreeModel;
import jcommander.pane.filetree.FileTreeNode;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class WorkPane extends JComponent {

    private File workingDirectory;

    private final Stack<String> undoHistory = new Stack<>();
    private final Stack<String> redoHistory = new Stack<>();

    private final JTextField pathField;

    private final FileTreeModel fileSystemModel;
    private final JTree tree;

    private final DirectoryListModel directoryModel;
    private final JList<File> list;

    public WorkPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        pathField = new JTextField(32);
        pathField.addActionListener(e -> {
            File validatorFile = new File(pathField.getText());
            if (validatorFile.exists() && validatorFile.isDirectory()) {
                changeWorkingDirectory(validatorFile);
            } else {
                refreshTextBox();
            }
        });
        panel.add(pathField, BorderLayout.NORTH);

        fileSystemModel = new FileTreeModel();
        tree = new JTree();
        tree.setModel(fileSystemModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setEditable(true);
        tree.setScrollsOnExpand(true);
        tree.setExpandsSelectedPaths(true);
        tree.addTreeSelectionListener(e -> {
            FileTreeNode node = (FileTreeNode) e.getPath().getLastPathComponent();
            if (!node.isLeaf()) {
                changeWorkingDirectory(node.getFile());
            }
        });

        directoryModel = new DirectoryListModel();
        list = new JList<>(directoryModel);
        list.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setCellRenderer(new FileCellRenderer());
        list.setDragEnabled(true);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
                    if (index == -1) {
                        return;
                    }

                    File file = directoryModel.getElementAt(index);
                    if (file.isDirectory()) {
                        changeWorkingDirectory(file);
                    }
                }
            }
        });

        JSplitPane views = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tree), new JScrollPane(list));

        panel.add(views, BorderLayout.CENTER);

        add(panel);

        setLayout(new FlowLayout());
        setPreferredSize(panel.getPreferredSize());
    }

    public void changeWorkingDirectory(File workingDirectory) {
        redoHistory.clear();
        undoHistory.push(this.workingDirectory.getAbsolutePath());
        setWorkingDirectory(workingDirectory);
    }

    private void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
        refresh();
    }

    public void refresh() {
        refreshTextBox();
        refreshTree();
        refreshList();
    }

    private void refreshTextBox() {
        pathField.setText(workingDirectory.getAbsolutePath());
    }

    private void refreshTree() {
        List<TreeNode> path = new ArrayList<>();
        TreeNode leaf = (TreeNode) fileSystemModel.getRoot();
        for (String label : pseudoPathFromString(workingDirectory.getAbsolutePath())) {
            for (int idx = 0; idx < leaf.getChildCount(); idx++) {
                FileTreeNode child = (FileTreeNode) leaf.getChildAt(idx);
                if (child.toString().equals(label)) { // TODO: Law of Demeter
                    path.add(child);
                    break;
                }
            }
        }
        TreePath treePath = new TreePath(path.toArray());
        fileSystemModel.refreshPath(treePath);
        tree.expandPath(treePath); // this does not work as of now
    }

    private static String[] pseudoPathFromString(String absolutePath) {
        String[] path = absolutePath.split(Pattern.quote(File.separator));

        if (path.length > 0) {
            // On Windows, this is to add a backslash to the drive's label (e.g.: "C:\").
            // On Linux, we can pretend that Linux's root is [empty string] + backslash, so together they form "/".
            // Thus, fortunately this method works fine on all platforms.
            path[0] += File.separator;
        }

        return path;
    }

    private void refreshList() {
        directoryModel.listDirectory(workingDirectory);
    }

    public void selectPrevious() {
        String url = undoHistory.pop();
        redoHistory.push(url);
        setWorkingDirectory(new File(url));
    }

    public void selectNext() {
        String url = redoHistory.pop();
        undoHistory.push(url);
        setWorkingDirectory(new File(url));
    }
}
