package hu.bme.jcommander.operation;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class DeleteOperationTest extends FileSystemTest {

    @Test
    void testDeleteNothing() {
        Path[] paths = new Path[0];
        DeleteOperation op = new DeleteOperation(paths);

        assumeFilesExist(paths);
        op.run();
        assertFalse(op.isFailed());
        assertFilesNotExist(paths);
    }

    @Test
    void testDeleteSingle() {
        Path[] paths = {srcD1};
        DeleteOperation op = new DeleteOperation(paths);

        assumeFilesExist(paths);
        op.run();
        assertFalse(op.isFailed());
        assertFilesNotExist(paths);
    }

    @Test
    void testDeleteMultiple() {
        Path[] paths = {srcD1, srcD2, srcD3};
        DeleteOperation op = new DeleteOperation(paths);

        assumeFilesExist(paths);
        op.run();
        assertFalse(op.isFailed());
        assertFilesNotExist(paths);
    }

    private static void assumeFilesExist(Path[] paths) {
        for (Path path : paths) {
            assumeTrue(Files.exists(path));
        }
    }

    private static void assertFilesNotExist(Path[] paths) {
        for (Path path : paths) {
            assertFalse(Files.exists(path));
        }
    }
}
