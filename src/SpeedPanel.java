import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpeedPanel extends JPanel {
	private JSpinner speedSpinner;
	private JComboBox<String> speedCombo;
	private JLabel speedLabel;
	private SpeedPanelListener speedPanelListener;
	
	public SpeedPanel() {
		speedLabel = new JLabel("Set/Select Speed (in GPS): ");
		speedLabel.setFont(new Font("",15,18));
		speedSpinner = new JSpinner();
		speedSpinner.setModel(new SpinnerNumberModel(2, 1, 1000 , 4));
		// TODO background and alignment not working
		speedSpinner.setBackground(Color.pink);
		speedSpinner.setAlignmentX(JSpinner.CENTER_ALIGNMENT);
		speedSpinner.setToolTipText("Increments the speed by units of 4");
		speedSpinner.setEnabled(false);
		speedCombo = new JComboBox<String>();
		// Setup speedCombo ComboBox
		DefaultComboBoxModel<String> speedModel = new DefaultComboBoxModel<String>();
		speedModel.addElement("Low");//Low = 2 generation per second - waitTime = 500ms
		speedModel.addElement("Medium");//Medium = 4 generation per second - waitTime = 250ms
		speedModel.addElement("High");//High = 8 generation per second - waitTime = 125ms
		speedModel.addElement("Extreme");//Extreme = 168 generation per second - waitTime = 62ms
		speedModel.addElement("Custom");//Set Custom value
		speedCombo.setModel(speedModel);
		speedCombo.setEditable(false);
		speedCombo.setSelectedIndex(0);
		speedCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int speed = (int) speedSpinner.getValue();
				JComboBox speedCombo = (JComboBox) e.getSource();
				switch(speedCombo.getSelectedItem().toString()){
				case "Low":
					speedSpinner.setEnabled(false);
					speed = 2;
					break;
				case "Medium":
					speedSpinner.setEnabled(false);
					speed = 4;
					break;
				case "High":
					speedSpinner.setEnabled(false);
					speed = 8;
					break;
				case "Extreme":
					speedSpinner.setEnabled(false);
					speed = 16;
					break;
				case "Custom":
					speedSpinner.setEnabled(true);
					break;
				}
				speedSpinner.setValue(speed);//speedSpinner will fire the selected speed to SimulationPanel
			}
		});
		speedSpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner speedSpinner = (JSpinner) e.getSource();
				int speed = (int) speedSpinner.getValue();
				fireSpeedSelected(speed);
			}
		});
		setLayout(new GridLayout(1, 3));
		add(speedLabel);
		add(speedCombo);
		add(speedSpinner);
	}
	private void fireSpeedSelected(int speed) {
		speedPanelListener.speedSelected(speed);
	}
	
	public void setSpeedPanelListener(SpeedPanelListener speedPanelListener) {
		this.speedPanelListener = speedPanelListener;
	}
}
