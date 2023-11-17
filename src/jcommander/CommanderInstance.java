package jcommander;

import jcommander.operation.*;
import jcommander.pane.WorkPane;
import jcommander.settings.IconType;
import jcommander.settings.Settings;

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

import static jcommander.ResourceFactory.getIcon;

public class CommanderInstance {

    private static final String INSTANCE_TITLE = "JCommander";
    private static final String SETTINGS_FILE_NAME = "settings.txt";

    private final Settings settings = new Settings(new File(SETTINGS_FILE_NAME));

    private final JFrame frame;
    private final WorkPane paneA;
    private final WorkPane paneB;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(Runtime.getRuntime()
                                                                                                .availableProcessors());
    private WorkPane activePane;
    private WorkPane passivePane;
    private JButton previous;
    private JButton next;

    public CommanderInstance() {
        frame = new JFrame(INSTANCE_TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

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
        frame.add(paneA.component(), BorderLayout.WEST);

        paneB = factoryB.create(WorkPane.class, factoryB);
        paneB.addHistoryChangeListener(e -> {
            if (activePane == paneB) {
                updateHistoryButtons(e.canUndo(), e.canRedo());
            }
        });
        frame.add(paneB.component(), BorderLayout.EAST);

        createMenuBar();
        createTopBar(frame);
        createCenterBar(frame);

        // by default, paneA is in foreground, and paneB is in background
        setActiveAndPassivePane(paneA, paneB);

        settings.refreshSettings();

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void setActiveAndPassivePane(WorkPane active, WorkPane passive) {
        activePane = active;
        passivePane = passive;
        activePane.notifyAllAboutWdHistory();
        activePane.component()
                  .setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
        passivePane.component()
                   .setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.WHITE));
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
        JCheckBoxMenuItem showTreeViewItem = new JCheckBoxMenuItem("Show Tree View");
        showTreeViewItem.setSelected(Boolean.parseBoolean(settings.get(Settings.Option.SHOW_TREE_VIEW)));
        showTreeViewItem.addActionListener(event -> settings.set(Settings.Option.SHOW_TREE_VIEW,
                showTreeViewItem.isSelected()));
        viewMenu.add(showTreeViewItem);

        menuBar.add(viewMenu);

        frame.setJMenuBar(menuBar);
    }

    private void createTopBar(Container container) {
        JToolBar topBar = new JToolBar(SwingConstants.HORIZONTAL);
        topBar.setFloatable(false);
        JButton refresh = new JButton();
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
        container.add(topBar, BorderLayout.NORTH);
    }

    private void createCenterBar(Container container) {
        JToolBar centerBar = new JToolBar(SwingConstants.VERTICAL);
        centerBar.setFloatable(false);
        JButton copy = new JButton();
        copy.setIcon(getIcon(IconType.COPY));
        copy.addActionListener(e -> issueFileOperation(CopyOperation.class));
        centerBar.add(copy);
        JButton move = new JButton();
        move.setIcon(getIcon(IconType.MOVE));
        move.addActionListener(e -> issueFileOperation(MoveOperation.class));
        centerBar.add(move);
        container.add(centerBar, BorderLayout.CENTER);
    }

    private void issueFileOperation(Class<? extends FileOperation> operationClass) {
        Constructor<?>[] declaredConstructors = operationClass.getDeclaredConstructors();
        Optional<Constructor<?>> matching = Arrays.stream(declaredConstructors)
                                               .filter(constructor -> constructor.getParameterCount() == 2).findFirst();
        if (matching.isEmpty()) {
            throw new IllegalArgumentException("Operation class has no two-parameter constructor.");
        }
        Constructor<?> constructor = matching.get();

        for (File sourceFile : activePane.getSelectedFiles()) {
            File targetFile = new File(passivePane.getWorkingDirectoryPath(), sourceFile.getName());
            try {
                Operation operation = (Operation) constructor.newInstance(sourceFile, targetFile);
                executor.execute(operation.then(new RefreshOperation(passivePane)));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException("Operation is unsuitable.");
            }
        }
    }
}
