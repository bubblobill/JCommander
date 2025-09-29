package org.reverence.jcommander.bars;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.settings.I18n;
import org.reverence.jcommander.settings.Settings;

import javax.swing.*;

import static org.reverence.jcommander.settings.Setting.SETTINGS;

public class MenuBarFacade {

    private final JMenuBar menuBar;

    /**
     * Constructs a MenuBarFacade associated with the specified settings.
     */
    public MenuBarFacade() {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(I18n.getText("menu.file"));
        JMenuItem exitItem = new JMenuItem(I18n.getText("menu.exit"));
        exitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        JMenu viewMenu = new JMenu(I18n.getText("menu.view"));

        JCheckBoxMenuItem showTreeView = new JCheckBoxMenuItem(I18n.getText("menu.show.tree"));
        showTreeView.setSelected(Boolean.parseBoolean(SETTINGS.get(Settings.Option.SHOW_TREE_VIEW)));
        showTreeView.addActionListener(event -> SETTINGS.set(Settings.Option.SHOW_TREE_VIEW, showTreeView.isSelected()));
        viewMenu.add(showTreeView);

        JCheckBoxMenuItem highlightActivePane = new JCheckBoxMenuItem(I18n.getText("menu.highlight.active.pane"));
        highlightActivePane.setSelected(Boolean.parseBoolean(SETTINGS.get(Settings.Option.HIGHLIGHT_ACTIVE_PANE)));
        highlightActivePane.addActionListener(event -> SETTINGS.set(Settings.Option.HIGHLIGHT_ACTIVE_PANE, highlightActivePane.isSelected()));
        viewMenu.add(highlightActivePane);

        menuBar.add(viewMenu);
    }

    /**
     * Retrieves the actual view as a menu bar component.
     *
     * @return the menu bar
     */
    public JMenuBar get() {
        return menuBar;
    }
}
