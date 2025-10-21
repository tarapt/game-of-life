package com.gameoflife.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for loading Game of Life patterns from files.
 * Supports .life file format with '*' or 'O' representing alive cells.
 *
 * @author TaraPrasad
 */
public final class PatternLoader {
    private static final Logger LOGGER = Logger.getLogger(PatternLoader.class.getName());

    // Prevent instantiation
    private PatternLoader() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Loads a pattern from a file into a cell grid.
     *
     * @param file File to load pattern from
     * @param cells Target cell grid to populate
     * @param originRow Starting row for pattern placement
     * @param originColumn Starting column for pattern placement
     * @throws IOException If file cannot be read
     * @throws IllegalArgumentException If file format is invalid
     */
    public static void loadPattern(File file, boolean[][] cells, int originRow, int originColumn)
            throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if (cells == null || cells.length == 0 || cells[0].length == 0) {
            throw new IllegalArgumentException("Cell grid cannot be null or empty");
        }
        if (!file.exists()) {
            throw new IOException("File does not exist: " + file.getAbsolutePath());
        }
        if (!file.canRead()) {
            throw new IOException("Cannot read file: " + file.getAbsolutePath());
        }

        List<String> lines = readPatternLines(file);
        if (lines.isEmpty()) {
            throw new IOException("Pattern file is empty");
        }

        applyPatternToGrid(lines, cells, originRow, originColumn);
        LOGGER.log(Level.INFO, "Successfully loaded pattern from: {0}", file.getName());
    }

    /**
     * Reads pattern lines from file, skipping the first line (header/comment).
     *
     * @param file File to read from
     * @return List of pattern lines
     * @throws IOException If file cannot be read
     */
    private static List<String> readPatternLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip first line (usually a comment or header)
            String firstLine = reader.readLine();
            if (firstLine == null) {
                return lines;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

    /**
     * Applies pattern lines to the cell grid with wrapping.
     *
     * @param lines Pattern lines
     * @param cells Target cell grid
     * @param originRow Starting row
     * @param originColumn Starting column
     */
    private static void applyPatternToGrid(List<String> lines, boolean[][] cells,
                                           int originRow, int originColumn) {
        int rows = cells.length;
        int columns = cells[0].length;
        int currentRow = originRow;

        for (String line : lines) {
            if (line == null) {
                continue;
            }

            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                int column = (originColumn + i) % columns;

                // '*' or 'O' represents alive cells
                if (ch == '*' || ch == 'O') {
                    cells[currentRow][column] = true;
                } else {
                    cells[currentRow][column] = false;
                }
            }

            currentRow = (currentRow + 1) % rows;
        }
    }

    /**
     * Validates if a file appears to be a valid pattern file.
     *
     * @param file File to validate
     * @return true if file appears valid, false otherwise
     */
    public static boolean isValidPatternFile(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null && lineCount < 10) {
                lineCount++;
                // Look for pattern characters
                if (line.contains("*") || line.contains("O") || line.contains(".")) {
                    return true;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error validating pattern file", e);
            return false;
        }

        return false;
    }
}
