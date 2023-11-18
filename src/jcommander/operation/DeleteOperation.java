package jcommander.operation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DeleteOperation extends Operation {

    private final File[] filesToDelete;

    public DeleteOperation(File[] selectedFiles) {
        this.filesToDelete = selectedFiles;
    }

    @Override
    public void run() {
        for (File file : filesToDelete) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                failed = true;
            }
        }
    }
}
