package jcommander.filesystem;

import jcommander.ResourceFactory;
import jcommander.settings.IconType;

import javax.swing.*;
import java.io.File;

public class DirectoryHandle extends FileHandle {

    public DirectoryHandle(File directory) {
        super(directory);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Argument must be a directory");
        }
    }

    @Override
    public String getName() {
        if (file.getParentFile() == null) {
            return file.getAbsolutePath();
        }
        return file.getName();
    }

    @Override
    public Handle[] getChildren() {
        File[] filesInDirectory = file.listFiles();
        // even though it technically should never be null, that's pretty much what our guard-clause is for,
        // but in some weird, hacky Windows-tested edge cases, it can indeed happen that a file is seemingly
        // a directory but one cannot access its contents

        if (filesInDirectory == null) {
            return new Handle[0];
        }

        Handle[] children = new Handle[filesInDirectory.length];
        for (int i = 0; i < filesInDirectory.length; i++) {
            children[i] = new FileHandleBuilder(filesInDirectory[i]).toFileHandle();
        }

        return children;
    }

    @Override
    public IconType getAssociatedIcon() {
        return IconType.DIRECTORY;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
