package jcommander.pane.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkingDirectoryTest {

    private WorkingDirectory wd;

    @BeforeEach
    public void setUp() {
        wd = new WorkingDirectory();
    }

    @Test
    public void testInitialValue() {
        assertEquals("", wd.getAbsolutePath(), "did not initialize to root");
    }

    @Test
    public void testSetToRoot() {
        wd.set(null);
        assertEquals("", wd.getAbsolutePath(), "was not set to root");
        wd.selectParent();
        assertEquals("", wd.getAbsolutePath(), "root's parent was not root");
    }

    @Test
    public void testSetToMountPoint() {
        File firstMount = File.listRoots()[0];
        wd.set(firstMount);
        assertEquals(firstMount.getAbsolutePath(), wd.getAbsolutePath(), "failed to set to mount point");
        wd.selectParent();
        assertEquals("", wd.getAbsolutePath(), "mount point's parent was not root");
    }
}
