package com.gameoflife.ui;

import com.gameoflife.listener.StartPanelListener;
import com.gameoflife.util.GameConfig;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Start screen panel with main menu options.
 * Provides navigation to simulation, rules, and about screens.
 *
 * @author TaraPrasad
 */
public class StartPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JGradientButton rulesButton;
    private StartPanelListener startPanelListener;
    private JGradientButton enterButton;
    private JGradientButton exitButton;
    private JLabel titleLabel;
    private JGradientButton aboutButton;

    /**
     * Creates a new StartPanel with menu buttons.
     */
    public StartPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{618};
        gridBagLayout.rowHeights = new int[]{12, 0, -66, 116, 70, 65, 71, 63, 66, 0};
        gridBagLayout.columnWeights = new double[]{0.0};
        setLayout(gridBagLayout);
        setBackground(GameConfig.START_PANEL_BACKGROUND);

        setupComponents();
        setupListeners();
    }

    /**
     * Sets up UI components and layout.
     */
    private void setupComponents() {
        titleLabel = new JLabel("Game of Life Simulation");
        titleLabel.setFont(new Font("Dialog", Font.PLAIN, GameConfig.TITLE_FONT_SIZE));
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 5, 0);
        gbc_label.ipadx = 20;
        gbc_label.gridx = 0;
        gbc_label.gridy = 3;
        add(titleLabel, gbc_label);

        enterButton = JGradientButton.newInstance("Enter Simulation");
        enterButton.setFont(new Font("Dialog", Font.PLAIN, GameConfig.BUTTON_FONT_SIZE));
        GridBagConstraints gbc_enter = new GridBagConstraints();
        gbc_enter.insets = new Insets(0, 0, 5, 0);
        gbc_enter.ipadx = 28;
        gbc_enter.gridx = 0;
        gbc_enter.gridy = 4;
        add(enterButton, gbc_enter);

        rulesButton = JGradientButton.newInstance("Rules");
        rulesButton.setFont(new Font("Dialog", Font.PLAIN, GameConfig.BUTTON_FONT_SIZE));
        GridBagConstraints gbc_rules = new GridBagConstraints();
        gbc_rules.insets = new Insets(0, 0, 5, 0);
        gbc_rules.ipadx = 125;
        gbc_rules.gridx = 0;
        gbc_rules.gridy = 5;
        add(rulesButton, gbc_rules);

        aboutButton = JGradientButton.newInstance("About Game of Life");
        aboutButton.setFont(new Font("Dialog", Font.PLAIN, GameConfig.BUTTON_FONT_SIZE));
        GridBagConstraints gbc_about = new GridBagConstraints();
        gbc_about.insets = new Insets(0, 0, 5, 0);
        gbc_about.gridx = 0;
        gbc_about.gridy = 6;
        add(aboutButton, gbc_about);

        exitButton = JGradientButton.newInstance("Exit");
        exitButton.setFont(new Font("Dialog", Font.PLAIN, GameConfig.BUTTON_FONT_SIZE));
        GridBagConstraints gbc_exit = new GridBagConstraints();
        gbc_exit.insets = new Insets(0, 0, 5, 0);
        gbc_exit.ipadx = 139;
        gbc_exit.gridx = 0;
        gbc_exit.gridy = 8;
        add(exitButton, gbc_exit);
    }

    /**
     * Sets up button action listeners.
     */
    private void setupListeners() {
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireRulesButtonClicked();
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEnterButtonClicked();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireExitButtonClicked();
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireAboutButtonClicked();
            }
        });
    }

    /**
     * Sets the start panel listener.
     *
     * @param startPanelListener Listener to receive panel events
     */
    public void setStartPanelListener(StartPanelListener startPanelListener) {
        this.startPanelListener = startPanelListener;
    }

    private void fireRulesButtonClicked() {
        if (startPanelListener != null) {
            startPanelListener.rulesButtonClicked();
        }
    }

    private void fireEnterButtonClicked() {
        if (startPanelListener != null) {
            startPanelListener.enterButtonClicked();
        }
    }

    private void fireExitButtonClicked() {
        if (startPanelListener != null) {
            startPanelListener.exitButtonClicked();
        }
    }

    private void fireAboutButtonClicked() {
        if (startPanelListener != null) {
            startPanelListener.aboutButtonClicked();
        }
    }

    private void fireGroupInfoButtonClicked() {
        if (startPanelListener != null) {
            startPanelListener.groupInfoButtonClicked();
        }
    }
}
