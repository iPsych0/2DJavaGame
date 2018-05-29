package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import dev.ipsych0.mygame.Handler;

public class WorldHandler implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private ArrayList<World> worlds;

	public WorldHandler(Handler handler, Island island){
		this.handler = handler;
		worlds = new ArrayList<World>();
		addWorld(island);
	}
	
	public void tick(){
		Iterator<World> it = worlds.iterator();
		while(it.hasNext()){
			World w = it.next();
			if(w.equals(handler.getWorld()))
				w.tick();
		}
	}
	
	public void render(Graphics g){
		for(World w : worlds){
			if(w.equals(handler.getWorld()))
				w.render(g);
		}
	}
	
	public void addWorld(World w){
		worlds.add(w);
	}

	public ArrayList<World> getWorlds() {
		return worlds;
	}

	public void setWorlds(ArrayList<World> worlds) {
		this.worlds = worlds;
	}
}
