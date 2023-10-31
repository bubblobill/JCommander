package jcommander.operation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CopyOperation extends FileOperation {

    public CopyOperation(File from, File to) {
        super(from, to);
    }

    @Override
    public void run() {
        try {
            Files.copy(from.toPath(), to.toPath());
        } catch (IOException ignored) {
            failed = true;
        }
    }
}
