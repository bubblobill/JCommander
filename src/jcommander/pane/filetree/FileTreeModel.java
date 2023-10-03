package jcommander.pane.filetree;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class FileTreeModel implements TreeModel {

    private final RootNode rootNode;
    private final List<TreeModelListener> listeners = new ArrayList<>();

    public FileTreeModel() {
        rootNode = new RootNode();
    }

    @Override
    public Object getRoot() {
        return rootNode;
    }

    @Override
    public Object getChild(Object parent, int index) {
        TreeNode parentTreeNode = (TreeNode) parent;
        return parentTreeNode.getChildAt(index);
    }

    @Override
    public int getChildCount(Object parent) {
        TreeNode parentTreeNode = (TreeNode) parent;
        return parentTreeNode.getChildCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        TreeNode treeNode = (TreeNode) node;
        return treeNode.isLeaf();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        TreeNode parentTreeNode = (TreeNode) parent;
        TreeNode treeNode = (TreeNode) child;
        return parentTreeNode.getIndex(treeNode);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        String newName = (String) newValue;
        FileTreeNode node = (FileTreeNode) path.getLastPathComponent();
        if (node.rename(newName)) {
            notifyAllNodeChanged(new TreeModelEvent(node, path));
        }
    }

    public void refreshPath(TreePath path) {
        FileTreeNode node = (FileTreeNode) path.getLastPathComponent();
        node.lazyLoadChildren();
        // TODO: figure out, what should be the proper 'source' of this event
        // tbh. I don't even know why the API even bothers
        // in the javadoc, it says it's typical to set it to 'this', but frankly it's very smelly to me
        notifyAllStructureChanged(new TreeModelEvent(this, path));
    }

    private void notifyAllNodeChanged(TreeModelEvent e) {
        for (TreeModelListener listener : listeners) {
            listener.treeNodesChanged(e);
        }
    }

    private void notifyAllStructureChanged(TreeModelEvent e) {
        for (TreeModelListener listener : listeners) {
            listener.treeStructureChanged(e);
        }
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }
}
