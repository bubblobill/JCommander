package org.reverence.jcommander;

/*
 * Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.bars.MenuBarFacade;
import org.reverence.jcommander.bars.NavigationBarFacade;
import org.reverence.jcommander.bars.ToolBarFacade;
import org.reverence.jcommander.operation.OperationExecutor;
import org.reverence.jcommander.pane.WorkPane;
import org.reverence.jcommander.settings.Settings;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.function.Supplier;

import static org.reverence.jcommander.settings.Setting.*;

public class CommanderInstance {
    private final OperationExecutor executor = new OperationExecutor();

    private final JFrame frame;
    private final WorkPane paneA;
    private final WorkPane paneB;
    private final NavigationBarFacade navBar;
    private final ToolBarFacade toolBar;
    private WorkPane activePane;
    private WorkPane passivePane;

    /**
     * Initializes a CommanderInstance and encapsulates the segments of the UI into one cohesive class:
     * <ul>
     *     <li>sets up all the UI elements,</li>
     *     <li>composes them into a layout,</li>
     *     <li>then makes the window visible.</li>
     * </ul>
     */
    public CommanderInstance() {
        frame = new JFrame();
        frame.setTitle(INSTANCE_TITLE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(new MenuBarFacade().get());

        // Initialize paneA

        ComponentFactory factoryA = new ComponentFactory(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setActiveAndPassivePane(paneA, paneB);
            }
        });

        paneA = factoryA.create(WorkPane.class, factoryA);
        paneA.addHistoryChangeListener(e -> {
            if (activePane == paneA) {
                updateHistoryButtons(e.canUndo(), e.canRedo());
            }
        });

        // Initialize paneB

        ComponentFactory factoryB = new ComponentFactory(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setActiveAndPassivePane(paneB, paneA);
            }
        });

        paneB = factoryB.create(WorkPane.class, factoryB);
        paneB.addHistoryChangeListener(e -> {
            if (activePane == paneB) {
                updateHistoryButtons(e.canUndo(), e.canRedo());
            }
        });

        // Initialize navigation- and toolbars.

        Supplier<WorkPane> getActivePane = () -> activePane;
        Supplier<WorkPane> getPassivePane = () -> passivePane;

        navBar = new NavigationBarFacade(paneA, paneB, getActivePane);
        toolBar = new ToolBarFacade(frame, executor, getActivePane, getPassivePane);

        setActiveAndPassivePane(paneA, paneB); // by default, paneA is in foreground, and paneB is in background

        GroupLayout layout = new GroupLayout(frame.getContentPane());

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(navBar.get())
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(paneA.component())
                                        .addComponent(toolBar.get())
                                        .addComponent(paneB.component())
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(navBar.get())
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(paneA.component())
                                        .addComponent(toolBar.get())
                                        .addComponent(paneB.component())
                        )
        );

        SETTINGS.addSettingChangedListener(event -> {
            if (event.option() == Settings.Option.HIGHLIGHT_ACTIVE_PANE) {
                setPaneBorderVisibility(Boolean.parseBoolean(event.value()));
            }
        });

        SETTINGS.refreshSettings();

        frame.getContentPane().setLayout(layout);
        frame.setVisible(true);
    }

    private void setActiveAndPassivePane(WorkPane active, WorkPane passive) {
        activePane = active;
        passivePane = passive;
        activePane.notifyAllAboutWdHistory();

        setPaneBorderVisibility(Boolean.parseBoolean(SETTINGS.get(Settings.Option.HIGHLIGHT_ACTIVE_PANE)));
    }

    private void setPaneBorderVisibility(boolean visible) {
        Color activeColor = visible ? ACTIVE_COLOR : PASSIVE_COLOR;

        // The active pane only gets colored differently if border highlighting of active pane is enabled.
        activePane.component()
                .setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, activeColor, activeColor));
        passivePane.component()
                .setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, PASSIVE_COLOR, PASSIVE_COLOR));
    }

    private void updateHistoryButtons(boolean undo, boolean redo) {
        navBar.updateHistoryButtons(undo, redo);
    }
}
