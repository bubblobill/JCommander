package jcommander.pane.directorylist;

import jcommander.filesystem.Handle;

import javax.swing.*;
import java.awt.*;

public class FileCellRenderer implements ListCellRenderer<Handle> {

    private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList<? extends Handle> list, Handle file, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel cell = (JLabel) defaultRenderer.getListCellRendererComponent(list, file, index, isSelected, cellHasFocus);

        cell.setText(file.getName());
        cell.setIcon(file.getAssociatedIcon());

        return cell;
    }
}
