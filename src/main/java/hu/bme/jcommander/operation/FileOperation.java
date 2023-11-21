package hu.bme.jcommander.operation;

import java.io.File;

public abstract class FileOperation extends Operation {
    protected final File from;
    protected final File to;

    protected FileOperation(File from, File to) {
        this.from = from;
        this.to = to;
    }
}
