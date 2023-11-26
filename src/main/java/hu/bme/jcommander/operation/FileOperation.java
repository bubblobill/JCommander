package hu.bme.jcommander.operation;

import java.nio.file.Path;

public abstract class FileOperation extends Operation {
    protected final Path from;
    protected final Path to;

    /**
     * An operation working with two operands with each being a path representing a file or directory.
     *
     * @param from the source location
     * @param to   the destination location
     */
    protected FileOperation(Path from, Path to) {
        this.from = from;
        this.to = to;
    }
}
