package hu.bme.jcommander.pane.directorylist;

import hu.bme.jcommander.filesystem.Handle;
import hu.bme.jcommander.pane.SelectionController;
import hu.bme.jcommander.pane.model.WorkingDirectory;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class DirectoryListController implements SelectionController {

    private final WorkingDirectory wd;
    private final DirectoryListModel directoryModel;
    private final JList<Handle> listView;

    public DirectoryListController(WorkingDirectory wd) {
        this.wd = wd;

        directoryModel = new DirectoryListModel();
        listView = new JList<>(directoryModel);
        listView.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listView.setCellRenderer(new DirectoryListCellRenderer());
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
    }

    public JComponent component() {
        return listView;
    }

    @Override
    public void refresh() {
        listView.clearSelection();
        directoryModel.listDirectory(wd.list());
    }

    @Override
    public File[] getSelectedFiles() {
        if (wd.isRoot()) {
            return new File[0];
        }

        return listView.getSelectedValuesList()
                .stream()
                .map(handle -> new File(handle.getAbsolutePath()))
                .toList()
                .toArray(new File[0]);
    }
}
