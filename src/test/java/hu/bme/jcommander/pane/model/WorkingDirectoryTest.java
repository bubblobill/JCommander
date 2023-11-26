package hu.bme.jcommander.pane.model;

import hu.bme.jcommander.filesystem.FileHandleBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkingDirectoryTest {

    private static boolean parentExisted;
    private static File parentDir;
    private static boolean childExisted;
    private static File childDir;
    private WorkingDirectory directory;

    @BeforeAll
    public static void createFiles() throws IOException {
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
    public static void deleteFiles() throws IOException {
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
    void testSetToRoot() {
        directory.resetToRoot();
        assertEquals("", directory.getAbsolutePath());
    }

    @Test
    void testSetToMountPoint() {
        File location = File.listRoots()[0];

        directory.setTo(new FileHandleBuilder().setFile(location).toFileHandle());
        assertEquals(location.getAbsolutePath(), directory.getAbsolutePath());
    }

    @Test
    void testSelectParent() {
        directory.setTo(new FileHandleBuilder(childDir).toFileHandle());
        directory.selectParent();
        assertEquals(parentDir.getAbsolutePath(), directory.getAbsolutePath());
    }

    @Test
    void testNavigation() {
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
