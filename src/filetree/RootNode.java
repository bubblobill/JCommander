package filetree;

import javax.swing.tree.TreeNode;
import java.io.File;
import java.util.*;

public class RootNode implements TreeNode {

    private final FileTreeNode[] mountPointNodes;

    public RootNode() {
        File[] mountPoints = File.listRoots();
        mountPointNodes = new FileTreeNode[mountPoints.length];

        for (int i = 0; i < mountPoints.length; i++) {
            FileTreeNode mountPointNode = new FileTreeNode(mountPoints[i]);
            mountPointNode.lazyLoadChildren();

            mountPointNodes[i] = mountPointNode;
        }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return mountPointNodes[childIndex];
    }

    @Override
    public int getChildCount() {
        return mountPointNodes.length;
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return Arrays.asList(mountPointNodes).indexOf((FileTreeNode) node);
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
        return Collections.enumeration(Arrays.asList(mountPointNodes));
    }

    @Override
    public String toString() {
        return "This PC";
    }
}
