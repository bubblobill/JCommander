package org.reverence.jcommander.filesystem;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import java.io.File;

public class FileHandleBuilder {

    private File file;

    /**
     * Constructs a builder.
     */
    public FileHandleBuilder() {
        this(null);
    }

    /**
     * Constructs a builder with an initial file.
     *
     * @param base the initial file
     */
    public FileHandleBuilder(File base) {
        setFile(base);
    }

    /**
     * Sets the builder to the given file.
     *
     * @param file the file
     * @return the builder
     */
    public FileHandleBuilder setFile(File file) {
        this.file = file;
        return this;
    }

    /**
     * Retrieves the final handle constructed.
     *
     * @return a handle
     */
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

    /**
     * Sets the builder to the current item's parent.
     *
     * @return the builder
     */
    public FileHandleBuilder parent() {
        file = file.getParentFile();
        return this;
    }
}
