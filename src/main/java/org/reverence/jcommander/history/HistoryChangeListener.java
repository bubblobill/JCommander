package org.reverence.jcommander.history;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import java.util.EventListener;

public interface HistoryChangeListener extends EventListener {

    /**
     * A history change handler that gets called whenever an object's history stacks change or the object is set.
     *
     * @param event the event
     */
    void historyChanged(HistoryChangedEvent event);
}
