import javax.swing.*;
import java.awt.*;

public class CommanderInstance {

    public static final int ICON_SIZE = 32;

    // TODO: move these to some other class
    private static ImageIcon getSquareIcon(String filename) {
        Image image = new ImageIcon("images/" + filename).getImage();
        return new ImageIcon(image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
    }

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
        createTopBar(frame);

        paneA = new WorkPane();
        frame.add(paneA, BorderLayout.WEST);

        createCenterBar(frame);

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

    private void createTopBar(Container container) {
        JToolBar topBar = new JToolBar(JToolBar.HORIZONTAL);
        topBar.setFloatable(false);
        JButton refresh = new JButton();
        refresh.setIcon(getSquareIcon("refresh.png"));
        refresh.addActionListener(e -> {
            paneA.refreshTree();
            paneB.refreshTree();
        });
        topBar.add(refresh);
        JButton find = new JButton();
        find.setIcon(getSquareIcon("find.png"));
        find.addActionListener(e -> openFindDialog());
        topBar.add(find);
        JButton previous = new JButton();
        previous.setIcon(getSquareIcon("left.png"));
        previous.addActionListener(e -> activePane.selectPrevious());
        topBar.add(previous);
        JButton next = new JButton();
        next.setIcon(getSquareIcon("right.png"));
        next.addActionListener(e -> activePane.selectNext());
        topBar.add(next);
        container.add(topBar, BorderLayout.NORTH);
    }

    private void openFindDialog() {
    }

    private void createCenterBar(Container container) {
        JToolBar centerBar = new JToolBar(JToolBar.VERTICAL);
        centerBar.setFloatable(false);
        JButton copy = new JButton();
        copy.setIcon(getSquareIcon("copy.png"));
        copy.addActionListener(e -> issueCopyOperation());
        centerBar.add(copy);
        JButton move = new JButton();
        move.setIcon(getSquareIcon("move.png"));
        move.addActionListener(e -> issueMoveOperation());
        centerBar.add(move);
        container.add(centerBar, BorderLayout.CENTER);
    }

    private void issueCopyOperation() {
    }

    private void issueMoveOperation() {
    }
}
