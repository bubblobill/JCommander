package jcommander.pane;

import jcommander.pane.directorylist.DirectoryListModel;
import jcommander.pane.directorylist.FileCellRenderer;
import jcommander.pane.filetree.FileTreeModel;
import jcommander.pane.filetree.FileTreeNode;
import jcommander.pane.model.WorkingDirectory;

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
import java.util.regex.Pattern;

import static jcommander.ResourceFactory.getIcon;

public class WorkPane extends JComponent {

    private final WorkingDirectory wd = new WorkingDirectory();

    private final JTextField pathField;

    private final FileTreeModel fileSystemModel;
    private final JTree tree;

    private final DirectoryListModel directoryModel;
    private final JList<File> list;

    public WorkPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JToolBar pathBar = new JToolBar();
        pathBar.setFloatable(false);

        JButton parentFolderButton = new JButton(getIcon("up.png")); // there could be an optional size parameter
        parentFolderButton.addActionListener(e -> wd.selectParent());
        pathBar.add(parentFolderButton);

        pathField = new JTextField(32);
        pathField.addActionListener(e -> {
            String suggestedPath = pathField.getText();
            if (suggestedPath.isBlank()) {
                wd.set(null);
            } else {
                File validatorFile = new File(suggestedPath);
                if (validatorFile.exists() && validatorFile.isDirectory()) {
                    wd.set(validatorFile);
                } else {
                    refreshTextBox();
                }
            }
        });
        pathField.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        pathBar.add(pathField);

        panel.add(pathBar, BorderLayout.NORTH);

        fileSystemModel = new FileTreeModel();
        tree = new JTree();
        tree.setModel(fileSystemModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setEditable(true);
        tree.setScrollsOnExpand(true);
        tree.setExpandsSelectedPaths(true);
        tree.addTreeSelectionListener(e -> {
            TreeNode node = (TreeNode) e.getPath().getLastPathComponent();
            if (node == fileSystemModel.getRoot()) {
                wd.set(null);
            } else if (!node.isLeaf()) {
                FileTreeNode fileTreeNode = (FileTreeNode) node;
                wd.set(fileTreeNode.getFile());
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
                        wd.set(file);
                    }
                }
            }
        });

        JSplitPane views = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tree), new JScrollPane(list));

        panel.add(views, BorderLayout.CENTER);

        add(panel);

        wd.addDirectoryChangeListener(e -> refresh());
        wd.set(null);

        setLayout(new FlowLayout());
        setPreferredSize(panel.getPreferredSize());
    }

    public void refresh() {
        refreshTextBox();
        refreshTree();
        refreshList();
    }

    private void refreshTextBox() {
        pathField.setText(wd.getAbsolutePath());
    }

    private void refreshTree() {
        if (wd.isRoot()) {
            return;
        }

        TreeNode leaf = (TreeNode) fileSystemModel.getRoot();
        List<TreeNode> path = new ArrayList<>();
        for (String label : pseudoPathFromString(wd.getAbsolutePath())) {
            for (int idx = 0; idx < leaf.getChildCount(); idx++) {
                FileTreeNode child = (FileTreeNode) leaf.getChildAt(idx);
                if (child.toString().equals(label)) { // TODO: Law of Demeter
                    path.add(child);
                    break;
                }
            }
        }

        TreePath treePath = new TreePath(path.toArray());
        //fileSystemModel.refreshPath(treePath);
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
        list.clearSelection();
        directoryModel.listDirectory(wd.list());
    }

    public void selectPrevious() {
        wd.selectPrevious();
    }

    public void selectNext() {
        wd.selectNext();
    }
}
