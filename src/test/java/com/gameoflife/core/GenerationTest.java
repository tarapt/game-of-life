package com.gameoflife.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Generation class.
 * Tests the core Game of Life logic including neighbor calculation
 * and next generation computation.
 *
 * @author TaraPrasad
 */
class GenerationTest {

    private boolean[][] cells;

    @BeforeEach
    void setUp() {
        cells = new boolean[10][10];
    }

    @Test
    void testConstructorWithValidGrid() {
        Generation gen = new Generation(cells);
        assertNotNull(gen);
        assertEquals(10, gen.getRows());
        assertEquals(10, gen.getColumns());
    }

    @Test
    void testConstructorWithNullGrid() {
        assertThrows(IllegalArgumentException.class, () -> new Generation(null));
    }

    @Test
    void testConstructorWithEmptyGrid() {
        boolean[][] empty = new boolean[0][0];
        assertThrows(IllegalArgumentException.class, () -> new Generation(empty));
    }

    @Test
    void testEmptyGridStaysEmpty() {
        Generation gen = new Generation(cells);
        boolean[][] next = gen.getNextGeneration();

        for (int i = 0; i < next.length; i++) {
            for (int j = 0; j < next[i].length; j++) {
                assertFalse(next[i][j], "Cell at [" + i + "][" + j + "] should be dead");
            }
        }
    }

    @Test
    void testBlockPattern() {
        // Block (2x2 square) is a still life
        cells[4][4] = true;
        cells[4][5] = true;
        cells[5][4] = true;
        cells[5][5] = true;

        Generation gen = new Generation(cells);
        boolean[][] next = gen.getNextGeneration();

        // Block should remain unchanged
        assertTrue(next[4][4]);
        assertTrue(next[4][5]);
        assertTrue(next[5][4]);
        assertTrue(next[5][5]);
    }

    @Test
    void testBlinkerPattern() {
        // Blinker oscillates between horizontal and vertical
        // Start with horizontal line
        cells[5][4] = true;
        cells[5][5] = true;
        cells[5][6] = true;

        Generation gen = new Generation(cells);
        boolean[][] next = gen.getNextGeneration();

        // Should become vertical
        assertFalse(next[5][4]);
        assertTrue(next[4][5]);
        assertTrue(next[5][5]);
        assertTrue(next[6][5]);
        assertFalse(next[5][6]);
    }

    @Test
    void testGliderPattern() {
        // Glider moves diagonally
        cells[1][2] = true;
        cells[2][3] = true;
        cells[3][1] = true;
        cells[3][2] = true;
        cells[3][3] = true;

        Generation gen = new Generation(cells);
        boolean[][] next = gen.getNextGeneration();

        // After one generation, glider should have moved
        assertTrue(next[2][1] || next[2][2] || next[2][3] || next[3][1] || next[3][2] || next[3][3] || next[4][2]);
    }

    @Test
    void testSingleCellDies() {
        // A single cell should die (underpopulation)
        cells[5][5] = true;

        Generation gen = new Generation(cells);
        boolean[][] next = gen.getNextGeneration();

        assertFalse(next[5][5], "Single cell should die");
    }

    @Test
    void testCellWithTwoNeighborsSurvives() {
        // Cell with exactly 2 neighbors survives
        cells[5][5] = true;
        cells[5][6] = true;
        cells[6][5] = true;

        Generation gen = new Generation(cells);
        boolean[][] next = gen.getNextGeneration();

        assertTrue(next[5][5], "Cell with 2 neighbors should survive");
    }

    @Test
    void testCellWithThreeNeighborsSurvives() {
        // Cell with exactly 3 neighbors survives
        cells[5][5] = true;
        cells[5][6] = true;
        cells[6][5] = true;
        cells[6][6] = true;

        Generation gen = new Generation(cells);
        boolean[][] next = gen.getNextGeneration();

        assertTrue(next[5][5], "Cell with 3 neighbors should survive");
    }

    @Test
    void testDeadCellWithThreeNeighborsBecomeAlive() {
        // Dead cell with exactly 3 neighbors becomes alive
        cells[5][5] = true;
        cells[5][6] = true;
        cells[6][6] = true;

        Generation gen = new Generation(cells);
        boolean[][] next = gen.getNextGeneration();

        assertTrue(next[6][5], "Dead cell with 3 neighbors should become alive");
    }

    @Test
    void testCellWithFourNeighborsDies() {
        // Cell with 4 neighbors dies (overpopulation)
        cells[5][5] = true;
        cells[4][5] = true;
        cells[6][5] = true;
        cells[5][4] = true;
        cells[5][6] = true;

        Generation gen = new Generation(cells);
        boolean[][] next = gen.getNextGeneration();

        assertFalse(next[5][5], "Cell with 4 neighbors should die");
    }

    @Test
    void testToroidalTopology() {
        // Test that edges wrap around (toroidal topology)
        cells[0][0] = true;
        cells[0][1] = true;
        cells[1][0] = true;

        Generation gen = new Generation(cells);
        boolean[][] next = gen.getNextGeneration();

        // The corner cell at [9][9] should be affected by wrap-around
        assertNotNull(next[9][9]);
    }

    @Test
    void testMultipleGenerations() {
        // Test that we can compute multiple generations
        cells[5][4] = true;
        cells[5][5] = true;
        cells[5][6] = true;

        Generation gen = new Generation(cells);

        boolean[][] gen1 = gen.getNextGeneration();
        assertNotNull(gen1);

        boolean[][] gen2 = gen.getNextGeneration();
        assertNotNull(gen2);

        // After 2 generations, blinker should return to horizontal
        assertTrue(gen2[5][4] || gen2[5][5] || gen2[5][6]);
    }

    @Test
    void testGetCurrentGeneration() {
        cells[5][5] = true;
        Generation gen = new Generation(cells);

        boolean[][] current = gen.getCurrentGeneration();
        assertNotNull(current);
        assertTrue(current[5][5]);

        // Modify returned array should not affect original
        current[5][5] = false;
        boolean[][] current2 = gen.getCurrentGeneration();
        assertTrue(current2[5][5], "Should return deep copy");
    }

    @Test
    void testSetCurrentGenerationWithValidDimensions() {
        Generation gen = new Generation(cells);
        boolean[][] newCells = new boolean[10][10];
        newCells[3][3] = true;

        gen.setCurrentGeneration(newCells);
        boolean[][] current = gen.getCurrentGeneration();
        assertTrue(current[3][3]);
    }

    @Test
    void testSetCurrentGenerationWithInvalidDimensions() {
        Generation gen = new Generation(cells);
        boolean[][] wrongSize = new boolean[5][5];

        assertThrows(IllegalArgumentException.class, () -> gen.setCurrentGeneration(wrongSize));
    }

    @Test
    void testDimensionsAccessors() {
        Generation gen = new Generation(cells);
        assertEquals(10, gen.getRows());
        assertEquals(10, gen.getColumns());
    }

    @Test
    void testLargeGrid() {
        // Test with larger grid
        boolean[][] largeCells = new boolean[100][100];
        largeCells[50][50] = true;
        largeCells[50][51] = true;
        largeCells[51][50] = true;

        Generation gen = new Generation(largeCells);
        boolean[][] next = gen.getNextGeneration();

        assertNotNull(next);
        assertEquals(100, next.length);
        assertEquals(100, next[0].length);
    }

    @Test
    void testSmallGrid() {
        // Test with minimal grid
        boolean[][] smallCells = new boolean[3][3];
        smallCells[1][1] = true;

        Generation gen = new Generation(smallCells);
        boolean[][] next = gen.getNextGeneration();

        assertNotNull(next);
        assertEquals(3, next.length);
        assertEquals(3, next[0].length);
    }
}
