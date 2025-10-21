package com.gameoflife.listener;

/**
 * Listener interface for speed panel events.
 *
 * @author TaraPrasad
 */
public interface SpeedPanelListener {

    /**
     * Called when a speed is selected.
     *
     * @param speed The selected speed value
     */
    void speedSelected(int speed);
}
