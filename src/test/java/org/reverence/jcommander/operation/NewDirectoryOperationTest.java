package org.reverence.jcommander.operation;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

class NewDirectoryOperationTest extends FileSystemTest {

    @Test
    void testNewDirectory() {
        NewDirectoryOperation op = new NewDirectoryOperation(dstD1);

        assumeFalse(Files.exists(dstD1));

        op.run();

        assertTrue(Files.exists(dstD1));
        assertTrue(Files.isDirectory(dstD1));
    }
}
