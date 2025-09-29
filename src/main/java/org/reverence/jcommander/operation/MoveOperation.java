package org.reverence.jcommander.operation;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
            Files.move(from, to, REPLACE_EXISTING);
        } catch (SecurityException | IOException ignored) {
            failed = true;
        }
    }
}
