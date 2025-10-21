package com.gameoflife.core;

/**
 * Handles the calculation of generations in Conway's Game of Life.
 * Implements the classic rules:
 * - A live cell with 2-3 neighbors survives
 * - A dead cell with exactly 3 neighbors becomes alive
 * - All other cells die or stay dead
 *
 * Performance optimized with array reuse instead of cloning.
 *
 * @author TaraPrasad
 */
public class Generation {
    private boolean[][] currentGeneration;
    private boolean[][] nextGeneration;
    private int[][] neighborCount;
    private final int rows;
    private final int columns;

    /**
     * Creates a new Generation calculator for the given grid.
     *
     * @param cells Initial cell state grid
     * @throws IllegalArgumentException if cells is null or empty
     */
    public Generation(boolean[][] cells) {
        if (cells == null || cells.length == 0 || cells[0].length == 0) {
            throw new IllegalArgumentException("Cell grid cannot be null or empty");
        }

        this.rows = cells.length;
        this.columns = cells[0].length;
        this.currentGeneration = deepCopy(cells);
        this.neighborCount = new int[rows][columns];
        this.nextGeneration = new boolean[rows][columns];
    }

    /**
     * Calculates the next generation based on current state.
     * Uses double-buffering to avoid unnecessary array allocations.
     */
    private void calculateNextGeneration() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int neighbors = neighborCount[row][column];

                if (neighbors == 3) {
                    // Birth: dead cell with 3 neighbors becomes alive
                    // Survival: live cell with 3 neighbors stays alive
                    nextGeneration[row][column] = true;
                } else if (neighbors == 2) {
                    // Survival: live cell with 2 neighbors stays alive
                    // Stasis: dead cell with 2 neighbors stays dead
                    nextGeneration[row][column] = currentGeneration[row][column];
                } else {
                    // Death: all other cases result in death
                    nextGeneration[row][column] = false;
                }
            }
        }
    }

    /**
     * Counts neighbors for each cell in the grid.
     * Uses toroidal (wrap-around) topology for edges.
     */
    private void calculateNeighbors() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                neighborCount[row][column] = countLiveNeighbors(row, column);
            }
        }
    }

    /**
     * Counts live neighbors for a specific cell using toroidal wrap-around.
     *
     * @param row Cell row index
     * @param column Cell column index
     * @return Number of live neighbors (0-8)
     */
    private int countLiveNeighbors(int row, int column) {
        int count = 0;

        // Check all 8 neighbors with wrap-around
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int r = (row + dr[i] + rows) % rows;
            int c = (column + dc[i] + columns) % columns;
            if (currentGeneration[r][c]) {
                count++;
            }
        }

        return count;
    }

    /**
     * Gets the current generation state.
     *
     * @return Deep copy of current generation to prevent external modification
     */
    public boolean[][] getCurrentGeneration() {
        return deepCopy(currentGeneration);
    }

    /**
     * Sets the current generation state.
     *
     * @param currentGeneration New generation state
     * @throws IllegalArgumentException if dimensions don't match
     */
    public void setCurrentGeneration(boolean[][] currentGeneration) {
        if (currentGeneration.length != rows || currentGeneration[0].length != columns) {
            throw new IllegalArgumentException(
                "New generation dimensions must match: " + rows + "x" + columns);
        }
        this.currentGeneration = deepCopy(currentGeneration);
    }

    /**
     * Computes and returns the next generation.
     * This method updates internal state and returns a copy of the next generation.
     *
     * @return Deep copy of the next generation state
     */
    public boolean[][] getNextGeneration() {
        calculateNeighbors();
        calculateNextGeneration();

        // Swap buffers for efficiency (reuse arrays)
        boolean[][] temp = currentGeneration;
        currentGeneration = nextGeneration;
        nextGeneration = temp;

        return deepCopy(currentGeneration);
    }

    /**
     * Creates a deep copy of a 2D boolean array.
     *
     * @param original Array to copy
     * @return Deep copy of the array
     */
    private boolean[][] deepCopy(boolean[][] original) {
        boolean[][] copy = new boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    /**
     * Gets the number of rows in the grid.
     *
     * @return Number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns in the grid.
     *
     * @return Number of columns
     */
    public int getColumns() {
        return columns;
    }
}
