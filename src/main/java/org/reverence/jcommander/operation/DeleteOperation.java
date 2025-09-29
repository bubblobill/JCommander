package org.reverence.jcommander.operation;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DeleteOperation extends Operation {

    private final Path[] filesToDelete;

    /**
     * Constructs a DeleteOperation with the specified array of files to be deleted.
     *
     * @param selectedFiles the array of paths (corresponding to files or directories) to be deleted
     */
    public DeleteOperation(Path[] selectedFiles) {
        this.filesToDelete = selectedFiles;
    }

    @Override
    public void run() {
        for (Path file : filesToDelete) {
            try {
                Files.delete(file);
            } catch (IOException e) {
                failed = true;
            }
        }
    }
}
