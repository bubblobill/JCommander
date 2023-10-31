package jcommander;

import jcommander.operation.CopyOperation;
import jcommander.operation.RefreshOperation;
import jcommander.pane.WorkPane;
import jcommander.settings.IconStyle;
import jcommander.settings.IconType;
import jcommander.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static jcommander.ResourceFactory.getIcon;

public class CommanderInstance {

    private static final String INSTANCE_TITLE = "JCommander";

    private final Settings settings = new Settings(new File("settings.txt"));

    private final JFrame frame;
    private final WorkPane paneA;
    private final WorkPane paneB;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(Runtime.getRuntime()
                                                                                                .availableProcessors());
    private WorkPane activePane;
    private WorkPane passivePane;
    private JButton previous;
    private JButton next;

    public CommanderInstance() throws IOException {
        frame = new JFrame(INSTANCE_TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        createMenuBar();
        createTopBar(frame);

        paneA = new WorkPane();
        paneA.addHistoryChangeListener(e -> {
            if (activePane == paneA) {
                updateHistoryButtons(e.canUndo(), e.canRedo());
            }
        });
        frame.add(paneA.component(), BorderLayout.WEST);

        createCenterBar(frame);

        paneB = new WorkPane();
        paneB.addHistoryChangeListener(e -> {
            if (activePane == paneB) {
                updateHistoryButtons(e.canUndo(), e.canRedo());
            }
        });
        frame.add(paneB.component(), BorderLayout.EAST);

        activePane = paneA; // by default, paneA is in focus
        passivePane = paneB; // TODO: it must always be the pane that is not the active pane

        updateHistoryButtons(false, false);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
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
        showTreeViewItem.addActionListener(event -> settings.setShowTreeView(viewMenu.isSelected()));
        viewMenu.add(showTreeViewItem);

        menuBar.add(viewMenu);

        frame.setJMenuBar(menuBar);
    }

    private void createTopBar(Container container) {
        JToolBar topBar = new JToolBar(SwingConstants.HORIZONTAL);
        topBar.setFloatable(false);
        JButton refresh = new JButton();
        refresh.setIcon(getIcon(IconType.REFRESH, IconStyle.COLORFUL));
        refresh.addActionListener(e -> {
            paneA.refresh();
            paneB.refresh();
        });
        topBar.add(refresh);
        JButton find = new JButton();
        find.setIcon(getIcon(IconType.FIND, IconStyle.COLORFUL));
        find.addActionListener(e -> openFindDialog());
        topBar.add(find);
        previous = new JButton();
        previous.setIcon(getIcon(IconType.LEFT, IconStyle.COLORFUL));
        previous.addActionListener(e -> activePane.selectPrevious());
        topBar.add(previous);
        next = new JButton();
        next.setIcon(getIcon(IconType.RIGHT, IconStyle.COLORFUL));
        next.addActionListener(e -> activePane.selectNext());
        topBar.add(next);
        container.add(topBar, BorderLayout.NORTH);
    }

    private void createCenterBar(Container container) {
        JToolBar centerBar = new JToolBar(SwingConstants.VERTICAL);
        centerBar.setFloatable(false);
        JButton copy = new JButton();
        copy.setIcon(getIcon(IconType.COPY, IconStyle.COLORFUL));
        copy.addActionListener(e -> issueCopyOperation());
        centerBar.add(copy);
        JButton move = new JButton();
        move.setIcon(getIcon(IconType.MOVE, IconStyle.COLORFUL));
        move.addActionListener(e -> issueMoveOperation());
        centerBar.add(move);
        container.add(centerBar, BorderLayout.CENTER);
    }

    private void openFindDialog() {
    }

    private void issueCopyOperation() {
        for (File sourceFile : activePane.getSelectedFiles()) {
            File targetFile = new File(passivePane.getWorkingDirectoryPath(), sourceFile.getName());
            executor.execute(new CopyOperation(sourceFile, targetFile).then(new RefreshOperation(passivePane)));
        }
    }

    private void issueMoveOperation() {
    }
}
