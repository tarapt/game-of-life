import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JButton;

public final class JGradientButton extends JButton{
    JGradientButton(String buttonName){
        super(buttonName);
        setFont(new Font("",20 ,25 ));
        setContentAreaFilled(false);
        setFocusPainted(false); 
    }
    
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setPaint(new GradientPaint(new Point(0, 0), Color.WHITE, new Point(0, getHeight()), new Color(0xBD8CBF)));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }

    public static final JGradientButton newInstance(String buttonName){
        return new JGradientButton(buttonName);
    }
}
