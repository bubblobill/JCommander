package hu.bme.jcommander.bars;

import hu.bme.jcommander.pane.WorkPane;
import hu.bme.jcommander.settings.IconType;

import javax.swing.*;

import java.util.function.Function;
import java.util.function.Supplier;

import static hu.bme.jcommander.ResourceFactory.getIcon;

public class NavigationBarFacade {

    private final JToolBar navBar;

    private final JButton previous;
    private final JButton next;

    public NavigationBarFacade(WorkPane paneA, WorkPane paneB, Supplier<WorkPane> activePane) {
        navBar = new JToolBar(SwingConstants.HORIZONTAL);
        navBar.setFloatable(false);
        JButton refresh = new JButton();
        refresh.setFocusable(false);
        refresh.setIcon(getIcon(IconType.REFRESH));
        refresh.addActionListener(e -> {
            paneA.refresh();
            paneB.refresh();
        });
        navBar.add(refresh);
        previous = new JButton();
        previous.setFocusable(false);
        previous.setIcon(getIcon(IconType.LEFT));
        previous.addActionListener(e -> activePane.get().selectPrevious());
        navBar.add(previous);
        next = new JButton();
        next.setFocusable(false);
        next.setIcon(getIcon(IconType.RIGHT));
        next.addActionListener(e -> activePane.get().selectNext());
        navBar.add(next);
    }

    public JToolBar get() {
        return navBar;
    }

    public void updateHistoryButtons(boolean undo, boolean redo) {
        previous.setEnabled(undo);
        next.setEnabled(redo);
    }
}
