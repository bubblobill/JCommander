package hu.bme.jcommander.bars;

import hu.bme.jcommander.operation.CopyOperation;
import hu.bme.jcommander.operation.MoveOperation;
import hu.bme.jcommander.operation.OperationExecutor;
import hu.bme.jcommander.pane.WorkPane;
import hu.bme.jcommander.settings.IconType;

import javax.swing.*;

import java.awt.*;
import java.util.function.Supplier;

import static hu.bme.jcommander.ResourceFactory.getIcon;

public class ToolBarFacade {

    private final JToolBar centerBar;

    public ToolBarFacade(Component parent, OperationExecutor executor, Supplier<WorkPane> activePane, Supplier<WorkPane> passivePane) {
        centerBar = new JToolBar(SwingConstants.VERTICAL);
        centerBar.setFloatable(false);

        JButton newDir = new JButton();
        newDir.setFocusable(false);
        newDir.setIcon(getIcon(IconType.NEW_DIRECTORY));
        newDir.addActionListener(e -> executor.issueNewDirectoryOperation(activePane.get()));
        centerBar.add(newDir);

        JButton delete = new JButton();
        delete.setFocusable(false);
        delete.setIcon(getIcon(IconType.DELETE));
        delete.addActionListener(e -> executor.issueDeleteOperation(activePane.get(), parent, activePane.get().getSelectedFiles()));
        centerBar.add(delete);

        JButton copy = new JButton();
        copy.setFocusable(false);
        copy.setIcon(getIcon(IconType.COPY));
        copy.addActionListener(e -> executor.issueFileOperation(activePane.get(), passivePane.get(), CopyOperation.class));
        centerBar.add(copy);

        JButton move = new JButton();
        move.setFocusable(false);
        move.setIcon(getIcon(IconType.MOVE));
        move.addActionListener(e -> executor.issueFileOperation(activePane.get(), passivePane.get(), MoveOperation.class));
        centerBar.add(move);
    }

    public JToolBar get() {
        return centerBar;
    }
}
