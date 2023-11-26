package hu.bme.jcommander.operation;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class CopyOperationTest extends FileSystemTest {

    @Test
    void testCopyFileWithoutOverwrite() throws IOException {
        CopyOperation op = new CopyOperation(srcF1, dstF1);

        assumeTrue(Files.exists(srcF1));
        assumeFalse(Files.exists(dstF1));

        op.run();
        assertFalse(op.isFailed());

        assertTrue(Files.exists(srcF1));
        assertTrue(Files.exists(dstF1));

        assertEquals(Files.readString(srcF1), Files.readString(dstF1));
    }

    @Test
    void testCopyFileWithOverwrite() throws IOException {
        CopyOperation op = new CopyOperation(srcF1, dstF2);

        assumeTrue(Files.exists(srcF1));
        assumeTrue(Files.exists(dstF2));

        op.run();
        assertFalse(op.isFailed());

        assertTrue(Files.exists(srcF1));
        assertTrue(Files.exists(dstF2));

        assertEquals(Files.readString(srcF1), Files.readString(dstF2));
    }

    @Test
    void testCopyDirectory() {
        CopyOperation op = new CopyOperation(srcD1, dstD1);

        assumeTrue(Files.exists(srcD1));
        assumeFalse(Files.exists(dstD1));

        op.run();
        assertFalse(op.isFailed());

        assertTrue(Files.exists(srcD1));
        assertTrue(Files.exists(dstD1));
    }
}
