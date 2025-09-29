package org.reverence.jcommander.pane.filetree;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.ResourceFactory;
import org.reverence.jcommander.filesystem.Handle;
import org.reverence.jcommander.settings.Settings;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

import static org.reverence.jcommander.settings.Setting.SETTINGS;

public class FileTreeCellRenderer implements TreeCellRenderer {

    private final DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
                                                  int row, boolean hasFocus) {
        FsNode node = (FsNode) value;
        Handle handle = node.getHandle();

        // because of Java API, our best chances are to utilize this totally not side effect free function
        renderer.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if(sel) {
            renderer.setBorder(BorderFactory.createLineBorder(
                    SETTINGS.getColour(Settings.Option.CELL_BORDER_COLOUR_SELECTED),
                    1));
            renderer.setForeground(SETTINGS.getColour(Settings.Option.CELL_FONT_COLOUR_SELECTED));
            renderer.setBackground(SETTINGS.getColour(Settings.Option.CELL_BACKGROUND_COLOUR_SELECTED));
        } else {
            renderer.setBorder(BorderFactory.createLineBorder(
                    SETTINGS.getColour(Settings.Option.CELL_BORDER_COLOUR),
                    1));
            renderer.setForeground(SETTINGS.getColour(Settings.Option.CELL_FONT_COLOUR));
            renderer.setBackground(SETTINGS.getColour(Settings.Option.CELL_BACKGROUND_COLOUR));
        }

        renderer.setIcon(ResourceFactory.getIcon(handle.getAssociatedIcon()));
        renderer.setEnabled(tree.isEnabled());
        renderer.setText(handle.getName());

        return renderer;
    }
}
