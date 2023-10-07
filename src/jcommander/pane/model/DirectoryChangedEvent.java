package jcommander.pane.model;

import java.util.EventObject;

public class DirectoryChangedEvent extends EventObject {

    public DirectoryChangedEvent(Object source) {
        super(source);
    }
}
