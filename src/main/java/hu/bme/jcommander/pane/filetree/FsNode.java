package hu.bme.jcommander.pane.filetree;

import hu.bme.jcommander.filesystem.Handle;

/**
 * Represents a concrete file, directory, mount point or system root of a file system as a node in a TreeModel.
 */
public interface FsNode {

    /**
     * Retrieves the underlying file's Handle
     *
     * @return the Handle
     */
    Handle getHandle();
}
