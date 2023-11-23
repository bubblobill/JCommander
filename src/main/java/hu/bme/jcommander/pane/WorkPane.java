package hu.bme.jcommander.pane;

import hu.bme.jcommander.ComponentFactory;
import hu.bme.jcommander.history.HistoryChangeListener;
import hu.bme.jcommander.pane.directorylist.DirectoryListController;
import hu.bme.jcommander.pane.filetree.FileTreeController;
import hu.bme.jcommander.pane.model.WorkingDirectory;
import hu.bme.jcommander.pane.path.ParentButtonController;
import hu.bme.jcommander.pane.path.PathFieldController;
import hu.bme.jcommander.settings.SettingChangeListener;
import hu.bme.jcommander.settings.SettingChangedEvent;
import hu.bme.jcommander.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class WorkPane implements SelectionController, SettingChangeListener {

    private static final double TREE_TO_DIR_RATIO = 0.3d; // 30% for the file tree - 70% for the directory list
    private final WorkingDirectory wd = new WorkingDirectory();

    private final JPanel panel;

    private final Controller parentButton;
    private final Controller pathField;

    private final SelectionController tree;
    private final SelectionController list;
    private final JSplitPane views;

    public WorkPane(ComponentFactory factory) {

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JToolBar pathBar = new JToolBar();
        pathBar.setFloatable(false);

        parentButton = factory.create(ParentButtonController.class, wd);
        pathField = factory.create(PathFieldController.class, wd);

        pathBar.add(parentButton.component());
        pathBar.add(pathField.component());
        panel.add(pathBar, BorderLayout.NORTH);

        tree = factory.create(FileTreeController.class, wd);
        list = factory.create(DirectoryListController.class, wd);

        views = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(tree.component()), new JScrollPane(list.component()));
        views.setResizeWeight(TREE_TO_DIR_RATIO);

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

    public void notifyAllAboutWdHistory() {
        wd.notifyAllAboutWdHistory();
    }

    public void selectPrevious() {
        wd.selectPrevious();
    }

    public void selectNext() {
        wd.selectNext();
    }

    public String getWorkingDirectoryPath() {
        return wd.getAbsolutePath();
    }

    @Override
    public File[] getSelectedFiles() {
        return list.getSelectedFiles();
    }

    public void addHistoryChangeListener(HistoryChangeListener l) {
        wd.addHistoryChangeListener(l);
    }

    public void removeHistoryChangeListener(HistoryChangeListener l) {
        wd.removeHistoryChangeListener(l);
    }

    @Override
    public void settingChanged(SettingChangedEvent event) {
        if (event.option() == Settings.Option.SHOW_TREE_VIEW) {
            boolean visible = Boolean.parseBoolean(event.value().toString());
            if (visible) {
                views.setLeftComponent(tree.component());
            } else {
                views.setLeftComponent(null);
            }
            views.setResizeWeight(TREE_TO_DIR_RATIO);
        }
    }
}
