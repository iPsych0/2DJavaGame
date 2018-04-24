package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.entities.statics.Whirlpool;
import dev.ipsych0.mygame.utils.MapLoader;

public class SwampLand extends World{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle testLandTile;

	public SwampLand(Handler handler, String path, int worldID) {
		super(handler);
		
		this.worldPath = path;
		this.worldID = worldID;

		width = MapLoader.getMapWidth(path);
		height = MapLoader.getMapHeight(path);
		
		loadWorld(path);
		
		entityManager.addEntity(new Lorraine(handler, 732, 640));
		
		entityManager.addEntity(new Tree(handler, 160, 128));
		entityManager.addEntity(new Tree(handler, 128, 128));
		entityManager.addEntity(new Tree(handler, 96, 192));
		entityManager.addEntity(new Tree(handler, 96, 160));
		
		entityManager.addEntity(new Rock(handler, 448, 576));
		
//		entityManager.addEntity(new Scorpion(handler, 160, 400));
//		entityManager.addEntity(new Scorpion(handler, 128, 800));
//		entityManager.addEntity(new Scorpion(handler, 128, 888));
//		entityManager.addEntity(new Scorpion(handler, 128, 944));
//		entityManager.addEntity(new Scorpion(handler, 190, 944));
//		entityManager.addEntity(new Scorpion(handler, 190, 888));
//		entityManager.addEntity(new Scorpion(handler, 190, 800));
		
		entityManager.addEntity(new TeleportShrine(handler, 200, 200));
		
		entityManager.addEntity(new Whirlpool(handler, 672, 432));
		
		testLandTile = new Rectangle(1568, 1300, 32, 200); 

	}
	
	@Override
	public void tick(){
		if(handler.getWorld() == this){
			super.tick();
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(testLandTile)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(1));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(60);
				handler.getPlayer().setY(164);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(1).getClass().getSimpleName());
			}
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(handler.getWorld() == this){
			super.render(g);
			
			g.drawRect((int) (testLandTile.x - handler.getGameCamera().getxOffset()), (int) (testLandTile.y - handler.getGameCamera().getyOffset()), 32, 168);
		}
	}

	public int getWorldID() {
		return worldID;
	}

	public void setWorldID(int worldID) {
		this.worldID = worldID;
	}
}
