package com.gameoflife.listener;

/**
 * Listener interface for simulation control events.
 *
 * @author TaraPrasad
 */
public interface SimulationControlsListener {

    /**
     * Called when the start button is clicked.
     */
    void startButtonClicked();

    /**
     * Called when the pause button is clicked.
     */
    void pauseButtonClicked();

    /**
     * Called when the resume button is clicked.
     */
    void resumeButtonClicked();

    /**
     * Called when the open file button is clicked.
     */
    void openFileButtonClicked();

    /**
     * Called when the next generation button is clicked.
     */
    void nextGenerationButtonClicked();

    /**
     * Called when the clear button is clicked.
     */
    void clearButtonClicked();

    /**
     * Called when the remove cells button is clicked.
     */
    void removeCellsButtonClicked();

    /**
     * Called when the add cells button is clicked.
     */
    void addCellsButtonClicked();

    /**
     * Called when the reset button is clicked.
     */
    void resetButtonClicked();
}
