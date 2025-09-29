package org.reverence.jcommander.pane.directorylist;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.ResourceFactory;
import org.reverence.jcommander.filesystem.Handle;
import org.reverence.jcommander.settings.Settings;

import javax.swing.*;
import java.awt.*;

import static org.reverence.jcommander.settings.Setting.SETTINGS;

public class DirectoryListCellRenderer implements ListCellRenderer<Handle> {

    private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList<? extends Handle> list, Handle handle, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel cell = (JLabel) defaultRenderer.getListCellRendererComponent(list, handle, index, isSelected, cellHasFocus);
        if(isSelected) {
            cell.setBorder(BorderFactory.createLineBorder(
                    SETTINGS.getColour(Settings.Option.CELL_BORDER_COLOUR_SELECTED),
                    1));
            cell.setForeground(SETTINGS.getColour(Settings.Option.CELL_FONT_COLOUR_SELECTED));
            cell.setBackground(SETTINGS.getColour(Settings.Option.CELL_BACKGROUND_COLOUR_SELECTED));
        } else {
            cell.setBorder(BorderFactory.createLineBorder(
                    SETTINGS.getColour(Settings.Option.CELL_BORDER_COLOUR),
                    1));
            cell.setForeground(SETTINGS.getColour(Settings.Option.CELL_FONT_COLOUR));
            cell.setBackground(SETTINGS.getColour(Settings.Option.CELL_BACKGROUND_COLOUR));
        }
        cell.setText(handle.getName());
        cell.setIcon(ResourceFactory.getIcon(handle.getAssociatedIcon()));

        return cell;
    }
}
