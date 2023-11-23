package hu.bme.jcommander;

import hu.bme.jcommander.bars.MenuBarFacade;
import hu.bme.jcommander.bars.NavigationBarFacade;
import hu.bme.jcommander.bars.ToolBarFacade;
import hu.bme.jcommander.operation.*;
import hu.bme.jcommander.pane.WorkPane;
import hu.bme.jcommander.settings.Settings;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.function.Supplier;

public class CommanderInstance {

    private static final String SETTINGS_FILE_NAME = "settings.txt";

    private static final String INSTANCE_TITLE = "JCommander";
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static final Color ACTIVE_COLOR = Color.RED;
    public static final Color PASSIVE_COLOR = Color.WHITE;

    private final Settings settings = new Settings(new File(SETTINGS_FILE_NAME));

    private final OperationExecutor executor = new OperationExecutor();

    private final JFrame frame;
    private final WorkPane paneA;
    private final WorkPane paneB;
    private final NavigationBarFacade navBar;
    private final ToolBarFacade toolBar;
    private WorkPane activePane;
    private WorkPane passivePane;

    public CommanderInstance() {
        frame = new JFrame();
        frame.setTitle(INSTANCE_TITLE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(new MenuBarFacade(settings).get());

        // Initialize paneA

        ComponentFactory factoryA = new ComponentFactory(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setActiveAndPassivePane(paneA, paneB);
            }
        }, settings);

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
        }, settings);

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

        // by default, paneA is in foreground, and paneB is in background
        setActiveAndPassivePane(paneA, paneB);

        settings.addSettingChangedListener(event -> {
            if (event.option() == Settings.Option.HIGHLIGHT_ACTIVE_PANE) {
                setPaneBorderVisibility(Boolean.parseBoolean(event.value()));
            }
        });

        settings.refreshSettings();

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

        frame.getContentPane().setLayout(layout);
        frame.setVisible(true);
    }

    private void setActiveAndPassivePane(WorkPane active, WorkPane passive) {
        activePane = active;
        passivePane = passive;
        activePane.notifyAllAboutWdHistory();

        setPaneBorderVisibility(Boolean.parseBoolean(settings.get(Settings.Option.HIGHLIGHT_ACTIVE_PANE)));
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
