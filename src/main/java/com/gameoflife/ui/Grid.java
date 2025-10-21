package com.gameoflife.ui;

import com.gameoflife.util.GameConfig;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * Visual grid component for displaying Game of Life cells.
 * Performance optimized by caching cell dimensions.
 *
 * @author TaraPrasad
 */
public class Grid extends JPanel {
    private static final long serialVersionUID = 1L;

    private boolean[][] cells;
    private double cellWidth;
    private double cellHeight;
    private boolean dimensionsValid = false;

    /**
     * Creates a new Grid with the given cell state.
     *
     * @param cells Initial cell state (true = alive, false = dead)
     */
    public Grid(boolean[][] cells) {
        if (cells == null || cells.length == 0 || cells[0].length == 0) {
            throw new IllegalArgumentException("Cell grid cannot be null or empty");
        }
        this.cells = cells;
        setBackground(GameConfig.GRID_BACKGROUND);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Recalculate dimensions if component size changed
        updateDimensions();

        // Draw the cells
        drawCells(g2);

        // Draw grid lines
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(1.0f));
        drawLines(g2);
        g2.setStroke(oldStroke);
    }

    /**
     * Updates cached cell dimensions if needed.
     */
    private void updateDimensions() {
        if (!dimensionsValid || cellWidth == 0 || cellHeight == 0) {
            cellWidth = (double) getWidth() / cells[0].length;
            cellHeight = (double) getHeight() / cells.length;
            dimensionsValid = true;
        }
    }

    /**
     * Draws alive cells on the grid.
     *
     * @param g Graphics context
     */
    private void drawCells(Graphics g) {
        g.setColor(GameConfig.CELL_COLOR);
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                if (cells[row][column]) {
                    int x = (int) Math.round(column * cellWidth);
                    int y = (int) Math.round(row * cellHeight);
                    int w = (int) Math.round(cellWidth + 1);
                    int h = (int) Math.round(cellHeight);
                    g.fillRect(x, y, w, h);
                }
            }
        }
    }

    /**
     * Draws grid lines.
     *
     * @param g Graphics context
     */
    private void drawLines(Graphics g) {
        g.setColor(GameConfig.GRID_LINE_COLOR);

        // Vertical lines
        for (int column = 0; column <= cells[0].length; column++) {
            int x = (int) Math.round(column * cellWidth);
            g.drawLine(x, 0, x, getHeight());
        }

        // Horizontal lines
        for (int row = 0; row <= cells.length; row++) {
            int y = (int) Math.round(row * cellHeight);
            g.drawLine(0, y, getWidth(), y);
        }
    }

    /**
     * Updates the grid with new cell state and repaints.
     *
     * @param cells New cell state
     * @throws IllegalArgumentException if dimensions don't match original grid
     */
    public void updateGrid(boolean[][] cells) {
        if (cells == null) {
            throw new IllegalArgumentException("Cells cannot be null");
        }
        if (cells.length != this.cells.length || cells[0].length != this.cells[0].length) {
            throw new IllegalArgumentException(
                "New grid dimensions must match original: " +
                this.cells.length + "x" + this.cells[0].length);
        }
        this.cells = cells;
        repaint();
    }

    /**
     * Clears all cells (sets them to dead state) and repaints.
     */
    public void clearCells() {
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                cells[row][column] = false;
            }
        }
        repaint();
    }

    /**
     * Gets the cell width in pixels.
     *
     * @return Cell width
     */
    public double getCellWidth() {
        updateDimensions();
        return cellWidth;
    }

    /**
     * Gets the cell height in pixels.
     *
     * @return Cell height
     */
    public double getCellHeight() {
        updateDimensions();
        return cellHeight;
    }

    /**
     * Gets the current cell state.
     *
     * @return Reference to cell array (not a copy for performance)
     */
    public boolean[][] getCells() {
        return cells;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        dimensionsValid = false;
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        dimensionsValid = false;
    }
}
