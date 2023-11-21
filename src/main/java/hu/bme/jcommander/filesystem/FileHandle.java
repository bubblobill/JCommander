package hu.bme.jcommander.filesystem;

import java.io.File;

public abstract class FileHandle implements Handle {

    protected File file;

    protected FileHandle(File file) {
        this.file = file;
    }

    @Override
    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    @Override
    public Handle getParent() {
        return new FileHandleBuilder(file).parent().toFileHandle();
    }

    @Override
    public void rename(String to) throws RenamingException {
        File newDirectory = new File(file.getParent(), to);
        if (!newDirectory.getParent().equals(file.getParent())) {
            throw new RenamingException("New file must not change path");
        }

        if (file.renameTo(newDirectory)) {
            file = newDirectory;
        } else {
            throw new RenamingException("Couldn't rename file");
        }
    }
}
