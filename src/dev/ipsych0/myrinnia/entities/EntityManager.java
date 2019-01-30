package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;

import java.awt.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4034952590793061132L;
    private Player player;
    private CopyOnWriteArrayList<Entity> entities;
    private Collection<Entity> deleted;
    private Entity selectedEntity;
    public static boolean isPressed = false;
    private CopyOnWriteArrayList<HitSplat> hitSplats;

    public EntityManager(Player player) {
        this.player = player;
        entities = new CopyOnWriteArrayList<Entity>();
        deleted = new CopyOnWriteArrayList<Entity>();
        hitSplats = new CopyOnWriteArrayList<>();
        addEntity(player);
    }

    public void tick() {
        // Iterate over all Entities and remove inactive ones
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            if (!e.isActive()) {
                deleted.add(e);
            }

            Collection<Condition> deleted = new CopyOnWriteArrayList<>();
            if(e instanceof Creature) {
                for (Condition c : ((Creature) e).getConditions()) {
                    if (c.isActive()) {
                        c.tick();
                    }else{
                        deleted.add(c);
                    }
                }
                ((Creature) e).getConditions().removeAll(deleted);
            }

            e.tick();
            // Update combat timers
            if (e.isDamaged() && e.getDamageDealer() != null) {
                e.updateCombatTimer();
            }
        }

        // If enemies are dead, update the respawn timers
        if (deleted.size() > 0) {
            entities.removeAll(deleted);
            for (Entity e : deleted) {
                e.startRespawnTimer();
                if (e.getRespawnTimer() == 0) {
                    e.respawn();
                    deleted.remove(e);
                }
            }
        }

        // Sort the list for rendering
        entities.sort((o1, o2) -> {
            Float a = o1.getY() + o1.getHeight();
            Float b = o2.getY() + o2.getHeight();
            return a.compareTo(b);
        });
    }

    public void render(Graphics g) {
        Rectangle mouse = Handler.get().getMouse();
        for (Entity e : entities) {

            if (e.getDamageReceiver() != null && Handler.get().getPlayer().isInCombat()) {
                if (e.isAttackable())
                    e.drawHP(g);
            }

            e.render(g);

            if (e instanceof Creature) {
                for (Projectile p : ((Creature) e).getProjectiles()) {
                    if (p.active) {
                        p.render(g);
                    }
                }
            }

            // If we rightclick an Entity, lock it to the top of the screen.
            if (e.getCollisionBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(mouse) && !e.equals(Handler.get().getPlayer()) && Handler.get().getMouseManager().isRightPressed() && !isPressed) {
                isPressed = true;
                if (e.isOverlayDrawn())
                    selectedEntity = e;
            }

            // Keep rendering the selected Entity
            if (selectedEntity != null) {
                if (selectedEntity.active)
                    selectedEntity.drawEntityOverlay(selectedEntity, g);
                else
                    selectedEntity = null;
            }

            // If we clicked away, remove the locked UI component
            if (selectedEntity != null && !e.getCollisionBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(mouse) &&
                    !Handler.get().getPlayer().hasRightClickedUI(mouse) &&
                    !e.equals(Handler.get().getPlayer()) && Handler.get().getMouseManager().isRightPressed() && !isPressed) {
                isPressed = true;
                selectedEntity = null;
            }

            // If the mouse is hovered over an Entity, draw the overlay
            if (e.getCollisionBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(mouse) && !e.equals(Handler.get().getPlayer())) {
                if (e.isOverlayDrawn())
                    e.drawEntityOverlay(e, g);
            }

            e.postRender(g);

        }

        Collection<HitSplat> deleted = new CopyOnWriteArrayList<HitSplat>();
        for (HitSplat hs : hitSplats) {
            if (hs.isActive()) {
                hs.render(g);
            } else {
                deleted.add(hs);
            }
        }
        hitSplats.removeAll(deleted);

    }

    public Entity getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(Entity selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public void postRender(Graphics g) {
        // Post renders for entities for additional
        player.postRender(g);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    // Getters & Setters

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public CopyOnWriteArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(CopyOnWriteArrayList<Entity> entities) {
        this.entities = entities;
    }

    public CopyOnWriteArrayList<HitSplat> getHitSplats() {
        return hitSplats;
    }
}
