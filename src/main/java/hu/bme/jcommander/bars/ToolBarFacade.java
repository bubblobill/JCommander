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

    /**
     * Constructs a ToolBarFacade with the specified parent component,
     * OperationExecutor, and suppliers for active and passive WorkPanes.
     *
     * @param parent      the parent component for dialogs
     * @param executor    the OperationExecutor for handling file operations
     * @param activePane  a supplier providing the currently active WorkPane
     * @param passivePane a supplier providing the currently passive WorkPane
     */
    public ToolBarFacade(Component parent, OperationExecutor executor, Supplier<WorkPane> activePane, Supplier<WorkPane> passivePane) {
        centerBar = new JToolBar(SwingConstants.VERTICAL);
        centerBar.setFloatable(false);

        // New Directory button
        JButton newDir = new JButton();
        newDir.setFocusable(false);
        newDir.setIcon(getIcon(IconType.NEW_DIRECTORY));
        newDir.addActionListener(e -> executor.issueNewDirectoryOperation(activePane.get()));
        centerBar.add(newDir);

        // Delete button
        JButton delete = new JButton();
        delete.setFocusable(false);
        delete.setIcon(getIcon(IconType.DELETE));
        delete.addActionListener(e -> executor.issueDeleteOperation(activePane.get(), parent, activePane.get().getSelectedFiles()));
        centerBar.add(delete);

        // Copy button
        JButton copy = new JButton();
        copy.setFocusable(false);
        copy.setIcon(getIcon(IconType.COPY));
        copy.addActionListener(e -> executor.issueFileOperation(activePane.get(), passivePane.get(), CopyOperation.class));
        centerBar.add(copy);

        // Move button
        JButton move = new JButton();
        move.setFocusable(false);
        move.setIcon(getIcon(IconType.MOVE));
        move.addActionListener(e -> executor.issueFileOperation(activePane.get(), passivePane.get(), MoveOperation.class));
        centerBar.add(move);
    }

    /**
     * Retrieves the toolbar as a component.
     *
     * @return the toolbar
     */
    public JToolBar get() {
        return centerBar;
    }
}
