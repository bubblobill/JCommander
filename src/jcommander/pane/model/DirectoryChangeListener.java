package jcommander.pane.model;

import java.util.EventListener;

public interface DirectoryChangeListener extends EventListener {

    void directoryChanged(DirectoryChangedEvent e);
}
