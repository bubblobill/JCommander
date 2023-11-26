package hu.bme.jcommander.operation;

import hu.bme.jcommander.pane.Controller;

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
