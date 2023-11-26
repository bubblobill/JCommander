package hu.bme.jcommander.pane;

import javax.swing.*;

public interface Controller {

    /**
     * Retrieves the controller's view as a component.
     *
     * @return the view
     */
    JComponent component();

    /**
     * Refreshes the controller's view.
     */
    void refresh();
}
