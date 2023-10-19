package jcommander.pane;

import jcommander.filesystem.Handle;
import jcommander.history.HistoryChangeListener;
import jcommander.pane.directorylist.DirectoryList;
import jcommander.pane.filetree.FileTreeController;
import jcommander.pane.model.WorkingDirectory;
import jcommander.pane.path.ParentButton;
import jcommander.pane.path.PathField;

import javax.swing.*;
import java.awt.*;

public class WorkPane extends JComponent implements Refreshable {

    private final WorkingDirectory wd = new WorkingDirectory();

    private final ParentButton parentButton;
    private final PathField pathField;

    private final FileTreeController tree;
    private final DirectoryList list;

    public WorkPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JToolBar pathBar = new JToolBar();
        pathBar.setFloatable(false);

        parentButton = new ParentButton(wd);
        pathBar.add(parentButton);

        pathField = new PathField(wd);
        pathBar.add(pathField);

        panel.add(pathBar, BorderLayout.NORTH);

        tree = new FileTreeController(wd);
        list = new DirectoryList(wd);

        JSplitPane views = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(tree.component()), new JScrollPane(list.component()));

        panel.add(views, BorderLayout.CENTER);

        add(panel);

        wd.addChangeListener(e -> refresh());
        wd.resetToRoot();

        setLayout(new FlowLayout());
        setPreferredSize(panel.getPreferredSize());
    }

    @Override
    public void refresh() {
        parentButton.refresh();
        pathField.refresh();
        tree.refresh();
        list.refresh();
    }

    public void selectPrevious() {
        wd.selectPrevious();
    }

    public void selectNext() {
        wd.selectNext();
    }

    public void addHistoryChangeListener(HistoryChangeListener<Handle> l) {
        wd.addHistoryChangeListener(l);
    }

    public void removeHistoryChangeListener(HistoryChangeListener<Handle> l) {
        wd.removeHistoryChangeListener(l);
    }
}
