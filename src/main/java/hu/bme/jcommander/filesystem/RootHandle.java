package hu.bme.jcommander.filesystem;

import hu.bme.jcommander.settings.IconType;

import java.io.File;

public class RootHandle implements Handle {

    private final Handle[] children;

    /**
     * Constructs a RootHandle representing the system root in a file system.
     */
    public RootHandle() {
        File[] mountPoints = File.listRoots();
        children = new Handle[mountPoints.length];

        for (int i = 0; i < mountPoints.length; i++) {
            children[i] = new MountHandle(mountPoints[i]);
        }
    }

    @Override
    public String getAbsolutePath() {
        return ""; // The root's path is an empty string.
    }

    @Override
    public String getName() {
        return "This PC";
    }

    @Override
    public Handle getParent() {
        return null; // The root has no parents.
    }

    @Override
    public Handle[] getChildren() {
        return children;
    }

    @Override
    public IconType getAssociatedIcon() {
        return IconType.COMPUTER;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public void rename(String to) throws RenamingException {
        throw new RenamingException("Can't rename root");
    }
}
