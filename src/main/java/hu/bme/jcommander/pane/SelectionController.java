package hu.bme.jcommander.pane;

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
