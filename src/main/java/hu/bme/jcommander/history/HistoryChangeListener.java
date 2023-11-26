package hu.bme.jcommander.history;

import java.util.EventListener;

public interface HistoryChangeListener extends EventListener {

    /**
     * A history change handler that gets called whenever an object's history stacks change or the object is set.
     *
     * @param event the event
     */
    void historyChanged(HistoryChangedEvent event);
}
