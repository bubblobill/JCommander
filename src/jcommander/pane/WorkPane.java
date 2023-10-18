package jcommander.pane;

import jcommander.filesystem.FileHandleBuilder;
import jcommander.filesystem.Handle;
import jcommander.history.HistoryChangeListener;
import jcommander.pane.directorylist.DirectoryListModel;
import jcommander.pane.directorylist.FileCellRenderer;
import jcommander.pane.filetree.FileNode;
import jcommander.pane.filetree.FileTreeModel;
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

    private final JButton parentButton;
    private final JTextField pathField;

    private final FileTreeModel fileSystemModel;
    private final JTree tree;

    private final DirectoryListModel directoryModel;
    private final JList<Handle> list;

    public WorkPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // TODO: decompose this one hell of a god-class in a similar manner how these blocks are structured
        {
            JToolBar pathBar = new JToolBar();
            pathBar.setFloatable(false);

            parentButton = new JButton(getIcon("up.png")); // there could be an optional size parameter
            createParentButton();
            pathBar.add(parentButton);

            pathField = new JTextField(32);
            createPathField();
            pathBar.add(pathField);

            panel.add(pathBar, BorderLayout.NORTH);
        }

        {
            {
                fileSystemModel = new FileTreeModel();
                tree = new JTree(fileSystemModel);
                createFileSystemTree();
            }

            {
                directoryModel = new DirectoryListModel();
                list = new JList<>(directoryModel);
                createDirectoryList();
            }

            JSplitPane views = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tree), new JScrollPane(list));

            panel.add(views, BorderLayout.CENTER);
        }

        add(panel);

        wd.addChangeListener(e -> refresh());
        wd.resetToRoot();

        setLayout(new FlowLayout());
        setPreferredSize(panel.getPreferredSize());
    }

    private void createParentButton() {
        parentButton.addActionListener(e -> wd.selectParent());
    }

    private void createPathField() {
        pathField.addActionListener(e -> {
            String suggestedPath = pathField.getText();
            if (suggestedPath.isBlank()) {
                wd.resetToRoot();
            } else {
                File validatorFile = new File(suggestedPath);
                if (validatorFile.exists() && validatorFile.isDirectory()) {
                    wd.setTo(new FileHandleBuilder(validatorFile).toFileHandle());
                } else {
                    refreshTextBox();
                }
            }
        });
        pathField.setFont(new Font("Sans Serif", Font.PLAIN, 20));
    }

    private void createFileSystemTree() {
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setEditable(true);
        tree.setScrollsOnExpand(true);
        tree.setExpandsSelectedPaths(true);
        tree.addTreeSelectionListener(e -> {
            TreeNode node = (TreeNode) e.getPath().getLastPathComponent();
            if (node == fileSystemModel.getRoot()) {
                wd.resetToRoot();
            } else {
                FileNode fileNode = (FileNode) node;
                wd.setTo(fileNode.getFile()); // tries to set, and does nothing if handle is not a directory
            }
        });
    }

    private void createDirectoryList() {
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

                    Handle file = directoryModel.getElementAt(index);
                    wd.setTo(file);
                }
            }
        });
    }

    private static String[] pseudoPathFromString(String absolutePath) {
        String[] path = absolutePath.split(Pattern.quote(File.separator));

        if (path.length > 0) {
            // On Windows, this is to add a backslash to the drive's label (e.g.: "C:\").
            // On Linux, we can pretend that Linux's root is [empty string] + backslash, so together they form "/".
            // Thus, this method works fine on all platforms.
            path[0] += File.separator;
        }

        return path;
    }

    public void refresh() {
        refreshParentFolderButton();
        refreshTextBox();
        refreshTree();
        refreshList();
    }

    private void refreshParentFolderButton() {
        parentButton.setEnabled(!wd.isRoot());
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
                FileNode child = (FileNode) leaf.getChildAt(idx);
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

    public void addHistoryChangeListener(HistoryChangeListener<Handle> l) {
        wd.addHistoryChangeListener(l);
    }

    public void removeHistoryChangeListener(HistoryChangeListener<Handle> l) {
        wd.removeHistoryChangeListener(l);
    }
}
