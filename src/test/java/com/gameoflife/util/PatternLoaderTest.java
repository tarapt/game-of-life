package com.gameoflife.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PatternLoader utility class.
 * Tests pattern file loading with various scenarios.
 *
 * @author TaraPrasad
 */
class PatternLoaderTest {

    @TempDir
    Path tempDir;

    private boolean[][] cells;
    private File testFile;

    @BeforeEach
    void setUp() {
        cells = new boolean[20][20];
    }

    @Test
    void testLoadSimplePattern() throws IOException {
        // Create a simple pattern file
        testFile = tempDir.resolve("test.life").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("#Simple pattern\n");
            writer.write("***\n");
            writer.write("*.*\n");
            writer.write("***\n");
        }

        PatternLoader.loadPattern(testFile, cells, 5, 5);

        // Verify pattern was loaded
        assertTrue(cells[5][5]);
        assertTrue(cells[5][6]);
        assertTrue(cells[5][7]);
        assertTrue(cells[6][5]);
        assertFalse(cells[6][6]);
        assertTrue(cells[6][7]);
        assertTrue(cells[7][5]);
        assertTrue(cells[7][6]);
        assertTrue(cells[7][7]);
    }

    @Test
    void testLoadPatternWithOCharacter() throws IOException {
        // Test with 'O' character for alive cells
        testFile = tempDir.resolve("test.life").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("#Pattern with O\n");
            writer.write("OOO\n");
        }

        PatternLoader.loadPattern(testFile, cells, 0, 0);

        assertTrue(cells[0][0]);
        assertTrue(cells[0][1]);
        assertTrue(cells[0][2]);
    }

    @Test
    void testLoadPatternWithWrapping() throws IOException {
        // Test toroidal wrapping
        testFile = tempDir.resolve("test.life").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("#Wrapping test\n");
            writer.write("***\n");
        }

        PatternLoader.loadPattern(testFile, cells, 0, 18);

        // Should wrap around
        assertTrue(cells[0][18]);
        assertTrue(cells[0][19]);
        assertTrue(cells[0][0]); // Wrapped
    }

    @Test
    void testLoadPatternWithNullFile() {
        assertThrows(IllegalArgumentException.class, () ->
            PatternLoader.loadPattern(null, cells, 0, 0)
        );
    }

    @Test
    void testLoadPatternWithNullCells() {
        testFile = tempDir.resolve("test.life").toFile();
        assertThrows(IllegalArgumentException.class, () ->
            PatternLoader.loadPattern(testFile, null, 0, 0)
        );
    }

    @Test
    void testLoadPatternWithEmptyCells() {
        testFile = tempDir.resolve("test.life").toFile();
        boolean[][] empty = new boolean[0][0];
        assertThrows(IllegalArgumentException.class, () ->
            PatternLoader.loadPattern(testFile, empty, 0, 0)
        );
    }

    @Test
    void testLoadPatternWithNonExistentFile() {
        testFile = new File(tempDir.toFile(), "nonexistent.life");
        assertThrows(IOException.class, () ->
            PatternLoader.loadPattern(testFile, cells, 0, 0)
        );
    }

    @Test
    void testLoadEmptyFile() throws IOException {
        testFile = tempDir.resolve("empty.life").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("#Header only\n");
        }

        assertThrows(IOException.class, () ->
            PatternLoader.loadPattern(testFile, cells, 0, 0)
        );
    }

    @Test
    void testIsValidPatternFileWithValidFile() throws IOException {
        testFile = tempDir.resolve("valid.life").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("#Valid pattern\n");
            writer.write("***\n");
        }

        assertTrue(PatternLoader.isValidPatternFile(testFile));
    }

    @Test
    void testIsValidPatternFileWithInvalidFile() throws IOException {
        testFile = tempDir.resolve("invalid.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("No pattern characters here\n");
        }

        assertFalse(PatternLoader.isValidPatternFile(testFile));
    }

    @Test
    void testIsValidPatternFileWithNull() {
        assertFalse(PatternLoader.isValidPatternFile(null));
    }

    @Test
    void testIsValidPatternFileWithNonExistent() {
        testFile = new File(tempDir.toFile(), "nonexistent.life");
        assertFalse(PatternLoader.isValidPatternFile(testFile));
    }

    @Test
    void testLoadLargePattern() throws IOException {
        // Test with a larger pattern
        testFile = tempDir.resolve("large.life").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("#Large pattern\n");
            for (int i = 0; i < 10; i++) {
                writer.write("**********\n");
            }
        }

        PatternLoader.loadPattern(testFile, cells, 0, 0);

        // Verify pattern was loaded
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(cells[i][j]);
            }
        }
    }

    @Test
    void testLoadPatternWithMixedCharacters() throws IOException {
        testFile = tempDir.resolve("mixed.life").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("#Mixed characters\n");
            writer.write("*.O\n");
            writer.write("O*.\n");
        }

        PatternLoader.loadPattern(testFile, cells, 0, 0);

        assertTrue(cells[0][0]);  // *
        assertFalse(cells[0][1]); // .
        assertTrue(cells[0][2]);  // O
        assertTrue(cells[1][0]);  // O
        assertTrue(cells[1][1]);  // *
        assertFalse(cells[1][2]); // .
    }
}
