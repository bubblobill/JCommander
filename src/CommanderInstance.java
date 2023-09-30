import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CommanderInstance {

    private final JFrame frame;
    private WorkPane activePane;
    private final WorkPane paneA;
    private final WorkPane paneB;

    public CommanderInstance() {
        frame = new JFrame("JCommander");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        createMenuBar();
        createTopBar();

        paneA = new WorkPane();
        frame.add(paneA, BorderLayout.WEST);

        createCenterBar();

        paneB = new WorkPane();
        frame.add(paneB, BorderLayout.EAST);

        activePane = paneA; // by default, paneA is in focus

        frame.pack();
        frame.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("New Window"));
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }

    private void createTopBar() {
        JToolBar topBar = new JToolBar(JToolBar.HORIZONTAL);
        topBar.setFloatable(false);
        topBar.add(new JButton("Refresh"));
        topBar.add(new JButton("Find"));
        topBar.add(new JButton("Back"));
        topBar.add(new JButton("Forward"));
        frame.add(topBar, BorderLayout.NORTH);
    }

    private void createCenterBar() {
        JToolBar centerBar = new JToolBar(JToolBar.VERTICAL);
        centerBar.setFloatable(false);
        centerBar.add(new JButton("Copy"));
        centerBar.add(new JButton("Move"));
        frame.add(centerBar, BorderLayout.CENTER);
    }
}
