package com.gameoflife.ui;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Panel displaying information about the Game of Life.
 * Loads content from an RTF resource file.
 *
 * @author TaraPrasad
 */
public class AboutPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AboutPanel.class.getName());
    private static final String ABOUT_FILE = "/about.rtf";

    private final Runnable backButtonCallback;

    /**
     * Creates a new AboutPanel.
     *
     * @param backButtonCallback Callback to invoke when back button is clicked
     */
    public AboutPanel(Runnable backButtonCallback) {
        this.backButtonCallback = backButtonCallback;
        setLayout(new BorderLayout());
        setBackground(Color.ORANGE);

        JEditorPane editor = createEditor();
        loadAboutContent(editor);

        JScrollPane editorScrollPane = new JScrollPane(editor);
        add(editorScrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AboutPanel.this.backButtonCallback != null) {
                    AboutPanel.this.backButtonCallback.run();
                }
            }
        });
        add(backButton, BorderLayout.SOUTH);
    }

    /**
     * Creates and configures the RTF editor pane.
     *
     * @return Configured JEditorPane
     */
    private JEditorPane createEditor() {
        RTFEditorKit rtf = new RTFEditorKit();
        JEditorPane editor = new JEditorPane();
        editor.setEditorKit(rtf);
        editor.setForeground(Color.BLACK);
        editor.setBackground(Color.ORANGE);
        editor.setEditable(false);
        return editor;
    }

    /**
     * Loads about content from RTF file.
     *
     * @param editor Editor pane to load content into
     */
    private void loadAboutContent(JEditorPane editor) {
        try (InputStream inputStream = getClass().getResourceAsStream(ABOUT_FILE)) {
            if (inputStream == null) {
                throw new IOException("About file not found: " + ABOUT_FILE);
            }

            RTFEditorKit rtf = new RTFEditorKit();
            rtf.read(inputStream, editor.getDocument(), 0);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load about file", e);
            showError("Could not load about file: " + e.getMessage());
        } catch (BadLocationException e) {
            LOGGER.log(Level.SEVERE, "Error reading about content", e);
            showError("Error displaying about content");
        }
    }

    /**
     * Displays an error message dialog.
     *
     * @param message Error message to display
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
