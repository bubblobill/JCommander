package jcommander.filesystem;

import jcommander.settings.IconType;

import java.io.File;

public class MountHandle extends DirectoryHandle {
    
    public MountHandle(File mount) {
        super(mount); // this is quirky
        // only because of inheritance are we obliged to call super first, even if the parameter turns out to be wrong
        if (mount.getParentFile() != null) {
            throw new IllegalArgumentException("Argument must be a mount point");
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
        throw new RenamingException("Can't rename mount point");
    }
}
