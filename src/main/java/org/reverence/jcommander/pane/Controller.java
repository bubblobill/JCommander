package org.reverence.jcommander.pane;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
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
