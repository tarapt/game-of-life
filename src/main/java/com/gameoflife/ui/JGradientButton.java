package com.gameoflife.ui;

import com.gameoflife.util.GameConfig;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Custom JButton with gradient background.
 * Provides a visually appealing button with gradient paint.
 *
 * @author TaraPrasad
 */
public final class JGradientButton extends JButton {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new gradient button with the specified text.
     *
     * @param buttonName Text to display on button
     */
    private JGradientButton(String buttonName) {
        super(buttonName);
        setFont(new Font("Dialog", Font.PLAIN, GameConfig.BUTTON_FONT_SIZE));
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            GradientPaint gradient = new GradientPaint(
                new Point(0, 0),
                GameConfig.BUTTON_GRADIENT_START,
                new Point(0, getHeight()),
                GameConfig.BUTTON_GRADIENT_END
            );
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
        } finally {
            g2.dispose();
        }

        super.paintComponent(g);
    }

    /**
     * Factory method to create a new gradient button.
     *
     * @param buttonName Text to display on button
     * @return New JGradientButton instance
     */
    public static JGradientButton newInstance(String buttonName) {
        return new JGradientButton(buttonName);
    }
}
