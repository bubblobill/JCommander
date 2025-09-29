package org.reverence.jcommander.operation;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.pane.Controller;

public class RefreshOperation extends Operation {

    private final Controller refreshable;

    /**
     * Constructs a RefreshOperation with the specified {@code refreshable} controller.
     *
     * @param refreshable the {@code Controller} to be refreshed
     */
    public RefreshOperation(Controller refreshable) {
        this.refreshable = refreshable;
    }

    @Override
    public void run() {
        refreshable.refresh();
        failed = false;
    }
}
