package hu.bme.jcommander.pane.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import hu.bme.jcommander.filesystem.FileHandleBuilder;
import hu.bme.jcommander.filesystem.RootHandle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WorkingDirectoryTest {

    private boolean parentExisted;
    private File parentDir;
    private boolean childExisted;
    private File childDir;
    private WorkingDirectory directory;

    @BeforeAll
    public void createFiles() throws IOException {
        String userHome = System.getProperty("user.home");

        parentDir = new File(userHome + File.separator + "dir1");
        parentExisted = parentDir.exists();
        if (!parentExisted) {
            System.out.println(parentDir.getAbsolutePath());
            Files.createDirectory(parentDir.toPath());
        }

        childDir = new File(userHome + File.separator + "dir1" + File.separator + "dir2");
        childExisted = childDir.exists();
        if (!childExisted) {
            Files.createDirectory(childDir.toPath());
        }
    }

    @AfterAll
    public void deleteFiles() throws IOException {
        if (!childExisted) {
            Files.delete(childDir.toPath());
        }

        if (!parentExisted) {
            Files.delete(parentDir.toPath());
        }
    }

    @BeforeEach
    public void init() {
        directory = new WorkingDirectory();
    }

    @Test
    public void testInitialDirectory() {
        assertEquals("", directory.getAbsolutePath());
    }

    @Test
    public void testSetToRoot() {
        directory.setTo(new RootHandle());
        assertEquals("", directory.getAbsolutePath());
    }

    @Test
    public void testSetToMountPoint() {
        File location = File.listRoots()[0];

        directory.setTo(new FileHandleBuilder().setFile(location).toFileHandle());
        assertEquals(location.getAbsolutePath(), directory.getAbsolutePath());
    }

    @Test
    public void testSelectParent() {
        directory.setTo(new FileHandleBuilder(childDir).toFileHandle());
        directory.selectParent();
        assertEquals(parentDir.getAbsolutePath(), directory.getAbsolutePath());
    }

    @Test
    public void testNavigation() {
        directory.setTo(new FileHandleBuilder(parentDir).toFileHandle());
        directory.setTo(new FileHandleBuilder(childDir).toFileHandle());
        directory.selectParent();

        directory.selectPrevious();
        assertEquals(childDir.getAbsolutePath(), directory.getAbsolutePath());
        directory.selectPrevious();
        assertEquals(parentDir.getAbsolutePath(), directory.getAbsolutePath());

        directory.selectNext();
        assertEquals(childDir.getAbsolutePath(), directory.getAbsolutePath());
        directory.selectNext();
        assertEquals(parentDir.getAbsolutePath(), directory.getAbsolutePath());
    }
}
