package hu.bme.jcommander.operation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MoveOperation extends FileOperation {

    /**
     * Moves a file or directory from one location to another location.
     *
     * @param from the source location represented as a path
     * @param to   the destination location represented as a path
     */
    public MoveOperation(Path from, Path to) {
        super(from, to);
    }

    @Override
    public void run() {
        try {
            Files.move(from, to);
        } catch (SecurityException | IOException ignored) {
            failed = true;
        }
    }
}
