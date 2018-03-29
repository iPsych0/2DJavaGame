package dev.ipsych0.mygame.items;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;

public class ItemManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private ArrayList<Item> items;

	public ItemManager(Handler handler){
		this.handler = handler;
		items = new ArrayList<Item>();
	}
	
	public void tick(){
		Iterator<Item> it = items.iterator();
		Collection<Item> deleted = new CopyOnWriteArrayList<Item>();
		while(it.hasNext()){
			Item i = it.next();
			// Checks player's position for any items nearby to pick up
			if(handler.getMouseManager().isRightPressed() && handler.getWorld().getEntityManager().getPlayer().itemPickupRadius().intersects(i.itemPosition(0, 0))){
				if(i.pickUpItem(i)){
					if(i.isPickedUp()){
						deleted.add(i);
					}
				}
			}
			i.tick();
		}
		items.removeAll(deleted);
	}
	
	public void render(Graphics g){
		for(Item i : items)
			i.render(g);
	}
	
	public void addItem(Item i){
		i.setHandler(handler);
		items.add(i);
		
		// Despawn timer for items dropped
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	
		                items.remove(i);
		               
		            }
		        }, 
		        300000
		);
	}
	
	// Getters & Setters

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

}
