package jcommander.pane.filetree;

import javax.swing.tree.TreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class FileTreeNode implements TreeNode {

    private final FileTreeNode parent;
    private List<FileTreeNode> children = null;
    private File file;

    public FileTreeNode(File file, FileTreeNode parent) {
        if (file == null) {
            throw new IllegalArgumentException("Descriptor shall not be null.");
        }

        this.parent = parent;
        this.file = file;
    }

    public FileTreeNode(File file) {
        this(file, null);
    }

    public File getFile() {
        return file;
    }

    public boolean rename(String newName) {
        File newFile = new File(file.getParent(), newName);
        if (!newFile.getParent().equals(file.getParent())) {
            return false;
        }

        if (file.renameTo(newFile)) {
            file = newFile;
            return true;
        }
        return false;
    }

    public void lazyLoadChildren() {
        if (file.isFile()) {
            return;
        }

        if (children == null) {
            children = new ArrayList<>();
        } else {
            children.clear();
        }

        File[] filesInDirectory = file.listFiles();
        // even though it technically should never be null, that's pretty much what our guard-clause is for,
        // but in some weird, hacky Windows-tested edge cases, it can indeed happen that a file is seemingly
        // a directory but one cannot access its contents
        if (filesInDirectory == null) {
            return;
        }

//        :))))
//        children = Arrays.asList(filesInDirectory).stream()
//                .map(f -> new FileTreeNode(f, this))
//                .collect(Collectors.toList());
        for (File file : filesInDirectory) {
            children.add(new FileTreeNode(file, this));
        }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        if (file.isFile()) {
            return null;
        }

        lazyLoadChildren(); // this way we're always updated on the current directory structure

        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        if (file.isFile()) {
            return 0;
        }

        File[] filesInDirectory = file.listFiles();
        if (filesInDirectory == null) {
            return 0;
        }

        return filesInDirectory.length; // note that this may differ from our current view on the FS (children)
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return children.indexOf((FileTreeNode) node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return file.isFile();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(children);
    }

    @Override
    public String toString() {
        if (parent != null) {
            return file.getName();
        }

        return file.getAbsolutePath();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FileTreeNode node)) {
            return false;
        }

        return file.equals(node.file);
    }
}
