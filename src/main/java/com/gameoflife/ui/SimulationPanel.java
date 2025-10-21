package com.gameoflife.ui;

import com.gameoflife.core.Generation;
import com.gameoflife.listener.SimulationControlsListener;
import com.gameoflife.listener.SpeedPanelListener;
import com.gameoflife.util.GameConfig;
import com.gameoflife.util.PatternLoader;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main simulation panel for Game of Life.
 * Manages the grid, controls, and simulation state.
 * Refactored for better separation of concerns and error handling.
 *
 * @author TaraPrasad
 */
public class SimulationPanel extends JComponent implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(SimulationPanel.class.getName());

    private final Runnable backButtonCallback;
    private Grid grid;
    private SimulationControls simulationControls;
    private JPanel statusPanel;
    private JLabel generationLabel;
    private SpeedPanel speedPanel;
    private JGradientButton backButton;
    private SimulationState simulationState;
    private Generation generation;
    private Timer timer;
    private boolean[][] cells;
    private int waitTime;
    private volatile boolean addingCells;
    private volatile boolean removingCells;
    private int generationNumber;

    private final JFileChooser fileDialog;

    /**
     * Simulation state enumeration.
     */
    private enum SimulationState {
        INITIAL, STARTABLE, RUNNING, PAUSED, RESUMED, SINGLESTEP
    }

    /**
     * Creates a new SimulationPanel.
     *
     * @param backButtonCallback Callback for back button
     */
    public SimulationPanel(Runnable backButtonCallback) {
        this.backButtonCallback = backButtonCallback;

        // Initialize variables
        generationNumber = 1;
        waitTime = GameConfig.DEFAULT_WAIT_TIME_MS;

        // Create file dialog with filter
        fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter(
                "Life Pattern Files (*.life)", "life"));
        File patternsDir = new File("Life Patterns");
        if (patternsDir.exists()) {
            fileDialog.setCurrentDirectory(patternsDir);
        }

        // Create components
        initializeComponents();
        setupLayout();
        setupListeners();
        setSimulationState(SimulationState.INITIAL);
    }

    /**
     * Initializes all UI components.
     */
    private void initializeComponents() {
        backButton = JGradientButton.newInstance("Back");
        cells = new boolean[GameConfig.GRID_ROWS][GameConfig.GRID_COLUMNS];
        statusPanel = new JPanel();
        speedPanel = new SpeedPanel();
        generationLabel = new JLabel("Generation: " + generationNumber);
        grid = new Grid(cells);
        generation = new Generation(cells);
        timer = new Timer(waitTime, this);
        simulationControls = new SimulationControls();

        setSize(850, 550);
        setLayout(new GridBagLayout());
    }

    /**
     * Sets up the panel layout.
     */
    private void setupLayout() {
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        generationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        generationLabel.setFont(new Font("Dialog", Font.PLAIN, GameConfig.STATUS_FONT_SIZE));
        statusPanel.add(generationLabel);

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;

        // First Row - Grid
        gc.gridy = 0;
        gc.weighty = 5;
        gc.weightx = 1;
        grid.setPreferredSize(new Dimension(getWidth(), (int) (getHeight() * 0.8)));
        add(grid, gc);

        // Second Row - Controls
        gc.gridy = 1;
        gc.weighty = 1;
        gc.weightx = 1;
        simulationControls.setPreferredSize(new Dimension(getWidth(), (int) (getHeight() * 0.16)));
        gc.anchor = GridBagConstraints.SOUTH;
        add(simulationControls, gc);

        // Third Row - Back button, status, speed
        gc.fill = GridBagConstraints.RELATIVE;
        gc.gridy = 2;
        gc.gridx = 0;
        gc.weighty = 0.2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(backButton, gc);

        gc.fill = GridBagConstraints.RELATIVE;
        statusPanel.setPreferredSize(new Dimension((int) (getWidth() * 0.2), (int) (getHeight() * 0.04)));
        gc.insets = new Insets(0, 100, 0, 0);
        gc.anchor = GridBagConstraints.WEST;
        add(statusPanel, gc);

        gc.fill = GridBagConstraints.REMAINDER;
        speedPanel.setPreferredSize(new Dimension((int) (getWidth() * 0.8), (int) (getHeight() * 0.04)));
        gc.anchor = GridBagConstraints.EAST;
        add(speedPanel, gc);
    }

    /**
     * Sets up all event listeners.
     */
    private void setupListeners() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (backButtonCallback != null) {
                    backButtonCallback.run();
                }
            }
        });

        speedPanel.setSpeedPanelListener(new SpeedPanelListener() {
            @Override
            public void speedSelected(int speed) {
                if (speed > 0) {
                    waitTime = 1000 / speed;
                    timer.setDelay(waitTime);
                }
            }
        });

        simulationControls.setSimulationControlsListener(new SimulationControlsListener() {
            @Override
            public void startButtonClicked() {
                generationNumber = 1;
                setSimulationState(SimulationState.RUNNING);
                timer.start();
            }

            @Override
            public void pauseButtonClicked() {
                setSimulationState(SimulationState.PAUSED);
            }

            @Override
            public void resumeButtonClicked() {
                setSimulationState(SimulationState.RESUMED);
            }

            @Override
            public void openFileButtonClicked() {
                handleOpenFile();
            }

            @Override
            public void nextGenerationButtonClicked() {
                setSimulationState(SimulationState.SINGLESTEP);
                displayNextGeneration();
            }

            @Override
            public void clearButtonClicked() {
                grid.clearCells();
            }

            @Override
            public void removeCellsButtonClicked() {
                addingCells = false;
                removingCells = true;
            }

            @Override
            public void addCellsButtonClicked() {
                removingCells = false;
                addingCells = true;
            }

            @Override
            public void resetButtonClicked() {
                handleReset();
            }
        });

        setupMouseListeners();
    }

    /**
     * Sets up mouse listeners for grid interaction.
     */
    private void setupMouseListeners() {
        grid.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toggleCell(e);
            }
        });

        grid.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDrag(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // Not used
            }
        });
    }

    /**
     * Toggles cell state on mouse click.
     *
     * @param e Mouse event
     */
    private void toggleCell(MouseEvent e) {
        int row = (int) (e.getY() / grid.getCellHeight());
        int column = (int) (e.getX() / grid.getCellWidth());

        if (isValidCell(row, column)) {
            cells[row][column] = !cells[row][column];
            grid.repaint();

            if (simulationState == SimulationState.INITIAL) {
                setSimulationState(SimulationState.STARTABLE);
            }
        }
    }

    /**
     * Handles mouse drag for adding/removing cells.
     *
     * @param e Mouse event
     */
    private void handleMouseDrag(MouseEvent e) {
        int row = (int) (e.getY() / grid.getCellHeight());
        int column = (int) (e.getX() / grid.getCellWidth());

        if (isValidCell(row, column)) {
            if (addingCells) {
                cells[row][column] = true;
            } else if (removingCells) {
                cells[row][column] = false;
            }
            grid.repaint();

            if (simulationState == SimulationState.INITIAL) {
                setSimulationState(SimulationState.STARTABLE);
            }
        }
    }

    /**
     * Checks if cell coordinates are valid.
     *
     * @param row Cell row
     * @param column Cell column
     * @return true if valid, false otherwise
     */
    private boolean isValidCell(int row, int column) {
        return row >= 0 && row < cells.length && column >= 0 && column < cells[0].length;
    }

    /**
     * Handles opening and loading a pattern file.
     */
    private void handleOpenFile() {
        resetButtonClicked();
        int returnVal = fileDialog.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileDialog.getSelectedFile();
            loadPatternFromFile(file);
        }
    }

    /**
     * Loads a pattern from a file with error handling.
     *
     * @param file File to load
     */
    private void loadPatternFromFile(File file) {
        try {
            PatternLoader.loadPattern(file, cells, GameConfig.ORIGIN_ROW, GameConfig.ORIGIN_COLUMN);
            generationNumber = 1;
            generation = new Generation(cells);
            grid.updateGrid(cells);
            updateStatusLabel();
            simulationControls.setLoadFileConfiguration();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load pattern file", e);
            JOptionPane.showMessageDialog(this,
                    "Could not load pattern file:\n" + e.getMessage(),
                    "Error Loading Pattern",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid pattern file", e);
            JOptionPane.showMessageDialog(this,
                    "Invalid pattern file format:\n" + e.getMessage(),
                    "Invalid Pattern",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Handles reset button click.
     */
    private void handleReset() {
        resetButtonClicked();
    }

    /**
     * Resets the simulation to initial state.
     */
    private void resetButtonClicked() {
        setSimulationState(SimulationState.INITIAL);
        generationNumber = 1;
        cells = new boolean[GameConfig.GRID_ROWS][GameConfig.GRID_COLUMNS];
        generation = new Generation(cells);
        grid.updateGrid(cells);
        updateStatusLabel();
    }

    /**
     * Sets the simulation state and updates UI accordingly.
     *
     * @param simulationState New simulation state
     */
    private void setSimulationState(SimulationState simulationState) {
        this.simulationState = simulationState;
        switch (simulationState) {
            case INITIAL:
                simulationControls.setInitialConfiguration();
                break;
            case STARTABLE:
                simulationControls.setStartableConfiguration();
                break;
            case RUNNING:
                simulationControls.setRunningConfiguration();
                break;
            case PAUSED:
                simulationControls.setPausedConfiguration();
                break;
            case RESUMED:
                simulationControls.setResumedConfiguration();
                break;
            case SINGLESTEP:
                simulationControls.setSingleStepConfiguration();
                break;
        }
    }

    /**
     * Displays the next generation.
     */
    private void displayNextGeneration() {
        cells = generation.getNextGeneration();
        grid.updateGrid(cells);
        generationNumber++;
        updateStatusLabel();
    }

    /**
     * Updates the status label with current generation.
     */
    private void updateStatusLabel() {
        generationLabel.setText("Generation: " + generationNumber);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (simulationState == SimulationState.RUNNING || simulationState == SimulationState.RESUMED) {
            displayNextGeneration();
        }
    }
}
