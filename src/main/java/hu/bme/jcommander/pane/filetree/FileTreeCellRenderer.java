package hu.bme.jcommander.pane.filetree;

import hu.bme.jcommander.ResourceFactory;
import hu.bme.jcommander.filesystem.Handle;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class FileTreeCellRenderer implements TreeCellRenderer {

    private final DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
                                                  int row, boolean hasFocus) {
        FsNode node = (FsNode) value;
        Handle handle = node.getHandle();

        // because of Java API, our best chances are to utilize this totally not side effect free function
        renderer.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        renderer.setIcon(ResourceFactory.getIcon(handle.getAssociatedIcon()));
        renderer.setEnabled(tree.isEnabled());
        renderer.setText(handle.getName());

        return renderer;
    }
}
