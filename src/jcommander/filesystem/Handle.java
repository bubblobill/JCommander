package jcommander.filesystem;

import javax.swing.*;

public interface Handle {

    String getAbsolutePath();

    String getName();

    Handle getParent();

    Handle[] getChildren();

    Icon getAssociatedIcon();

    boolean isLeaf();

    void rename(String to) throws RenamingException;
}
