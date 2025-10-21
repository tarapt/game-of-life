package com.gameoflife.listener;

/**
 * Listener interface for start panel events.
 *
 * @author TaraPrasad
 */
public interface StartPanelListener {

    /**
     * Called when the enter button is clicked.
     */
    void enterButtonClicked();

    /**
     * Called when the exit button is clicked.
     */
    void exitButtonClicked();

    /**
     * Called when the rules button is clicked.
     */
    void rulesButtonClicked();

    /**
     * Called when the about button is clicked.
     */
    void aboutButtonClicked();

    /**
     * Called when the group info button is clicked.
     */
    void groupInfoButtonClicked();
}
