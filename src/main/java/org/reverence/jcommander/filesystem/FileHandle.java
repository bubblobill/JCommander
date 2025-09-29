package org.reverence.jcommander.filesystem;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.settings.I18n;

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
            throw new RenamingException(I18n.getText("error.path.change"));
        }

        if (file.renameTo(newDirectory)) {
            file = newDirectory;
        } else {
            throw new RenamingException(I18n.getText("error.rename.fail", "noun.file"));
        }
    }
}
