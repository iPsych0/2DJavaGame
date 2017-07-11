package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.EntityManager;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine1;
import dev.ipsych0.mygame.entities.statics.TeleportShrine2;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemManager;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.mapeditor.MiniMap;
import dev.ipsych0.mygame.tiles.Ambiance;
import dev.ipsych0.mygame.tiles.Tiles;

public class SwampLand extends World{
	
	private Rectangle nextLevelTile;

	public SwampLand(Handler handler, Player player, String path) {
		super(handler);
		
		mapLoader = new MapLoader();
		loadGroundTiles(path);
		loadTerrainTiles(path);
		loadAmbianceTiles(path);
		
		entityManager.addEntity(new Lorraine(handler, 732, 640));
		
		entityManager.addEntity(new Tree(handler, 160, 128));
		entityManager.addEntity(new Tree(handler, 128, 128));
		entityManager.addEntity(new Tree(handler, 96, 192));
		entityManager.addEntity(new Tree(handler, 96, 160));
		
		entityManager.addEntity(new Rock(handler, 448, 576));
		
		entityManager.addEntity(new Scorpion(handler, 160, 576));
		entityManager.addEntity(new Scorpion(handler, 128, 800));
		entityManager.addEntity(new Scorpion(handler, 128, 888));
		entityManager.addEntity(new Scorpion(handler, 128, 944));
		entityManager.addEntity(new Scorpion(handler, 190, 944));
		entityManager.addEntity(new Scorpion(handler, 190, 888));
		entityManager.addEntity(new Scorpion(handler, 190, 800));
		
		entityManager.addEntity(new TeleportShrine2(handler, 200, 200));
		entityManager.addEntity(new TeleportShrine1(handler, 200, 168));
		
		nextLevelTile = new Rectangle(1568, 1300, 32, 200); 
		
		getInventory().getItemSlots().get(getInventory().findFreeSlot(Item.testSword)).addItem(Item.testSword, 1);
	}
	
	public void tick(){
		if(handler.getWorld() == this){
			inventory.tick();
			equipment.tick();
			itemManager.tick();
			entityManager.tick();
			chatWindow.tick();
			sparkles.tick();
			statScreen.tick();
			miniMap.tick();
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(nextLevelTile)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(1));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(60);
				handler.getPlayer().setY(164);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(1).getClass().getSimpleName());
				handler.getWorld().getChatWindow().sendMessage("X = " + getEntityManager().getPlayer().getX() + " and Y = " + getEntityManager().getPlayer().getY());
			}
		}
	}
	
	public void render(Graphics g){
		if(handler.getWorld() == this){
			// Set variables for rendering only the tiles that show on screen
			int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tiles.TILEWIDTH);
			int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tiles.TILEWIDTH + 1);
			int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tiles.TILEHEIGHT);
			int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tiles.TILEHEIGHT + 1);
			
			// Render the tiles
			for(int y = yStart; y < yEnd; y++){
				for(int x = xStart; x < xEnd; x++){
					getTile(x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
							(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
				}
			}
			
			// Render the terrain tiles
			for(int y = yStart; y < yEnd; y++){
				for(int x = xStart; x < xEnd; x++){
					getTerrain(x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
							(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
				}
			}
			
			// Render the ambiance tiles
			for(int y = yStart; y < yEnd; y++){
				for(int x = xStart; x < xEnd; x++){
					if(getAmbiance(x, y) == Ambiance.sparkleTile){
						g.drawImage(sparkles.getCurrentFrame(), (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
								(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()), null);
					}
					getAmbiance(x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
							(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
				}
			}
			
			// Items
			
			itemManager.render(g);
			
			// Entities
			entityManager.render(g);
			
			// NPC ChatWindow
			
			chatWindow.render(g);
			
			// MiniMap
			
			inventory.render(g);
			equipment.render(g);
			statScreen.render(g);
			miniMap.render(g);
			
			g.drawRect((int) (nextLevelTile.x - handler.getGameCamera().getxOffset()), (int) (nextLevelTile.y - handler.getGameCamera().getyOffset()), 32, 168);
		}
	}
}
