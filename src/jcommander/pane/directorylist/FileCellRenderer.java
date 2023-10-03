package jcommander.pane.directorylist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import static jcommander.ResourceFactory.getSquareIcon;

public class FileCellRenderer implements ListCellRenderer<File> {

    private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList<? extends File> list, File file, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel cell = (JLabel) defaultRenderer.getListCellRendererComponent(list, file, index, isSelected, cellHasFocus);
        cell.setText(file.getName());

        if (file.isFile()) {
            cell.setIcon(getSquareIcon("file.png"));
            cell.setForeground(Color.BLACK);
        } else {
            cell.setIcon(getSquareIcon("directory.png"));
            cell.setForeground(Color.BLUE);
        }

        return cell;
    }
}
