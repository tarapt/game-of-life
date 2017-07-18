import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;
/**
 * 
 *@author TaraPrasad, Aneesh, Sourabh
 *
 */

public class Grid extends JPanel {
	private double width;
	private double height;
	private boolean[][] cells;

	public Grid(boolean[][] cells) {
		this.cells = cells;
		setBackground(new Color(0xCC1100));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// Draw the cells
		drawCells(g2);
		//Draw the lines
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2.0f));
		drawLines(g2);
		g2.setStroke(oldStroke);
		/*Filling – is a process of painting the shape’s interior with solid color or a color gradient, or a texture pattern
		Stroking – is a process of drawing a shape’s outline applying stroke width, line style, and color attribute
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2.0f,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		drawLines(g2);
		g2.setStroke(oldStroke);*/
	}

	private void drawCells(Graphics g) {
		width = (double) this.getWidth() / cells[0].length;
		height = (double) this.getHeight() / cells.length;
		g.setColor(Color.GREEN);
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[0].length; column++) {
				if (cells[row][column] == true) {
					g.fillRect((int) Math.round (column * width), (int) Math.round (row * height), (int) Math.round( width + 1), (int)  Math.round(height));
				}
			}
		}
	}

	private void drawLines(Graphics g) {
		width = (double) this.getWidth() / cells[0].length;
		height = (double) this.getHeight() / cells.length;
		g.setColor(Color.BLACK);
		for (int column = 0; column < cells[0].length + 1; column++) {
			g.drawLine((int) Math.round(column * width), 0, (int) Math.round(column * width), this.getHeight());
		}
		for (int row = 0; row < cells.length + 1; row++) {
			g.drawLine(0, (int) Math.round(row * height), this.getWidth(), (int) Math.round(row * height));
		}
	}
	public void updateGrid(boolean[][] cells) {
		this.cells = cells;//Store new reference
		repaint();
	}

	public void clearCells() {
		width = (double) this.getWidth() / cells[0].length;
		height = (double) this.getHeight() / cells.length;
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[0].length; column++) {
				if (cells[row][column] == true) {
					cells[row][column] = false;
				}
			}
		}
		repaint();
	}
}
