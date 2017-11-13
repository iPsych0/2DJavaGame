package dev.ipsych0.mygame.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Text {
	
	/*
	 * Draws a String to the screen with specified font, colour and centred/not centred
	 */
	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color color, Font font) {
		g.setColor(color);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
		
		if(center) {
			FontMetrics fm = g.getFontMetrics(font);
			x = xPos - fm.stringWidth(text) / 2;
			y = (yPos - fm.getHeight() / 2) + fm.getAscent();
		}
		
		g.drawString(text, x, y);
	}

}
