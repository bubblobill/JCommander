package org.reverence.jcommander.pane.filetree;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.filesystem.FileHandle;
import org.reverence.jcommander.filesystem.Handle;
import org.reverence.jcommander.filesystem.RenamingException;
import org.reverence.jcommander.settings.I18n;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class FileNode implements TreeNode, FsNode {

    private final FileNode parent;
    private final List<FileNode> children = new ArrayList<>();
    private final FileHandle file;

    /**
     * Constructs a FileNode with a pre-known parent node.
     *
     * @param file   the underlying file
     * @param parent the node of the underlying file's parent
     */
    public FileNode(FileHandle file, FileNode parent) {
        if (file == null) {
            throw new IllegalArgumentException(I18n.getText("error.descriptor.null"));
        }

        this.parent = parent;
        this.file = file;
    }

    /**
     * Constructs a FileNode representing a file from the file system as a node in the model of a JTree.
     *
     * @param file the underlying file
     */
    public FileNode(FileHandle file) {
        this(file, null);
    }

    /**
     * Renames the underlying file.
     *
     * @param newName the new name to be given to the file
     * @return true if the operation succeeded, false otherwise
     */
    public boolean rename(String newName) {
        try {
            file.rename(newName);
            return true;
        } catch (RenamingException ignored) {
            return false;
        }
    }

    /**
     * Loads the children of this node's underlying file as individual FileNodes.
     */
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
    public int hashCode() {
        return file.hashCode();
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

    @Override
    public Handle getHandle() {
        return file;
    }
}
