package org.reverence.jcommander.filesystem;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.settings.I18n;
import org.reverence.jcommander.settings.IconType;

import java.io.File;

public class MountHandle extends DirectoryHandle {

    /**
     * Constructs a new MountHandle with the specified mount point.
     *
     * @param mount The mount point to be represented by this handle.
     * @throws IllegalArgumentException if the provided mount point has a non-null parent,
     *                                  indicating it is not a mount point.
     */
    public MountHandle(File mount) {
        super(mount); // this is quirky
        // only because of inheritance are we obliged to call super first, even if the parameter turns out to be wrong
        if (mount.getParentFile() != null) {
            throw new IllegalArgumentException(I18n.getText("error.not.mount.point"));
        }
    }

    @Override
    public String getName() {
        return getAbsolutePath(); // for mount points, the name shall be equal to the absolute path
    }

    @Override
    public IconType getAssociatedIcon() {
        return IconType.DISK;
    }

    @Override
    public Handle getParent() {
        return new RootHandle();
    }

    @Override
    public void rename(String to) throws RenamingException {
        throw new RenamingException(I18n.getText("error.rename.fail", "noun.mount.point"));
    }
}
