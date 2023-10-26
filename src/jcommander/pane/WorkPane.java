package jcommander.pane;

import jcommander.history.HistoryChangeListener;
import jcommander.pane.directorylist.DirectoryListController;
import jcommander.pane.filetree.FileTreeController;
import jcommander.pane.model.WorkingDirectory;
import jcommander.pane.path.ParentButtonController;
import jcommander.pane.path.PathFieldController;

import javax.swing.*;
import java.awt.*;

public class WorkPane implements Controller {

    private final WorkingDirectory wd = new WorkingDirectory();

    private final JPanel panel;

    private final Controller parentButton;
    private final Controller pathField;

    private final Controller tree;
    private final Controller list;

    public WorkPane() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JToolBar pathBar = new JToolBar();
        pathBar.setFloatable(false);

        parentButton = new ParentButtonController(wd);
        pathField = new PathFieldController(wd);

        pathBar.add(parentButton.component());
        pathBar.add(pathField.component());
        panel.add(pathBar, BorderLayout.NORTH);

        tree = new FileTreeController(wd);
        list = new DirectoryListController(wd);

        JSplitPane views = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(tree.component()), new JScrollPane(list.component()));

        panel.add(views, BorderLayout.CENTER);

        wd.addChangeListener(e -> refresh());
        wd.resetToRoot();

        panel.setPreferredSize(panel.getPreferredSize());
    }

    @Override
    public JComponent component() {
        return panel;
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

    public void addHistoryChangeListener(HistoryChangeListener l) {
        wd.addHistoryChangeListener(l);
    }

    public void removeHistoryChangeListener(HistoryChangeListener l) {
        wd.removeHistoryChangeListener(l);
    }
}
