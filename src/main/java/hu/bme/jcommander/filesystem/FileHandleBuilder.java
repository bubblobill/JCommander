package hu.bme.jcommander.filesystem;

import java.io.File;

public class FileHandleBuilder {

    private File file;

    public FileHandleBuilder() {
        this(null);
    }

    public FileHandleBuilder(File base) {
        setFile(base);
    }

    public FileHandleBuilder setFile(File file) {
        this.file = file;
        return this;
    }

    public Handle toFileHandle() {
        if (file == null) {
            return new RootHandle();
        }

        if (file.getParentFile() == null) {
            return new MountHandle(file);
        }

        if (file.isDirectory()) {
            return new DirectoryHandle(file);
        }

        return new RegularFileHandle(file);
    }

    public FileHandleBuilder parent() {
        file = file.getParentFile();
        return this;
    }
}
