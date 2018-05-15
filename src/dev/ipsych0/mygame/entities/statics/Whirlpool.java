package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class Whirlpool extends StaticEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private Animation spinning;
	private boolean isFishing = false;
	private int fishingTimer = 0;
	private int minAttempts = 4, maxAttempts = 9;
	private int random = 0;
	private int attempts = 0;

	public Whirlpool(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		isNpc = true;
		attackable = false;
		spinning = new Animation(125, Assets.whirlpool);
	}

	@Override
	public void tick() {
		spinning.tick();
		if(isFishing) {
			if(Player.isMoving || handler.getMouseManager().isLeftPressed()) {
				fishingTimer = 0;
				speakingTurn = 1;
				isFishing = false;
				return;
			}
			if(random != 0) {
				if(attempts == random) {
					attempts = 0;
					isFishing = false;
					this.active = false;
					this.die();
				}
			}
			
			fishingTimer++;
			
			if(fishingTimer >= 180) {
				System.out.println(random + " and " + attempts);
				int roll = handler.getRandomNumber(1, 100);
	        	if(roll < 60) {
	        		handler.giveItem(Item.regularFish, 1);
	        		handler.sendMsg("You caught something!");
	        		handler.getSkillsUI().getSkill(SkillsList.FISHING).addExperience(10);
	        		attempts++;
	        	}else {
	        		handler.sendMsg("The fish got away...");
	        		attempts++;
	        	}
	        	speakingTurn = 1;
	        	fishingTimer = 0;
	        	
	        	if(attempts == minAttempts - 1) {
	        		random = handler.getRandomNumber(minAttempts, maxAttempts);
	        	}
			}
			
		}
	}
	
	@Override
	public void die(){
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(spinning.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
		postRender(g);
	}

	@Override
	public void interact() {
		if(this.speakingTurn == 0) {
			speakingTurn++;
			return;
		}
		if(this.speakingTurn == 1) {
			handler.sendMsg("Fishing...");
			speakingTurn = 2;
			isFishing = true;
		}
		if(this.speakingTurn == 2) {
			return;
		}
	}

	@Override
	public void postRender(Graphics g) {
		if(isFishing) {
			g.drawImage(Assets.fishingIcon, (int) (handler.getPlayer().getX() - handler.getGameCamera().getxOffset()), (int) (handler.getPlayer().getY() - handler.getGameCamera().getyOffset() - 32 ), width, height, null);
		}
		
	}

	@Override
	public void respawn() {
		handler.getWorld().getEntityManager().addEntity(new Whirlpool(handler, xSpawn, ySpawn));		
	}
	
}
