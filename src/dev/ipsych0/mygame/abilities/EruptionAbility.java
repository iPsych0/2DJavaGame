package dev.ipsych0.mygame.abilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;

public class EruptionAbility extends Ability {
	
	private Color ability = new Color(89, 58, 2, 224);
	private Rectangle hitBox;
	private boolean initDone;
	private int renderTimer = 0;
	private int displayTime = 1 * 60;
	
	public EruptionAbility(Handler handler, CharacterStats element, String name, AbilityType abilityType, boolean selectable,
			int cooldownTime, int castingTime, int overcastTime, int baseDamage, String description) {
		super(handler, element, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(Assets.dirtHole, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
		if(hitBox != null) {
			g.setColor(ability);
			g.fillRect(hitBox.x - (int)handler.getGameCamera().getxOffset(), hitBox.y - (int)handler.getGameCamera().getyOffset(), hitBox.width, hitBox.height);
		}
	}

	@Override
	public void cast() {
		if(!initDone) {
			hitBox = new Rectangle((int)caster.getX() - ItemSlot.SLOTSIZE, (int)caster.getY() - ItemSlot.SLOTSIZE,
					caster.getWidth() + ItemSlot.SLOTSIZE * 2, caster.getHeight() + ItemSlot.SLOTSIZE * 2);
			initDone = true;
		
			for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
				if(hitBox.contains(e.getCollisionBounds(0, 0))) {
					if(!e.equals(caster)) {
						e.damage(caster, e);
					}
				}
			}
		}
		renderTimer++;
		if(renderTimer == displayTime) {
			hitBox = null;
			this.setCasting(false);
		}
	}
	
	@Override
	protected void countDown() {
		cooldownTimer++;
		if(cooldownTimer / 60 == cooldownTime) {
			this.setOnCooldown(false);
			this.setActivated(false);
			this.setCasting(false);
			castingTimeTimer = 0;
			initDone = false;
			cooldownTimer = 0;
			renderTimer = 0;
			hitBox = null;
		}
	}

}