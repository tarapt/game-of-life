import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Insets;

/**
 * Describes the rules of Game of Life
 * 
 * @author TaraPrasad
 */
public class StartPanel extends JPanel {
	private JGradientButton rulesButton;
	private StartPanelListener startPanelListener;
	private JGradientButton enterButton;
	private JGradientButton exitButton;
	private JLabel titleLabel;
	private JGradientButton about;
	private JGradientButton groupInfo;

	public StartPanel() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 618 };
		gridBagLayout.rowHeights = new int[] { 12, 0, -66, 116, 70, 65, 71, 63, 66, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0 };
		setLayout(gridBagLayout);
		setBackground(new Color(0x43AC6A));
		setUpGridBagLayout();
		rulesButton.addActionListener(new ActionListener() {
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
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireAboutButtonClicked();
			}
		});
		groupInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireGroupInfoButtonClicked();
			}
		});
	}

	private void fireRulesButtonClicked() {
		if (startPanelListener != null) 
			startPanelListener.rulesButtonClicked();
	}

	private void fireGroupInfoButtonClicked() {
		if (startPanelListener != null) 
			startPanelListener.groupInfoButtonClicked();
	}

	private void fireAboutButtonClicked() {
		if (startPanelListener != null) 
			startPanelListener.aboutButtonClicked();
	}

	public void setStartPanelListener(StartPanelListener startPanelListener) {
		this.startPanelListener = startPanelListener;
	}

	private void fireEnterButtonClicked() {
		if (startPanelListener != null) 
			startPanelListener.enterButtonClicked();
	}

	private void fireExitButtonClicked() {
		if (startPanelListener != null)
			startPanelListener.exitButtonClicked();
	}

	private void setUpGridBagLayout() {
		titleLabel = new JLabel("Game of Life Simulation In Applet");
		titleLabel.setFont(new Font("Dialog", Font.PLAIN, 40));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.ipadx = 20;
		gbc_label.gridx = 0;
		gbc_label.gridy = 3;
		add(titleLabel, gbc_label);
		enterButton = JGradientButton.newInstance("Enter Simulation");
		enterButton.setFont(new Font("", 15, 20));

		GridBagConstraints gbc_enter = new GridBagConstraints();
		gbc_enter.insets = new Insets(0, 0, 5, 0);
		gbc_enter.ipadx = 28;
		gbc_enter.gridx = 0;
		gbc_enter.gridy = 4;
		add(enterButton, gbc_enter);

		rulesButton = JGradientButton.newInstance("Rules");
		rulesButton.setFont(new Font("", 15, 20));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.ipadx = 125;
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 5;
		add(rulesButton, gbc_btnNewButton_1);

		about = JGradientButton.newInstance("About Game of Life");
		about.setFont(new Font("Dialog", Font.PLAIN, 20));
		GridBagConstraints gbc_about = new GridBagConstraints();
		gbc_about.insets = new Insets(0, 0, 5, 0);
		gbc_about.gridx = 0;
		gbc_about.gridy = 6;
		add(about, gbc_about);

		groupInfo = JGradientButton.newInstance("Group Info");
		groupInfo.setFont(new Font("Dialog", Font.PLAIN, 20));
		GridBagConstraints gbc_groupInfo = new GridBagConstraints();
		gbc_groupInfo.ipadx = 78;
		gbc_groupInfo.insets = new Insets(0, 0, 5, 0);
		gbc_groupInfo.gridx = 0;
		gbc_groupInfo.gridy = 7;
//		add(groupInfo, gbc_groupInfo);

		exitButton = JGradientButton.newInstance("Exit");
		exitButton.setFont(new Font("", 15, 20));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.ipadx = 139;
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 8;
		add(exitButton, gbc_btnNewButton_2);

	}
}
