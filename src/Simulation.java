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
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

/**
 * Simulation is a JComponent consisting of two JPanel objects - 1) grid : Grid
 * 2) SimulationControls : SimulationControls
 * 
 * @author TaraPrasad
 */
public class Simulation extends JComponent implements ActionListener {
	private Game game;
	private Grid grid;
	private SimulationControls simulationControls;
	private JPanel statusPanel;
	private JLabel generationLabel;
	private SpeedPanel speedPanel;
	private JGradientButton backButton;
	private SimulationState simulationState;
	private Generation generation;
	private Timer timer;// Swing Timer - Uses a separate timer thread for
						// execution
	private boolean[][] cells;
//	private boolean[][] firstGeneration; // Automatically set the first time.
	private int waitTime;// time to wait in milliseconds
	private boolean addingCells;// Make sure that removingCells is not true at
	private boolean removingCells;// the same time as addingCells and
									// vice-versa.
	private int generationNumber;

	// maintain ratio 1:3
	public static final int ROWS = 60;
	public static final int COLUMNS = 180;
	public static final int ORIGIN_ROW = 20;
	public static final int ORIGIN_COLUMN = 40;
	
	final JFileChooser fileDialog = new JFileChooser();

	private enum SimulationState {
		INITIAL, STARTABLE, RUNNING, PAUSED, RESUMED, SINGLESTEP
	}

	public Simulation(Game game) {
		this.game = game;
		// Initialize variables
		generationNumber = 1;
		waitTime = 1000;
		// Create Objects
		backButton = JGradientButton.newInstance("Back");
		cells = new boolean[ROWS][COLUMNS];
		statusPanel = new JPanel();
		speedPanel = new SpeedPanel();
		generationLabel = new JLabel("Generation: " + generationNumber);
		grid = new Grid(cells);
		generation = new Generation(cells);
		timer = new Timer(waitTime, this);// this refers to the listener (here its the Simulation class
											// implementing a Listener)
		simulationControls = new SimulationControls();
		setSimulationState(SimulationState.INITIAL);

		// Setting the Layout
		setSize(850, 550);
		setLayout(new GridBagLayout());
		setUpLayout();
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.backButtonFromChild();
			}
		});
		speedPanel.setSpeedPanelListener(new SpeedPanelListener() {

			@Override
			public void speedSelected(int speed) {
				if (speed != 0) {
					waitTime = (int) 1000 / speed;
					timer.setDelay(waitTime);
				}
			}

		});
		simulationControls.setSimulationControlsListener(new SimulationControlsListener() {
			// When re-assigning cells make sure to re-assign
			// the Grid class copy of cells also because they both need to point
			// to the same object at all times.
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
				resetButtonClicked();
				int returnVal = fileDialog.showOpenDialog(Simulation.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileDialog.getSelectedFile();
					loadConfigFromFile(file);
					generationNumber = 1;
					generation = new Generation(cells);
					grid.updateGrid(cells);
					updateStatusLabel();
					simulationControls.setLoadFileConfiguration();
				}
			}

			private void loadConfigFromFile(File file) {
				try {
					Scanner sc = new Scanner(file);
					sc.nextLine();
					int row = ORIGIN_ROW;
					while (sc.hasNextLine()) {
			            String line = sc.nextLine();
			            for (int i = 0; i < line.length(); i++) {
			            	if(line.charAt(i) == '*' || line.charAt(i) == 'O') 
			            		cells[row][(ORIGIN_COLUMN + i) % COLUMNS] = true;
			            	else
			            		cells[row][(ORIGIN_COLUMN + i) % COLUMNS] = false;
						}
			            row = (row + 1) % ROWS;
			        }
			        sc.close();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(new JFrame(), "Couldn't read the file.", "Dialog",
					        JOptionPane.ERROR_MESSAGE);
				}
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
				setSimulationState(SimulationState.INITIAL);
				generationNumber = 1;
				cells = new boolean[ROWS][COLUMNS];
				generation = new Generation(cells);
				grid.updateGrid(cells);
				updateStatusLabel();
			}
		});
		// Adding Mouse Listeners
		grid.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				/**
				 * Toggles cells state
				 */
				super.mousePressed(e);
				double width = (double) grid.getWidth() / cells[0].length;
				double height = (double) grid.getHeight() / cells.length;
				int row = (int) (e.getY() / height);
				int column = (int) (e.getX() / width);
				if (((row >= 0) && (row < cells.length)) && ((column >= 0) && (column < cells[0].length))) {
					if (cells[row][column] == true)
						cells[row][column] = false;
					else
						cells[row][column] = true;
				}
				grid.repaint();
				if (simulationState == SimulationState.INITIAL) {
					setSimulationState(SimulationState.STARTABLE);
				}
			}

		});
		grid.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				/*
				 * Takes decision based on the boolean variables addingCells or removingCells
				 * values
				 */
				double width = (double) grid.getWidth() / cells[0].length;
				double height = (double) grid.getHeight() / cells.length;
				int row = (int) (e.getY() / height);
				int column = (int) (e.getX() / width);
				if (addingCells) {
					if (((row >= 0) && (row < cells.length)) && ((column >= 0) && (column < cells[0].length))) {
						cells[row][column] = true;
					}
				}
				if (removingCells) {
					if (((row >= 0) && (row < cells.length)) && ((column >= 0) && (column < cells[0].length))) {
						cells[row][column] = false;
					}
				}
				grid.repaint();
				if (simulationState == SimulationState.INITIAL) {
					setSimulationState(SimulationState.STARTABLE);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});
	}

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

	private void displayNextGeneration() {
//		if (generationNumber == 1)
//			firstGeneration = cells.clone();
		generation = new Generation(cells.clone());
		cells = generation.getNextGeneration().clone();
		grid.updateGrid(cells);
		generationNumber++;
		updateStatusLabel();
	}

	private void setUpLayout() {
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		generationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		generationLabel.setFont(new Font("", 15, 20));
		statusPanel.add(generationLabel);

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		// First Row
		gc.gridy = 0;
		gc.weighty = 5;
		gc.weightx = 1;
		grid.setPreferredSize(new Dimension(getWidth(), (int) (getHeight() * 0.8)));
		add(grid, gc);
		// Second Row
		gc.gridy = 1;
		gc.weighty = 1;
		gc.weightx = 1;
		simulationControls.setPreferredSize(new Dimension(getWidth(), (int) (getHeight() * 0.16)));
		gc.anchor = GridBagConstraints.SOUTH;
		add(simulationControls, gc);
		// Third Row
		gc.fill = GridBagConstraints.RELATIVE;
		gc.gridy = 2;
		gc.gridx = 0;
		gc.weighty = 0.2;
		gc.anchor = GridBagConstraints.LINE_START;
		add(backButton, gc);

		gc.fill = GridBagConstraints.RELATIVE;
		gc.gridy = 2;
		gc.gridx = 0;
		gc.weighty = 0.2;
		statusPanel.setPreferredSize(new Dimension((int) (getWidth() * 0.2), (int) (getHeight() * 0.04)));
		gc.insets = new Insets(0, 100, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		add(statusPanel, gc);

		gc.fill = GridBagConstraints.REMAINDER;
		gc.gridx = 0;
		speedPanel.setPreferredSize(new Dimension((int) (getWidth() * 0.8), (int) (getHeight() * 0.04)));
		gc.anchor = GridBagConstraints.EAST;
		add(speedPanel, gc);

	}

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
