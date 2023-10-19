package jcommander.pane;

import jcommander.filesystem.Handle;
import jcommander.history.HistoryChangeListener;
import jcommander.pane.directorylist.DirectoryListController;
import jcommander.pane.filetree.FileTreeController;
import jcommander.pane.model.WorkingDirectory;
import jcommander.pane.path.ParentButtonController;
import jcommander.pane.path.PathFieldController;

import javax.swing.*;
import java.awt.*;

public class WorkPane extends JComponent {

    private final WorkingDirectory wd = new WorkingDirectory();

    private final Controller parentButton;
    private final Controller pathField;

    private final Controller tree;
    private final Controller list;

    public WorkPane() {
        JPanel panel = new JPanel();
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

        add(panel);

        wd.addChangeListener(e -> refresh());
        wd.resetToRoot();

        setLayout(new FlowLayout());
        setPreferredSize(panel.getPreferredSize());
    }

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
