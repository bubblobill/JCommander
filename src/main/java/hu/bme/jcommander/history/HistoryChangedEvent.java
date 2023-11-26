package hu.bme.jcommander.history;

import java.util.EventObject;

public class HistoryChangedEvent extends EventObject {

    private final boolean undo;
    private final boolean redo;

    /**
     * Constructs a HistoryChangedEvent with the specified source
     * and information about the availability of undo and redo actions.
     *
     * @param source  the source object that triggered the event
     * @param canUndo indicates whether an undo operation is available
     * @param canRedo indicates whether a redo operation is available
     */
    public HistoryChangedEvent(Object source, boolean canUndo, boolean canRedo) {
        super(source);
        this.undo = canUndo;
        this.redo = canRedo;
    }

    /**
     * Indicates whether an undo operation can be performed on the associated object.
     *
     * @return true if an undo operation can be performed, false otherwise
     */
    public boolean canUndo() {
        return undo;
    }

    /**
     * Indicates whether a redo operation can be performed on the associated object.
     *
     * @return true if a redo operation can be performed, false otherwise
     */
    public boolean canRedo() {
        return redo;
    }
}
