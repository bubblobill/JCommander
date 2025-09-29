package org.reverence.jcommander.pane.directorylist;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.ResourceFactory;
import org.reverence.jcommander.filesystem.Handle;

import javax.swing.*;
import java.awt.*;

public class DirectoryListCellRenderer implements ListCellRenderer<Handle> {

    private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList<? extends Handle> list, Handle handle, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel cell = (JLabel) defaultRenderer.getListCellRendererComponent(list, handle, index, isSelected, cellHasFocus);
        cell.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
        cell.setText(handle.getName());
        cell.setIcon(ResourceFactory.getIcon(handle.getAssociatedIcon()));

        return cell;
    }
}
