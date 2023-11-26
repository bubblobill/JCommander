package hu.bme.jcommander.filesystem;

import hu.bme.jcommander.settings.IconType;

public interface Handle {

    /**
     * Retrieves the absolute path of the file.
     *
     * @return the absolute path
     */
    String getAbsolutePath();

    /**
     * Retrieves the name of the file.
     *
     * @return the name
     */
    String getName();

    /**
     * Retrieves a handle to the parent of the underlying file
     *
     * @return a handle
     */
    Handle getParent();

    /**
     * Retrieves the handles to the underlying file's children.
     *
     * @return an array of handles representing the handle's children if the handle is not a leaf,
     * and an empty array otherwise
     */
    Handle[] getChildren();

    /**
     * Retrieves the icon type associated with the kind of handle.
     *
     * @return the icon type
     */
    IconType getAssociatedIcon();

    /**
     * Tells whether the handle has (or capable of having) children.
     *
     * @return true if it can have children, false otherwise
     */
    boolean isLeaf();

    /**
     * Attempts to rename the underlying file.
     *
     * @param to the new name
     * @throws RenamingException if the underlying file is not a regular file or directory,
     *                           but a mount point or the system root
     */
    void rename(String to) throws RenamingException;
}
