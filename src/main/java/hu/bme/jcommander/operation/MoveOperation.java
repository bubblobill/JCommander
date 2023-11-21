package hu.bme.jcommander.operation;

import java.io.File;

public class MoveOperation extends FileOperation {

    public MoveOperation(File from, File to) {
        super(from, to);
    }

    @Override
    public void run() {
        try {
            failed = !from.renameTo(to);
        } catch (SecurityException ignored) {
            failed = true;
        }
    }
}
