import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.rtf.RTFEditorKit;

class GroupInfoPanel extends JPanel {
	public GroupInfoPanel(Game game) {
		Game parent = game;
		setBackground(Color.ORANGE);
		// Create an RTF editor window
		RTFEditorKit rtf = new RTFEditorKit();
		JEditorPane editor = new JEditorPane();
		JButton backButton = new JButton("Back");
		add(backButton, BorderLayout.SOUTH);
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.backButtonFromChild();
			}
		});
		editor.setEditorKit(rtf);
		editor.setForeground(Color.BLACK);
		editor.setBackground(Color.ORANGE);
		editor.setEditable(false);

		// This text could be big so add a scroll pane
		JScrollPane editorScrollPane = new JScrollPane(editor);
		add(editorScrollPane, BorderLayout.CENTER);

		// Load an RTF file into the editor
		try {
			FileInputStream fi = new FileInputStream("groupInfo.rtf");
			rtf.read(fi, editor.getDocument(), 0);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("I/O error");
		} catch (BadLocationException e) {
		}
	}
}