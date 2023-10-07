package jcommander.history;

import java.util.EventListener;

public interface HistoryChangeListener<T> extends EventListener {

    void historyChanged(HistoryChangedEvent<T> e);
}
