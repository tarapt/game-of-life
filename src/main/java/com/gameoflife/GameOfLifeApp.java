package com.gameoflife;

import com.gameoflife.listener.StartPanelListener;
import com.gameoflife.ui.AboutPanel;
import com.gameoflife.ui.RulesPanel;
import com.gameoflife.ui.SimulationPanel;
import com.gameoflife.ui.StartPanel;
import com.gameoflife.util.GameConfig;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main application class for Game of Life.
 * Migrated from JApplet to JFrame for modern Java compatibility.
 *
 * This application simulates Conway's Game of Life, a cellular automaton
 * devised by mathematician John Conway.
 *
 * @author TaraPrasad
 * @version 2.0.0
 */
public class GameOfLifeApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(GameOfLifeApp.class.getName());

    private CardLayout cardLayout;
    private SimulationPanel simulationPanel;
    private StartPanel startPanel;
    private RulesPanel rulesPanel;
    private AboutPanel aboutPanel;

    /**
     * Creates a new Game of Life application.
     */
    public GameOfLifeApp() {
        initializeUI();
        setupPanels();
        setupListeners();
    }

    /**
     * Initializes the main UI frame.
     */
    private void initializeUI() {
        setTitle("Game of Life Simulation");
        setSize(GameConfig.APP_WIDTH, GameConfig.APP_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);

        cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);
    }

    /**
     * Sets up all application panels.
     */
    private void setupPanels() {
        startPanel = new StartPanel();
        simulationPanel = new SimulationPanel(this::showStartPanel);
        rulesPanel = new RulesPanel(this::showStartPanel);
        aboutPanel = new AboutPanel(this::showStartPanel);

        getContentPane().add(startPanel, "start");
        getContentPane().add(simulationPanel, "simulation");
        getContentPane().add(rulesPanel, "rules");
        getContentPane().add(aboutPanel, "about");

        // Show start panel initially
        cardLayout.show(getContentPane(), "start");
    }

    /**
     * Sets up event listeners for navigation.
     */
    private void setupListeners() {
        startPanel.setStartPanelListener(new StartPanelListener() {
            @Override
            public void enterButtonClicked() {
                cardLayout.show(getContentPane(), "simulation");
            }

            @Override
            public void exitButtonClicked() {
                System.exit(0);
            }

            @Override
            public void rulesButtonClicked() {
                cardLayout.show(getContentPane(), "rules");
            }

            @Override
            public void aboutButtonClicked() {
                cardLayout.show(getContentPane(), "about");
            }

            @Override
            public void groupInfoButtonClicked() {
                // Not implemented in this version
            }
        });
    }

    /**
     * Shows the start panel.
     */
    private void showStartPanel() {
        cardLayout.show(getContentPane(), "start");
    }

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not set system look and feel", e);
        }

        // Launch application on Event Dispatch Thread
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GameOfLifeApp app = new GameOfLifeApp();
                    app.setVisible(true);
                    LOGGER.log(Level.INFO, "Game of Life application started successfully");
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Failed to start application", e);
                    System.err.println("Error starting application: " + e.getMessage());
                    System.exit(1);
                }
            }
        });
    }
}
