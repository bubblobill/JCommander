package org.reverence.jcommander.pane;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import java.io.File;

/**
 * Defines an interface for controllers that support file selections.
 */
public interface SelectionController extends Controller {

    /**
     * Retrieves the currently selected files.
     *
     * @return An array of selected files.
     */
    File[] getSelectedFiles();
}
