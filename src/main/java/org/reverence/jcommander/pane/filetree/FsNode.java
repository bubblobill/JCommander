package org.reverence.jcommander.pane.filetree;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.filesystem.Handle;

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
