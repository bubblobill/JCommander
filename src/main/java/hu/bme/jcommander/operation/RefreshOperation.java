package hu.bme.jcommander.operation;

import hu.bme.jcommander.pane.Controller;

public class RefreshOperation extends Operation {

    private final Controller refreshable;

    public RefreshOperation(Controller refreshable) {
        this.refreshable = refreshable;
    }

    @Override
    public void run() {
        refreshable.refresh();
        failed = false;
    }
}
