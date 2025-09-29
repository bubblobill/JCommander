package org.reverence.jcommander.operation;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemTest {

    protected FileSystem fs;
    protected Path srcDir;
    protected Path srcF1;
    protected Path srcD1;
    protected Path srcD2;
    protected Path srcD3;
    protected Path dstDir;
    protected Path dstF1;
    protected Path dstF2;
    protected Path dstD1;

    @BeforeEach
    void init() throws IOException {
        fs = Jimfs.newFileSystem(Configuration.unix());

        srcDir = fs.getPath("/src");
        Files.createDirectory(srcDir);

        srcF1 = fs.getPath("/src/F1.txt");
        Files.writeString(srcF1, "F1 from src");
        srcD1 = fs.getPath("/src/D1/");
        Files.createDirectory(srcD1);
        srcD2 = fs.getPath("/src/D2/");
        Files.createDirectory(srcD2);
        srcD3 = fs.getPath("/src/D3/");
        Files.createDirectory(srcD3);

        dstDir = fs.getPath("/dst");
        Files.createDirectory(dstDir);

        dstF1 = fs.getPath("/dst/F1.txt");
        dstF2 = fs.getPath("/dst/F2.txt");
        Files.writeString(dstF2, "F2 from dst");
        dstD1 = fs.getPath("/dst/D1/");
    }
}
