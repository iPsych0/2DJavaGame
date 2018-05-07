package dev.ipsych0.mygame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.hpoverlay.HPOverlay;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.Text;

public abstract class Entity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	public static boolean isCloseToNPC = false;
	protected int health;
	public static final int DEFAULT_HEALTH = 100;
	protected int maxHealth = DEFAULT_HEALTH;
	protected boolean active = true;
	protected boolean attackable = true;
	protected boolean isNpc = false;
	protected boolean isShop = false;
	protected boolean drawnOnMap = false;
	protected boolean damaged = false;
	protected boolean staticNpc = false;
	protected boolean shopping = false;
	protected boolean solid = true;
	protected Entity damageDealer, damageReceiver;
	protected int speakingTurn = 1;
	protected int speakingCheckpoint = 0;
	private int ty = 0;
	protected ChatDialogue chatDialogue;
	protected boolean overlayDrawn = true;
	protected int lastHit = 0;
	protected boolean inCombat = false;
	protected int combatTimer = 0;
	
	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = DEFAULT_HEALTH;
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	// Abstract Methods (EVERY object that is an Entity, MUST HAVE these methods)
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void postRender(Graphics g);
	
	public abstract void die();
	
	public abstract void interact();
	
	public abstract String[] getEntityInfo(Entity hoveringEntity);
	
	/*
	 * Checks the collision for Entities
	 * @returns: true if collision, false if no collision
	 */
	public boolean checkEntityCollisions(float xOffset, float yOffset){
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if(!e.solid)
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	
	/*
	 * Checks if the distance between the player and the closest Entity is <= 64px
	 */
	public boolean playerIsNearNpc(){
		// Looks for the closest entity and returns that entity
		if(distanceToEntity(((int)getClosestEntity().getX() + getClosestEntity().getWidth() / 2), ((int)getClosestEntity().getY() + + getClosestEntity().getHeight() / 2),
				((int)handler.getPlayer().getX() + handler.getPlayer().getWidth() / 2), ((int)handler.getPlayer().getY() + handler.getPlayer().getHeight() / 2)) <= Tiles.TILEWIDTH * 2){
			// Interact with the respective speaking turn
			isCloseToNPC = true;
			return true;
		}else {
			// Out of range
			isCloseToNPC = false;
			return false;
		}
					
	}
	
	/*
	 * Returns the closest Entity to the Player
	 */
	public Entity getClosestEntity(){
		double closestDistance;
		Entity closestEntity;
		HashMap<Double, Entity> hashMap = new HashMap<Double, Entity>();
		ArrayList<Double> pythagoras = new ArrayList<Double>();
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(!e.isNpc()){
				continue;
			}
			if(e.equals(handler.getPlayer())){
				continue;
			}
			
			int dx = (int) ((handler.getPlayer().getX() + handler.getPlayer().getWidth() / 2) - (e.getX() + e.getWidth() / 2));
		    int dy = (int) ((handler.getPlayer().getY() + handler.getPlayer().getHeight() / 2) - (e.getY() + e.getHeight() / 2));
		    hashMap.put(Math.sqrt(dx * dx + dy * dy), e);
		    pythagoras.add(Math.sqrt(dx * dx + dy * dy));
		    Collections.sort(pythagoras);
		}
		closestDistance = pythagoras.get(0);
		closestEntity = hashMap.get(closestDistance);
		return closestEntity;
	}
	
	/*
	 * Returns the damage an Entity should deal (Combat formula)
	 * NOTE: OVERRIDE THIS METHOD FOR SPECIFIC ENTITIES FOR CUSTOM DAMAGE FORMULAS!!!
	 */
	public int getDamage(Entity dealer, Entity receiver) {
		// Default damage formula
		Creature d = (Creature) dealer;
		Creature r = (Creature) receiver;
		return (int) Math.floor((d.getBaseDamage() + d.getPower()) - (r.getDefence() * 0.75));
	}
	
	/*
	 * Deals the damage (subtracts the damage from HP)
	 * @params: dealer = the Entity that deals the damage
	 * 			receiver = the Entity that receives the damage
	 */
	public void damage(Entity dealer, Entity receiver){
		damageDealer = dealer;
		damageReceiver = receiver;
		damageReceiver.health -= damageDealer.getDamage(damageDealer, receiver);
		damageReceiver.damaged = true;
		damageReceiver.ty = 0;
		damageReceiver.lastHit = 0;
		damageReceiver.combatTimer = 0;
		damageReceiver.inCombat = true;
		if(damageDealer.equals(handler.getPlayer())) {
			damageDealer.setInCombat(true);
			damageDealer.combatTimer = 0;
		}

		if(damageReceiver.health <= 0){
			damageReceiver.active = false;
			damageReceiver.die();
		}
	}
	
	/*
	 * Drawing the hitsplat
	 */
	public void drawDamage(Entity dealer, Graphics g) {
		if(damaged) {
			ty--;
			damageReceiver.lastHit++;
			//g.setColor(Color.YELLOW);
			//g.fillRect((int) (x - handler.getGameCamera().getxOffset() + 8), (int) (y - handler.getGameCamera().getyOffset() + 24 + ty), 20, 16);
			
			Text.drawString(g, String.valueOf(dealer.getDamage(dealer, damageReceiver)) ,
					(int) (x - handler.getGameCamera().getxOffset() + 10), (int) (y - handler.getGameCamera().getyOffset() + 36 + ty), false, Color.RED, Assets.font32);
			
			if(damageReceiver.lastHit == 45) {
				damageReceiver.damaged = false;
				damageReceiver.lastHit = 0;
			}
		}
	}
	
	public void drawHP(Graphics g) {
		g.setColor(HPOverlay.hpColorRed);
		g.fillRoundRect((int) (x - handler.getGameCamera().getxOffset() - 6),
				(int) (y - handler.getGameCamera().getyOffset() - 8), 44, 6, 0, 4);
		g.setColor(HPOverlay.hpColorRedOutline);
		g.drawRoundRect((int) (x - handler.getGameCamera().getxOffset() - 6),
				(int) (y - handler.getGameCamera().getyOffset() - 8), 44, 6, 0, 4);
		
		g.setColor(HPOverlay.hpColorGreen);
		if(this.getHealth() >= this.getMaxHealth()) {
			g.fillRoundRect((int) (x - handler.getGameCamera().getxOffset() - 6),
					(int) (y - handler.getGameCamera().getyOffset() - 8), 44, 6, 0, 4);
			g.setColor(HPOverlay.hpColorGreenOutline);
			g.drawRoundRect((int) (x - handler.getGameCamera().getxOffset() - 6),
					(int) (y - handler.getGameCamera().getyOffset() - 8), 44, 6, 0, 4);
		}else {
			g.fillRoundRect((int) (x - handler.getGameCamera().getxOffset() - 6),
					(int) (y - handler.getGameCamera().getyOffset() - 8), (int)(44 * (double)this.getHealth() /
					(double)this.getMaxHealth()), 6, 0, 4);
			g.setColor(HPOverlay.hpColorGreenOutline);
			g.drawRoundRect((int) (x - handler.getGameCamera().getxOffset() - 6),
					(int) (y - handler.getGameCamera().getyOffset() - 8), (int)(44 * (double)this.getHealth() /
					(double)this.getMaxHealth()), 6, 0, 4);
		}
	}
	
	/**
	 * Draws an Entity's information to an overlay at the top of the screen
	 * @param hoveringEntity
	 * @param g
	 */
	public void drawEntityOverlay(Entity hoveringEntity, Graphics g) {
		int yPos = 12;
		g.drawImage(Assets.chatwindow, 400, 1, 200, 50, null);
		for(int i = 0; i < getEntityInfo(hoveringEntity).length; i++) {
			Text.drawString(g, getEntityInfo(hoveringEntity)[i], 500, yPos, true, Color.YELLOW, Assets.font14);
			yPos += 14;
		}
	}
	
	
	/*
	 * Check the distance to another entity
	 */
	public double distanceToEntity(int x1, int y1, int x2, int y2){
		int dx = x2 - x1;
	    int dy = y2 - y1;
	    return Math.sqrt(dx * dx + dy * dy);
	}
	
	/*
	 * Returns the collision bounds of an Entity
	 */
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int)Math.round((x + bounds.x + xOffset)), (int)Math.round((y + bounds.y + yOffset)), bounds.width, bounds.height);
	}
	
	// Getters & Setters
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isAttackable(){
		return attackable;
	}

	public boolean isDamaged() {
		return damaged;
	}

	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}

	public boolean isNpc() {
		return isNpc;
	}

	public void setNpc(boolean isNpc) {
		this.isNpc = isNpc;
	}

	public boolean isShop() {
		return isShop;
	}

	public void setShop(boolean isShop) {
		this.isShop = isShop;
	}

	public boolean isDrawnOnMap() {
		return drawnOnMap;
	}

	public void setDrawnOnMap(boolean drawnOnMap) {
		this.drawnOnMap = drawnOnMap;
	}

	public boolean isStaticNpc() {
		return staticNpc;
	}

	public void setStaticNpc(boolean staticNpc) {
		this.staticNpc = staticNpc;
	}

	public Entity getDamageDealer() {
		return damageDealer;
	}

	public void setDamageDealer(Entity damageDealer) {
		this.damageDealer = damageDealer;
	}

	public Entity getDamageReceiver() {
		return damageReceiver;
	}

	public void setDamageReceiver(Entity damageReceiver) {
		this.damageReceiver = damageReceiver;
	}

	public ChatDialogue getChatDialogue() {
		return chatDialogue;
	}

	public void setChatDialogue(ChatDialogue chatDialogue) {
		this.chatDialogue = chatDialogue;
	}

	public int getSpeakingTurn() {
		return speakingTurn;
	}

	public void setSpeakingTurn(int speakingTurn) {
		this.speakingTurn = speakingTurn;
	}

	public boolean isOverlayDrawn() {
		return overlayDrawn;
	}

	public void setOverlayDrawn(boolean overlayDrawn) {
		this.overlayDrawn = overlayDrawn;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isInCombat() {
		return inCombat;
	}

	public void setInCombat(boolean inCombat) {
		this.inCombat = inCombat;
	}

	public int getSpeakingCheckpoint() {
		return speakingCheckpoint;
	}

	public void setSpeakingCheckpoint(int speakingCheckpoint) {
		this.speakingCheckpoint = speakingCheckpoint;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
}
