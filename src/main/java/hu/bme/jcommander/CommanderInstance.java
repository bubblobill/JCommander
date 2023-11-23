package hu.bme.jcommander;

import hu.bme.jcommander.operation.*;
import hu.bme.jcommander.pane.WorkPane;
import hu.bme.jcommander.settings.IconType;
import hu.bme.jcommander.settings.Settings;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static hu.bme.jcommander.ResourceFactory.getIcon;

public class CommanderInstance {

    private static final String INSTANCE_TITLE = "JCommander";
    private static final String SETTINGS_FILE_NAME = "settings.txt";
    public static final Color ACTIVE_COLOR = Color.RED;
    public static final Color PASSIVE_COLOR = Color.WHITE;

    private final Settings settings = new Settings(new File(SETTINGS_FILE_NAME));

    private final JFrame frame;
    private final WorkPane paneA;
    private final WorkPane paneB;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(Runtime.getRuntime()
            .availableProcessors());
    private WorkPane activePane;
    private WorkPane passivePane;
    private final JButton previous;
    private final JButton next;

    public CommanderInstance() {
        frame = new JFrame(INSTANCE_TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ComponentFactory factoryA = new ComponentFactory(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setActiveAndPassivePane(paneA, paneB);
            }
        }, settings);

        ComponentFactory factoryB = new ComponentFactory(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setActiveAndPassivePane(paneB, paneA);
            }
        }, settings);

        paneA = factoryA.create(WorkPane.class, factoryA);
        paneA.addHistoryChangeListener(e -> {
            if (activePane == paneA) {
                updateHistoryButtons(e.canUndo(), e.canRedo());
            }
        });

        paneB = factoryB.create(WorkPane.class, factoryB);
        paneB.addHistoryChangeListener(e -> {
            if (activePane == paneB) {
                updateHistoryButtons(e.canUndo(), e.canRedo());
            }
        });

        createMenuBar();

        JToolBar topBar = new JToolBar(SwingConstants.HORIZONTAL);
        topBar.setFloatable(false);
        JButton refresh = new JButton();
        refresh.setFocusable(false);
        refresh.setIcon(getIcon(IconType.REFRESH));
        refresh.addActionListener(e -> {
            paneA.refresh();
            paneB.refresh();
        });
        topBar.add(refresh);
        previous = new JButton();
        previous.setFocusable(false);
        previous.setIcon(getIcon(IconType.LEFT));
        previous.addActionListener(e -> activePane.selectPrevious());
        topBar.add(previous);
        next = new JButton();
        next.setFocusable(false);
        next.setIcon(getIcon(IconType.RIGHT));
        next.addActionListener(e -> activePane.selectNext());
        topBar.add(next);

        JToolBar centerBar = new JToolBar(SwingConstants.VERTICAL);
        centerBar.setFloatable(false);

        JButton newDir = new JButton();
        newDir.setFocusable(false);
        newDir.setIcon(getIcon(IconType.NEW_DIRECTORY));
        newDir.addActionListener(e -> issueNewDirectoryOperation());
        centerBar.add(newDir);

        JButton delete = new JButton();
        delete.setFocusable(false);
        delete.setIcon(getIcon(IconType.DELETE));
        delete.addActionListener(e -> issueDeleteOperation(activePane.getSelectedFiles()));
        centerBar.add(delete);

        JButton copy = new JButton();
        copy.setFocusable(false);
        copy.setIcon(getIcon(IconType.COPY));
        copy.addActionListener(e -> issueFileOperation(CopyOperation.class));
        centerBar.add(copy);

        JButton move = new JButton();
        move.setFocusable(false);
        move.setIcon(getIcon(IconType.MOVE));
        move.addActionListener(e -> issueFileOperation(MoveOperation.class));
        centerBar.add(move);

        // by default, paneA is in foreground, and paneB is in background
        setActiveAndPassivePane(paneA, paneB);

        settings.addSettingChangedListener(event -> {
            if (event.option() == Settings.Option.HIGHLIGHT_ACTIVE_PANE) {
                setPaneBorderVisibility(Boolean.parseBoolean(event.value().toString()));
            }
        });

        settings.refreshSettings();

        GroupLayout layout = new GroupLayout(frame.getContentPane());

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(topBar)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(paneA.component())
                                        .addComponent(centerBar)
                                        .addComponent(paneB.component())
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(topBar)
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(paneA.component())
                                        .addComponent(centerBar)
                                        .addComponent(paneB.component())
                        )
        );

        frame.getContentPane().setLayout(layout);
        frame.setSize(1280, 720);
        frame.setVisible(true);
    }

    private void setPaneBorderVisibility(boolean visible) {
        Color activeColor = visible ? ACTIVE_COLOR : PASSIVE_COLOR;

        // The active pane only gets colored differently if border highlighting of active pane is enabled.
        activePane.component()
                .setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, activeColor, activeColor));
        passivePane.component()
                .setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, PASSIVE_COLOR, PASSIVE_COLOR));
    }

    private void setActiveAndPassivePane(WorkPane active, WorkPane passive) {
        activePane = active;
        passivePane = passive;
        activePane.notifyAllAboutWdHistory();

        setPaneBorderVisibility(Boolean.parseBoolean(settings.get(Settings.Option.HIGHLIGHT_ACTIVE_PANE)));
    }

    private void updateHistoryButtons(boolean undo, boolean redo) {
        previous.setEnabled(undo);
        next.setEnabled(redo);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
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

        frame.setJMenuBar(menuBar);
    }

    private void issueDeleteOperation(File[] selectedFiles) {
        String title = "Delete Files";
        String message = "Are you sure you want to delete every selected file and directory?";
        if (JOptionPane.showConfirmDialog(frame, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            executor.execute(new DeleteOperation(selectedFiles).then(new RefreshOperation(activePane)));
        }
    }

    private void issueNewDirectoryOperation() {
        Operation operation = new NewDirectoryOperation(activePane.getWorkingDirectoryPath()
                + File.separator
                + "New Directory")
                .then(new RefreshOperation(activePane));
        executor.execute(operation);
    }

    private void issueFileOperation(Class<? extends FileOperation> operationClass) {
        Constructor<?>[] declaredConstructors = operationClass.getDeclaredConstructors();
        Optional<Constructor<?>> matching = Arrays.stream(declaredConstructors)
                .filter(constructor -> constructor.getParameterCount() == 2)
                .findFirst();
        if (matching.isEmpty()) {
            throw new IllegalArgumentException("Operation class has no two-parameter constructor.");
        }
        Constructor<?> constructor = matching.get();

        for (File sourceFile : activePane.getSelectedFiles()) {
            File targetFile = new File(passivePane.getWorkingDirectoryPath(), sourceFile.getName());
            try {
                Operation operation = (Operation) constructor.newInstance(sourceFile, targetFile);
                executor.execute(operation.then(new RefreshOperation(passivePane))
                        .then(new RefreshOperation(activePane)));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException("Operation is unsuitable.");
            }
        }
    }
}
