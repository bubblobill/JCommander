package jcommander.operation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NewDirectoryOperation extends Operation {

    private final String location;
    public NewDirectoryOperation(String location) {
        this.location = location;
    }

    @Override
    public void run() {
        try {
            Files.createDirectory(Path.of(location));
        } catch (IOException e) {
            failed = true;
        }
    }
}
