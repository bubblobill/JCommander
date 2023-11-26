package hu.bme.jcommander.operation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NewDirectoryOperation extends Operation {

    private final Path path;

    /**
     * Constructs a new directory creation operation with the specified path.
     *
     * @param path the path where the new directory will be created
     */
    public NewDirectoryOperation(Path path) {
        this.path = path;
    }

    /**
     * Executes the operation, creating a new directory at the specified path.
     */
    @Override
    public void run() {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            failed = true;
        }
    }
}
