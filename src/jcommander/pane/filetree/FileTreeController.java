package jcommander.pane.filetree;

import jcommander.pane.SelectionController;
import jcommander.pane.model.WorkingDirectory;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileTreeController implements SelectionController {

    private final WorkingDirectory wd;
    private final FileTreeModel fileSystemModel;
    private final JTree treeView;

    public FileTreeController(WorkingDirectory wd) {
        this.wd = wd;

        fileSystemModel = new FileTreeModel();

        treeView = new JTree(fileSystemModel);
        treeView.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeView.setEditable(true);
        treeView.addTreeSelectionListener(e -> {
            TreeNode node = (TreeNode) e.getPath().getLastPathComponent();
            if (node == fileSystemModel.getRoot()) {
                wd.resetToRoot();
            } else {
                FileNode fileNode = (FileNode) node;
                wd.setTo(fileNode.toFile()); // tries to set, and does nothing if handle is not a directory
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

    @Override
    public JComponent component() {
        return treeView;
    }

    @Override
    public void refresh() {
        if (wd.isRoot()) {
            return;
        }

        TreeNode leaf = (TreeNode) fileSystemModel.getRoot();
        List<TreeNode> path = new ArrayList<>();
        for (String label : pseudoPathFromString(wd.getAbsolutePath())) {
            for (int idx = 0; idx < leaf.getChildCount(); idx++) {
                FileNode child = (FileNode) leaf.getChildAt(idx);
                if (child.toString().equals(label)) {
                    path.add(child);
                    break;
                }
            }
        }

        TreePath treePath = new TreePath(path.toArray());
        //fileSystemModel.refreshPath(treePath);
        treeView.expandPath(treePath); // this does not work as of now
    }

    @Override
    public File[] getSelectedFiles() {
        if (wd.isRoot()) {
            return new File[0];
        }

        // Since the selection mode prohibits the selection of multiple nodes, it is sufficient to query for one
        // selected node only.

        // If it returns null, nothing is selected.
        TreePath selectionPath = treeView.getSelectionPath();
        if (selectionPath == null) {
            return new File[0];
        }

        // Extract the underlying file otherwise.
        FileNode node = (FileNode) selectionPath.getLastPathComponent();
        File file = new File(node.toFile().getAbsolutePath());

        File[] files = new File[1];
        files[0] = file;
        return files;
    }
}
