package jcommander.pane.filetree;

import jcommander.filesystem.FileHandle;
import jcommander.filesystem.Handle;
import jcommander.filesystem.RenamingException;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class FileNode implements TreeNode {

    private final FileNode parent;
    private final List<FileNode> children = new ArrayList<>();
    private final FileHandle file;

    public FileNode(FileHandle file, FileNode parent) {
        if (file == null) {
            throw new IllegalArgumentException("Descriptor shall not be null.");
        }

        this.parent = parent;
        this.file = file;
    }

    public FileNode(FileHandle file) {
        this(file, null);
    }

    public boolean rename(String newName) {
        try {
            file.rename(newName);
            return true;
        } catch (RenamingException ignored) {
            return false;
        }
    }

    public void lazyLoadChildren() {
        children.clear();
        for (Handle child : file.getChildren()) {
            children.add(new FileNode((FileHandle) child, this));
        }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        lazyLoadChildren(); // this way we're always updated on the current directory structure
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        lazyLoadChildren();
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
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
        return file.isLeaf();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(children);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FileNode node)) {
            return false;
        }

        return file.equals(node.file);
    }

    @Override
    public String toString() {
        return file.getName();
    }

    public Handle toFile() {
        return file;
    }
}
