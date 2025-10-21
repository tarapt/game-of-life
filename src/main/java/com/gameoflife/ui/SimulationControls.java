package com.gameoflife.ui;

import com.gameoflife.listener.SimulationControlsListener;
import com.gameoflife.util.GameConfig;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel containing simulation control buttons.
 * Manages the UI state based on simulation state.
 *
 * @author TaraPrasad
 */
public class SimulationControls extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private SimulationControlsListener simulationControlsListener;
    private JGradientButton nextGenerationButton;
    private JGradientButton pauseButton;
    private JGradientButton resumeButton;
    private JGradientButton addCellsButton;
    private JGradientButton removeCellsButton;
    private JGradientButton clearButton;
    private JGradientButton openFileButton;
    private JGradientButton startButton;
    private JGradientButton resetButton;

    /**
     * Creates a new SimulationControls panel with all control buttons.
     */
    public SimulationControls() {
        setBackground(GameConfig.CONTROLS_BACKGROUND);
        setLayout(new GridLayout(3, 3));

        // Create control buttons
        nextGenerationButton = JGradientButton.newInstance("Next Generation");
        pauseButton = JGradientButton.newInstance("Pause");
        resumeButton = JGradientButton.newInstance("Resume");
        addCellsButton = JGradientButton.newInstance("Add Cells Mode");
        removeCellsButton = JGradientButton.newInstance("Remove Cells Mode");
        clearButton = JGradientButton.newInstance("Clear");
        openFileButton = JGradientButton.newInstance("Open File");
        startButton = JGradientButton.newInstance("Start");
        resetButton = JGradientButton.newInstance("Reset");

        // Add buttons to panel
        add(startButton);
        add(openFileButton);
        add(nextGenerationButton);
        add(pauseButton);
        add(resumeButton);
        add(resetButton);
        add(addCellsButton);
        add(removeCellsButton);
        add(clearButton);

        // Add action listeners
        pauseButton.addActionListener(this);
        nextGenerationButton.addActionListener(this);
        resumeButton.addActionListener(this);
        addCellsButton.addActionListener(this);
        removeCellsButton.addActionListener(this);
        clearButton.addActionListener(this);
        openFileButton.addActionListener(this);
        startButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    /**
     * Sets the listener for simulation control events.
     *
     * @param listener Listener to receive control events
     */
    public void setSimulationControlsListener(SimulationControlsListener listener) {
        this.simulationControlsListener = listener;
    }

    /**
     * Sets button states for initial configuration.
     */
    public void setInitialConfiguration() {
        addCellsButton.setEnabled(true);
        removeCellsButton.setEnabled(true);
        clearButton.setEnabled(true);
        nextGenerationButton.setEnabled(false);
        pauseButton.setEnabled(false);
        resumeButton.setEnabled(false);
        openFileButton.setEnabled(true);
        startButton.setEnabled(false);
        resetButton.setEnabled(false);
    }

    /**
     * Sets button states after loading a file.
     */
    public void setLoadFileConfiguration() {
        addCellsButton.setEnabled(true);
        removeCellsButton.setEnabled(true);
        clearButton.setEnabled(true);
        nextGenerationButton.setEnabled(true);
        pauseButton.setEnabled(true);
        resumeButton.setEnabled(false);
        openFileButton.setEnabled(true);
        startButton.setEnabled(true);
        resetButton.setEnabled(true);
    }

    /**
     * Sets button states when simulation is startable.
     */
    public void setStartableConfiguration() {
        nextGenerationButton.setEnabled(true);
        startButton.setEnabled(true);
    }

    /**
     * Sets button states when simulation is running.
     */
    public void setRunningConfiguration() {
        pauseButton.setEnabled(true);
        openFileButton.setEnabled(true);
        resetButton.setEnabled(true);
        startButton.setEnabled(false);
        nextGenerationButton.setEnabled(false);
    }

    /**
     * Sets button states for single-step mode.
     */
    public void setSingleStepConfiguration() {
        openFileButton.setEnabled(true);
        resetButton.setEnabled(true);
    }

    /**
     * Sets button states when simulation is paused.
     */
    public void setPausedConfiguration() {
        nextGenerationButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resumeButton.setEnabled(true);
    }

    /**
     * Sets button states when simulation is resumed.
     */
    public void setResumedConfiguration() {
        resumeButton.setEnabled(false);
        nextGenerationButton.setEnabled(false);
        pauseButton.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == pauseButton) {
            firePauseButtonClicked();
        } else if (source == resumeButton) {
            fireResumeButtonClicked();
        } else if (source == nextGenerationButton) {
            fireNextGenerationButtonClicked();
        } else if (source == addCellsButton) {
            fireAddCellsButtonClicked();
        } else if (source == removeCellsButton) {
            fireRemoveCellsButtonClicked();
        } else if (source == clearButton) {
            fireClearButtonClicked();
        } else if (source == openFileButton) {
            fireOpenFileButtonClicked();
        } else if (source == startButton) {
            fireStartButtonClicked();
        } else if (source == resetButton) {
            fireResetButtonClicked();
        }
    }

    private void fireResetButtonClicked() {
        if (simulationControlsListener != null) {
            simulationControlsListener.resetButtonClicked();
        }
    }

    private void fireStartButtonClicked() {
        if (simulationControlsListener != null) {
            simulationControlsListener.startButtonClicked();
        }
    }

    private void fireClearButtonClicked() {
        if (simulationControlsListener != null) {
            simulationControlsListener.clearButtonClicked();
        }
    }

    private void fireRemoveCellsButtonClicked() {
        if (simulationControlsListener != null) {
            simulationControlsListener.removeCellsButtonClicked();
        }
    }

    private void fireAddCellsButtonClicked() {
        if (simulationControlsListener != null) {
            simulationControlsListener.addCellsButtonClicked();
        }
    }

    private void fireNextGenerationButtonClicked() {
        if (simulationControlsListener != null) {
            simulationControlsListener.nextGenerationButtonClicked();
        }
    }

    private void fireResumeButtonClicked() {
        if (simulationControlsListener != null) {
            simulationControlsListener.resumeButtonClicked();
        }
    }

    private void firePauseButtonClicked() {
        if (simulationControlsListener != null) {
            simulationControlsListener.pauseButtonClicked();
        }
    }

    private void fireOpenFileButtonClicked() {
        if (simulationControlsListener != null) {
            simulationControlsListener.openFileButtonClicked();
        }
    }
}
