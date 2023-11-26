package hu.bme.jcommander.operation;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class MoveOperationTest extends FileSystemTest {

    @Test
    void testMoveFileWithoutOverwrite() throws IOException {
        MoveOperation op = new MoveOperation(srcF1, dstF1);

        assumeTrue(Files.exists(srcF1));
        assumeFalse(Files.exists(dstF1));

        String srcF1Content = Files.readString(srcF1);

        op.run();
        assertFalse(op.isFailed());

        assertFalse(Files.exists(srcF1));
        assertTrue(Files.exists(dstF1));

        String dstF1Content = Files.readString(dstF1);

        assertEquals(srcF1Content, dstF1Content);
    }

    @Test
    void testMoveFileWithOverwrite() throws IOException {
        MoveOperation op = new MoveOperation(srcF1, dstF2);

        assumeTrue(Files.exists(srcF1));
        assumeTrue(Files.exists(dstF2));

        String srcF1Content = Files.readString(srcF1);

        op.run();
        assertFalse(op.isFailed());

        assertFalse(Files.exists(srcF1));
        assertTrue(Files.exists(dstF2));

        String dstF2Content = Files.readString(dstF2);

        assertEquals(srcF1Content, dstF2Content);
    }

    @Test
    void testMoveDirectory() {
        MoveOperation op = new MoveOperation(srcD1, dstD1);

        assumeTrue(Files.exists(srcD1));
        assumeFalse(Files.exists(dstD1));

        op.run();
        assertFalse(op.isFailed());

        assertFalse(Files.exists(srcD1));
        assertTrue(Files.exists(dstD1));
    }
}
