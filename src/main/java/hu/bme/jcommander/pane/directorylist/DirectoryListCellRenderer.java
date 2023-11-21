package hu.bme.jcommander.pane.directorylist;

import hu.bme.jcommander.ResourceFactory;
import hu.bme.jcommander.filesystem.Handle;

import javax.swing.*;
import java.awt.*;

public class DirectoryListCellRenderer implements ListCellRenderer<Handle> {

    private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList<? extends Handle> list, Handle handle, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel cell = (JLabel) defaultRenderer.getListCellRendererComponent(list, handle, index, isSelected, cellHasFocus);

        cell.setText(handle.getName());
        cell.setIcon(ResourceFactory.getIcon(handle.getAssociatedIcon()));

        return cell;
    }
}
