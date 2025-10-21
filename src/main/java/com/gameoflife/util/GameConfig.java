package com.gameoflife.util;

import java.awt.Color;

/**
 * Configuration class for Game of Life application.
 * Centralizes all configuration constants for easy maintenance and customization.
 *
 * @author TaraPrasad
 */
public final class GameConfig {

    // Prevent instantiation
    private GameConfig() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // Application dimensions
    public static final int APP_WIDTH = 1290;
    public static final int APP_HEIGHT = 640;

    // Grid configuration
    public static final int GRID_ROWS = 60;
    public static final int GRID_COLUMNS = 180;
    public static final int ORIGIN_ROW = 20;
    public static final int ORIGIN_COLUMN = 40;

    // Simulation timing
    public static final int DEFAULT_WAIT_TIME_MS = 1000;
    public static final int MAX_SIMULATION_SPEED = 10;

    // Colors
    public static final Color GRID_BACKGROUND = Color.BLACK;
    public static final Color CELL_COLOR = Color.WHITE;
    public static final Color GRID_LINE_COLOR = Color.BLACK;
    public static final Color START_PANEL_BACKGROUND = new Color(0x43AC6A);
    public static final Color CONTROLS_BACKGROUND = new Color(255, 255, 153);
    public static final Color BUTTON_GRADIENT_START = Color.WHITE;
    public static final Color BUTTON_GRADIENT_END = new Color(0xBD8CBF);

    // Font sizes
    public static final int TITLE_FONT_SIZE = 40;
    public static final int BUTTON_FONT_SIZE = 20;
    public static final int STATUS_FONT_SIZE = 20;

    // File patterns
    public static final String PATTERN_FILE_EXTENSION = ".life";
}
