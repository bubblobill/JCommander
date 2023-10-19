package jcommander.history;

import java.util.EventListener;

public interface HistoryChangeListener extends EventListener {

    void historyChanged(HistoryChangedEvent e);
}
