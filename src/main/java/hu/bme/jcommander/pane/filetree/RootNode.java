package hu.bme.jcommander.pane.filetree;

import hu.bme.jcommander.filesystem.FileHandle;
import hu.bme.jcommander.filesystem.Handle;
import hu.bme.jcommander.filesystem.RootHandle;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class RootNode implements TreeNode, FsNode {

    private final RootHandle root;
    private final List<FileNode> children = new ArrayList<>();

    /**
     * Constructs a RootNode with the specified root handle.
     *
     * @param root the root handle representing the root of the file system
     */
    public RootNode(RootHandle root) {
        this.root = root;
        refresh();
    }

    /**
     * Refreshes the child nodes of the root node.
     */
    public void refresh() {
        children.clear();
        for (Handle mount : root.getChildren()) {
            children.add(new FileNode((FileHandle) mount));
        }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(children);
    }

    @Override
    public String toString() {
        return root.getName();
    }

    @Override
    public Handle getHandle() {
        return root;
    }
}
