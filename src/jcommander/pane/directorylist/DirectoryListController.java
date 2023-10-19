package jcommander.pane.directorylist;

import jcommander.filesystem.Handle;
import jcommander.pane.Controller;
import jcommander.pane.model.WorkingDirectory;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DirectoryListController implements Controller {

    private final WorkingDirectory wd;
    private final DirectoryListModel directoryModel;
    private final JList<Handle> listView;

    public DirectoryListController(WorkingDirectory wd) {
        this.wd = wd;

        directoryModel = new DirectoryListModel();
        listView = new JList<>(directoryModel);
        listView.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listView.setCellRenderer(new FileCellRenderer());
        listView.setDragEnabled(true);
        listView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = listView.locationToIndex(e.getPoint());
                    if (index == -1) {
                        return;
                    }

                    Handle file = directoryModel.getElementAt(index);
                    DirectoryListController.this.wd.setTo(file);
                }
            }
        });

        listView.setMinimumSize(listView.getPreferredScrollableViewportSize());
    }

    public JComponent component() {
        return listView;
    }

    @Override
    public void refresh() {
        listView.clearSelection();
        directoryModel.listDirectory(wd.list());
    }
}
