package dev.ipsych0.mygame.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;

public class ItemManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private CopyOnWriteArrayList<Item> items;
	private Collection<Item> deleted;
	private Collection<Item> added;

	public ItemManager(Handler handler){
		this.handler = handler;
		items = new CopyOnWriteArrayList<Item>();
		deleted = new CopyOnWriteArrayList<Item>();
		added = new CopyOnWriteArrayList<Item>();
	}
	
	public void tick(){
		Iterator<Item> it = items.iterator();
		while(it.hasNext()){
			Item i = it.next();
			
			// Checks player's position for any items nearby to pick up
			if(handler.getMouseManager().isRightPressed() && handler.getWorld().getEntityManager().getPlayer().itemPickupRadius().intersects(i.itemPosition(0, 0))){
				if(!handler.getPlayer().hasRightClickedUI(new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1))) {
					if(i.pickUpItem(i)){
						if(i.isPickedUp()){
							deleted.add(i);
						}
					}
				}
			}
			i.tick();
		}
		
		// If non-worldspawn Items are dropped, start timer for despawning
		if(added.size() > 0) {
			for(Item i : added) {
				i.startRespawnTimer();
				if(i.getRespawnTimer() == 0) {
					deleted.add(i);
					added.remove(i);
				}
			}
		}
		
		// If Item's timer is 0, remove the items from the world.
		if(deleted.size() > 0) {
			items.removeAll(deleted);
		}
	}
	
	public void render(Graphics g){
		for(Item i : items) {
			i.render(g);
			
			// Draw item bounds for picking up
//			g.drawRect((int)(i.itemPosition(0, 0).x - handler.getGameCamera().getxOffset()), (int)(i.itemPosition(0, 0).y - handler.getGameCamera().getyOffset()), i.itemPosition(0, 0).width, i.itemPosition(0, 0).height);
		}
	}
	
	public void addItem(Item i){
		i.setHandler(handler);
		items.add(i);
		added.add(i);
	}
	
	public void addItem(Item i, boolean isWorldSpawn){
		i.setHandler(handler);
		items.add(i);
	}
	
	// Getters & Setters

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public CopyOnWriteArrayList<Item> getItems() {
		return items;
	}

	public void setItems(CopyOnWriteArrayList<Item> items) {
		this.items = items;
	}

}
