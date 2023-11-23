package hu.bme.jcommander.bars;

import hu.bme.jcommander.settings.Settings;

import javax.swing.*;

public class MenuBarFacade {

    private final JMenuBar menuBar;

    public MenuBarFacade(Settings settings) {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        JMenu viewMenu = new JMenu("View");

        JCheckBoxMenuItem showTreeView = new JCheckBoxMenuItem("Show Tree View");
        showTreeView.setSelected(Boolean.parseBoolean(settings.get(Settings.Option.SHOW_TREE_VIEW)));
        showTreeView.addActionListener(event -> settings.set(Settings.Option.SHOW_TREE_VIEW, showTreeView.isSelected()));
        viewMenu.add(showTreeView);

        JCheckBoxMenuItem highlightActivePane = new JCheckBoxMenuItem("Highlight Active Pane");
        highlightActivePane.setSelected(Boolean.parseBoolean(settings.get(Settings.Option.HIGHLIGHT_ACTIVE_PANE)));
        highlightActivePane.addActionListener(event -> settings.set(Settings.Option.HIGHLIGHT_ACTIVE_PANE, highlightActivePane.isSelected()));
        viewMenu.add(highlightActivePane);

        menuBar.add(viewMenu);
    }

    public JMenuBar get() {
        return menuBar;
    }
}
