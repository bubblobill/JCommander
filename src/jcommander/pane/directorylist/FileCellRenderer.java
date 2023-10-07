package jcommander.pane.directorylist;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static jcommander.ResourceFactory.getIcon;

public class FileCellRenderer implements ListCellRenderer<File> {

    private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList<? extends File> list, File file, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel cell = (JLabel) defaultRenderer.getListCellRendererComponent(list, file, index, isSelected, cellHasFocus);

        if (file.getParentFile() == null) { // if it represents a mount point
            cell.setText(file.getAbsolutePath());
            cell.setIcon(getIcon("disk.png"));
        } else {
            cell.setText(file.getName());

            if (file.isFile()) {
                cell.setIcon(getIcon("file.png"));
                cell.setForeground(Color.BLACK);
            } else {
                cell.setIcon(getIcon("directory.png"));
                cell.setForeground(Color.BLUE);
            }
        }

        return cell;
    }
}
