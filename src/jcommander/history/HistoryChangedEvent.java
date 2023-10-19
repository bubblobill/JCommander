package jcommander.history;

import java.util.EventObject;

public class HistoryChangedEvent extends EventObject {

    private final boolean undo;
    private final boolean redo;

    public HistoryChangedEvent(Object source, boolean canUndo, boolean canRedo) {
        super(source);
        this.undo = canUndo;
        this.redo = canRedo;
    }

    public boolean canUndo() {
        return undo;
    }

    public boolean canRedo() {
        return redo;
    }
}
