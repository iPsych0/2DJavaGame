package dev.ipsych0.mygame.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
import dev.ipsych0.mygame.input.KeyManager;

public class TextBox implements KeyListener {
	
	public int x, y, width, height;
	public String charactersTyped = "";
	public boolean numbersOnly = false;
	private Handler handler;
	private Rectangle bounds;
	private boolean focus = false;
	public int index = 0;
	private StringBuilder sb;
	private boolean loaded = false;
	public static boolean enterPressed = false;
	public static boolean isOpen = false;
	private Color selected = new Color(102, 51, 0, 127);
	private Color notSelected = new Color(102, 51, 0, 78);
	private Color cursorColor = new Color(75, 38, 0);
	private int blinkTimer = 0;
	private String cursor = "|";
	
	public TextBox(Handler handler, int x, int y, int width, int height, boolean numbersOnly) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.numbersOnly = numbersOnly;
		
		bounds = new Rectangle(x,y,width,height);
		
		sb = new StringBuilder(charactersTyped);
		
		handler.getGame().getDisplay().getFrame().addKeyListener(this);
		handler.getGame().getDisplay().getCanvas().addKeyListener(this);
	}
	
	public void tick() {
		if(isOpen) {
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(bounds.contains(mouse) && handler.getMouseManager().isLeftPressed()) {
				focus = true;
				KeyManager.typingFocus = true;
			}
			
			if(!bounds.contains(mouse) && handler.getMouseManager().isLeftPressed()) {
				focus = false;
				KeyManager.typingFocus = false;
			}
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			
			g.setColor(Color.DARK_GRAY);
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
			
			if(focus) {
				
				blinkTimer++;
				
				g.setColor(selected);
				g.fillRect(x + 2, y, width - 4, height);
				
				if(blinkTimer >= 0 && blinkTimer < 30) {
					cursor = "|";
					if(!charactersTyped.isEmpty()) {
						int textWidth = g.getFontMetrics().stringWidth(charactersTyped);
						Text.drawString(g, cursor, (x + (width / 2)) + textWidth / 2 + 2, y + 17, true, cursorColor, Assets.font14);
					}else {
						Text.drawString(g, cursor, x + (width / 2), y + 17, true, cursorColor, Assets.font14);
					}
				}
				else if(blinkTimer == 30) {
					cursor = "";
					Text.drawString(g, cursor, x + (width / 2), y + 17, true, cursorColor, Assets.font14);
				}
				else if(blinkTimer >= 60) {
					blinkTimer = 0;
				}
			}else {
				g.setColor(notSelected);
				g.fillRect(x + 2, y, width - 4, height);
			}
			
			if(!charactersTyped.isEmpty())
				Text.drawString(g, charactersTyped, x + (width / 2), y + 16, true, Color.YELLOW, Assets.font14);
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(isOpen) {
			if(focus) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					if(charactersTyped.isEmpty()) {
						return;
					}
					enterPressed = true;
					charactersTyped = sb.toString();
					sb.setLength(0);
					index = 0;
					return;
				}
				
				if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
					if(index > 0) {
						sb.deleteCharAt(index - 1);
						index--;
						charactersTyped = sb.toString();
						return;
					}
				}
			
				if(numbersOnly) {
					if(!Character.isDigit(e.getKeyChar())) {
						return;
					}else {
						if(index <= 8) {
							sb.append(e.getKeyChar());
							index++;
							charactersTyped = sb.toString();
						}else {
							return;
						}
					}
				}else {
					if(index <= 8) {
						if(Character.isAlphabetic(e.getKeyChar())) {
							sb.append(e.getKeyChar());
							index++;
							charactersTyped = sb.toString();
							}else {
								return;
							}
						}
				}
			}
		}else {
			sb.setLength(0);
			charactersTyped = sb.toString();
			index = 0;
			
		}
	}

	public String getCharactersTyped() {
		return charactersTyped;
	}

	public void setCharactersTyped(String charactersTyped) {
		this.charactersTyped = charactersTyped;
	}

}
