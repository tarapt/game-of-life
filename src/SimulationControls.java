import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * @author TaraPrasad, Aneesh, Saurabh
 *
 */

public class SimulationControls extends JPanel implements ActionListener {

	private SimulationControlsListener simulationControlsListener;
	private JGradientButton nextGenerationButton;
	private JGradientButton pauseButton;
	private JGradientButton resumeButton;
	private JGradientButton addCellsButton;
	private JGradientButton removeCellsButton;
	private JGradientButton clearButton;
	private JGradientButton restartButton;
	private JGradientButton startButton;
	private JGradientButton resetButton;

	public SimulationControls() {
		Color lightYellow = new Color(255, 255, 153);
		setBackground(lightYellow);
		setLayout(new GridLayout(3, 3));
		// Creating the control buttons
		nextGenerationButton = JGradientButton.newInstance("Next Generation");
		pauseButton = JGradientButton.newInstance("Pause");
		resumeButton = JGradientButton.newInstance("Resume");
		addCellsButton = JGradientButton.newInstance("Add Cells Mode");
		removeCellsButton = JGradientButton.newInstance("Remove Cells Mode");
		clearButton = JGradientButton.newInstance("Clear Button");
		restartButton = JGradientButton.newInstance("Restart With Recent Initial Setup");
		startButton = JGradientButton.newInstance("Start");
		resetButton = JGradientButton.newInstance("Reset");
		// Adding the control buttons to the panel
		add(startButton);
		add(restartButton);
		add(nextGenerationButton);
		add(pauseButton);
		add(resumeButton);
		add(resetButton);
		add(addCellsButton);
		add(removeCellsButton);
		add(clearButton);

		// Adding action listeners to each control button
		pauseButton.addActionListener(this);
		nextGenerationButton.addActionListener(this);
		resumeButton.addActionListener(this);
		addCellsButton.addActionListener(this);
		removeCellsButton.addActionListener(this);
		clearButton.addActionListener(this);
		restartButton.addActionListener(this);
		startButton.addActionListener(this);
		resetButton.addActionListener(this);
	}

	public void setSimulationControlsListener(SimulationControlsListener simulationControlsListener) {
		this.simulationControlsListener = simulationControlsListener;
	}

	public void setInitialConfiguration() {
		addCellsButton.setEnabled(true);
		removeCellsButton.setEnabled(true);
		clearButton.setEnabled(true);
		nextGenerationButton.setEnabled(false);
		pauseButton.setEnabled(false);
		resumeButton.setEnabled(false);
		restartButton.setEnabled(false);
		startButton.setEnabled(false);
		resetButton.setEnabled(false);
	}
	
	public void setStartableConfiguration() {
		nextGenerationButton.setEnabled(true);
		startButton.setEnabled(true);
	}
	
	public void setRunningConfiguration() {
		pauseButton.setEnabled(true);
		restartButton.setEnabled(true);
		resetButton.setEnabled(true);
		startButton.setEnabled(false);
		nextGenerationButton.setEnabled(false);
	}
	
	public void setSingleStepConfiguration() {
		restartButton.setEnabled(true);
		resetButton.setEnabled(true);
	}
	
	public void setPausedConfiguration() {
		nextGenerationButton.setEnabled(true);
		pauseButton.setEnabled(false);
		resumeButton.setEnabled(true);
	}
	
	public void setResumedConfiguration() {
		resumeButton.setEnabled(false);
		nextGenerationButton.setEnabled(false);
		pauseButton.setEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(pauseButton))
			firePauseButtonClicked();
		else if (e.getSource().equals(resumeButton))
			fireResumeButtonClicked();
		else if (e.getSource().equals(nextGenerationButton))
			fireNextGenerationButtonClicked();
		else if (e.getSource().equals(addCellsButton))
			fireAddCellsButtonClicked();
		else if (e.getSource().equals(removeCellsButton))
			fireRemoveCellsButtonClicked();
		else if (e.getSource().equals(clearButton))
			fireClearButtonClicked();
		else if (e.getSource().equals(restartButton))
			fireRestartButtonClicked();
		else if (e.getSource().equals(startButton))
			fireStartButtonClicked();
		else if (e.getSource().equals(resetButton))
			fireResetButtonClicked();
	}

	private void fireResetButtonClicked() {
		if (simulationControlsListener != null)
			simulationControlsListener.resetButtonClicked();
	}

	private void fireStartButtonClicked() {
		if (simulationControlsListener != null)
			simulationControlsListener.startButtonClicked();
	}

	private void fireClearButtonClicked() {
		if (simulationControlsListener != null)
			simulationControlsListener.clearButtonClicked();
	}

	private void fireRemoveCellsButtonClicked() {
		if (simulationControlsListener != null)
			simulationControlsListener.removeCellsButtonClicked();
	}

	private void fireAddCellsButtonClicked() {
		if (simulationControlsListener != null)
			simulationControlsListener.addCellsButtonClicked();
	}

	private void fireNextGenerationButtonClicked() {
		if (simulationControlsListener != null)
			simulationControlsListener.nextGenerationButtonClicked();
	}

	private void fireResumeButtonClicked() {
		if (simulationControlsListener != null)
			simulationControlsListener.resumeButtonClicked();
	}

	private void firePauseButtonClicked() {
		if (simulationControlsListener != null)
			simulationControlsListener.pauseButtonClicked();
	}

	private void fireRestartButtonClicked() {
		if (simulationControlsListener != null)
			simulationControlsListener.restartButtonClicked();
	}
}