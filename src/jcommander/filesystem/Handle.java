package jcommander.filesystem;

import jcommander.settings.IconType;

import javax.swing.*;

public interface Handle {

    String getAbsolutePath();

    String getName();

    Handle getParent();

    Handle[] getChildren();

    IconType getAssociatedIcon();

    boolean isLeaf();

    void rename(String to) throws RenamingException;
}
