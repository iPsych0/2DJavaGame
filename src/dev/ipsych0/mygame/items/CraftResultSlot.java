package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.gfx.Assets;

public class CraftResultSlot {
	
	private int x, y;
	public static final int SLOTSIZE = 32;
	private ItemStack itemStack;
	public static boolean stackable = true;
	private Rectangle bounds;
	
	public CraftResultSlot(int x, int y, ItemStack itemStack) {
		this.x = x;
		this.y = y;
		this.itemStack = itemStack;
		bounds = new Rectangle(x, y, SLOTSIZE, SLOTSIZE);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, SLOTSIZE, SLOTSIZE);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, SLOTSIZE, SLOTSIZE);
		
		if(itemStack != null) {
			g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
			g.setColor(Color.YELLOW);
			g.setFont(Assets.font14);
			g.drawString(Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21);
		}
	}
	
	public void addItem(Item item, int amount) {
		
		this.itemStack = new ItemStack(item, amount);
			
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
