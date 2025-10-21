package com.gameoflife.ui;

import com.gameoflife.listener.SpeedPanelListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel for controlling simulation speed.
 * Provides preset speeds and custom speed selection.
 *
 * @author TaraPrasad
 */
public class SpeedPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int SPEED_LOW = 2;
    private static final int SPEED_MEDIUM = 4;
    private static final int SPEED_HIGH = 8;
    private static final int SPEED_EXTREME = 16;

    private JSpinner speedSpinner;
    private JComboBox<String> speedCombo;
    private JLabel speedLabel;
    private SpeedPanelListener speedPanelListener;

    /**
     * Creates a new SpeedPanel with speed controls.
     */
    public SpeedPanel() {
        speedLabel = new JLabel("Set/Select Speed (in GPS): ");
        speedLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        speedSpinner = new JSpinner();
        speedSpinner.setModel(new SpinnerNumberModel(SPEED_LOW, 1, 1000, 4));
        speedSpinner.setToolTipText("Increments the speed by units of 4");
        speedSpinner.setEnabled(false);

        speedCombo = new JComboBox<>();
        setupSpeedComboBox();

        setLayout(new GridLayout(1, 3));
        add(speedLabel);
        add(speedCombo);
        add(speedSpinner);
    }

    /**
     * Configures the speed combo box with preset speeds.
     */
    private void setupSpeedComboBox() {
        DefaultComboBoxModel<String> speedModel = new DefaultComboBoxModel<>();
        speedModel.addElement("Low");      // 2 generations per second
        speedModel.addElement("Medium");   // 4 generations per second
        speedModel.addElement("High");     // 8 generations per second
        speedModel.addElement("Extreme");  // 16 generations per second
        speedModel.addElement("Custom");   // Custom value

        speedCombo.setModel(speedModel);
        speedCombo.setEditable(false);
        speedCombo.setSelectedIndex(0);

        speedCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSpeedSelection();
            }
        });

        speedSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int speed = (int) speedSpinner.getValue();
                fireSpeedSelected(speed);
            }
        });
    }

    /**
     * Handles speed selection from combo box.
     */
    private void handleSpeedSelection() {
        String selected = (String) speedCombo.getSelectedItem();
        if (selected == null) {
            return;
        }

        int speed;
        boolean enableCustom = false;

        switch (selected) {
            case "Low":
                speed = SPEED_LOW;
                break;
            case "Medium":
                speed = SPEED_MEDIUM;
                break;
            case "High":
                speed = SPEED_HIGH;
                break;
            case "Extreme":
                speed = SPEED_EXTREME;
                break;
            case "Custom":
                speed = (int) speedSpinner.getValue();
                enableCustom = true;
                break;
            default:
                speed = SPEED_LOW;
                break;
        }

        speedSpinner.setEnabled(enableCustom);
        speedSpinner.setValue(speed);
    }

    /**
     * Notifies listener of speed selection.
     *
     * @param speed Selected speed value
     */
    private void fireSpeedSelected(int speed) {
        if (speedPanelListener != null) {
            speedPanelListener.speedSelected(speed);
        }
    }

    /**
     * Sets the speed panel listener.
     *
     * @param speedPanelListener Listener to receive speed events
     */
    public void setSpeedPanelListener(SpeedPanelListener speedPanelListener) {
        this.speedPanelListener = speedPanelListener;
    }
}
