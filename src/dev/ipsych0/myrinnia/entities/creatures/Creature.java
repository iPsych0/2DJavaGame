package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Buff;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.pathfinding.CombatState;
import dev.ipsych0.myrinnia.pathfinding.Node;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Creature extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = -2545797921368819194L;

    /*
     * Default Creature variables
     */
    public static final float DEFAULT_SPEED = 1.0f, DEFAULT_ATTACKSPEED = 1.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 32,
            DEFAULT_CREATURE_HEIGHT = 32;

    public static final int DEFAULT_DAMAGE = 1,
            DEFAULT_STRENGTH = 0,
            DEFAULT_DEXTERITY = 0,
            DEFAULT_INTELLIGENCE = 0,
            DEFAULT_DEFENCE = 0,
            DEFAULT_VITALITY = 0;
    protected int baseDamage;
    protected int strength;
    protected int dexterity;
    protected int intelligence;
    protected int defence;
    protected int vitality;
    protected float attackSpeed;
    protected int combatLevel;
    protected static final double LEVEL_EXPONENT = 0.998;
    protected int attackRange = Tiles.TILEWIDTH * 2;
    protected ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    // Walking timer
    private int time = 0;

    // Radius variables:
    protected int xSpawn = (int) getX();
    protected int ySpawn = (int) getY();
    protected int xRadius = 192;
    protected int yRadius = 192;
    protected Rectangle radius;

    // A* stuff
    protected CombatState state;
    protected List<Node> nodes;
    protected int pathFindRadiusX = 1024, pathFindRadiusY = 1024;
    protected AStarMap map = new AStarMap(this, (int) xSpawn - pathFindRadiusX, (int) ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2, xSpawn, ySpawn);
    protected boolean aStarInitialized;
    protected Color pathColour = new Color(44, 255, 12, 127);
    private int stuckTimerX = 0, stuckTimerY = 0;
    private int lastX = (int) x, lastY = (int) y;
    private static final int TIMES_PER_SECOND = 4;
    private int timePerPathCheck = (60 / TIMES_PER_SECOND); // 4 times per second.
    private int pathTimer = 0;
    private List<Condition> conditions = new CopyOnWriteArrayList<>();
    private List<Buff> buffs = new CopyOnWriteArrayList<>();

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected Direction direction;
    // Last faced direction
    private Direction lastFaced;

    protected float speed;
    protected float xMove, yMove;

    public Creature(float x, float y, int width, int height) {
        super(x, y, width, height);
        state = CombatState.IDLE;
        baseDamage = (DEFAULT_DAMAGE);
        strength = (DEFAULT_STRENGTH);
        dexterity = (DEFAULT_DEXTERITY);
        intelligence = (DEFAULT_INTELLIGENCE);
        defence = (DEFAULT_DEFENCE);
        vitality = (DEFAULT_VITALITY);
        speed = (DEFAULT_SPEED);
        attackSpeed = (DEFAULT_ATTACKSPEED);
        maxHealth = (int) (DEFAULT_HEALTH + Math.round(vitality * 1.5));
        health = maxHealth;
        combatLevel = 1;
        xMove = 0;
        yMove = 0;
        drawnOnMap = true;
        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

    }

    /*
     * The default damage formula (see Entity::getDamage() function)
     * NOTE: USE THIS METHOD WITH @Override IN SPECIFIC ENTITIES TO CREATE PERSONAL DAMAGE FORMULA!
     */
    @Override
    public int getDamage(DamageType damageType, Entity dealer, Entity receiver) {
        return super.getDamage(damageType, dealer, receiver);
    }

    /*
     * Moves on the X or Y axis, keeping in mind the collision detection
     */
    public void move() {
        if (!checkEntityCollisions(xMove, 0f))
            moveX();
        if (!checkEntityCollisions(0f, yMove))
            moveY();
    }

    /*
     * Handles movement on the X-axis
     */
    public void moveX() {
        if (xMove > 0) { // Moving right
            direction = Direction.RIGHT;
            lastFaced = Direction.RIGHT;
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tiles.TILEWIDTH;

            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT)) {
                x += xMove;
            } else {
                x = tx * Tiles.TILEWIDTH - bounds.x - bounds.width - 1;
            }

        } else if (xMove < 0) { // Moving left
            direction = Direction.LEFT;
            lastFaced = Direction.LEFT;
            int tx = (int) (x + xMove + bounds.x) / Tiles.TILEWIDTH;

            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT)) {

                x += xMove;
            } else {
                x = tx * Tiles.TILEWIDTH + Tiles.TILEWIDTH - bounds.x;
            }
        } else if (xMove == 0) {
            direction = lastFaced;
        }
    }

    /*
     * Handles movement on the Y-axis
     */
    public void moveY() {
        if (yMove < 0) { // Up
            direction = Direction.UP;
            lastFaced = Direction.UP;
            int ty = (int) (y + yMove + bounds.y) / Tiles.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty)) {
                y += yMove;
            } else {
                y = ty * Tiles.TILEHEIGHT + Tiles.TILEHEIGHT - bounds.y;
            }

        } else if (yMove > 0) { // Down
            direction = Direction.DOWN;
            lastFaced = Direction.DOWN;
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tiles.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty)) {
                y += yMove;
            } else {
                y = ty * Tiles.TILEHEIGHT - bounds.y - bounds.height - 1;
            }
        } else if (yMove == 0) {
            direction = lastFaced;
        }
    }

    /*
     * Handles collision detection with Tiles
     */
    public boolean collisionWithTile(int x, int y) {
        // Debug
        if (Handler.noclipMode && this.equals(Handler.get().getPlayer()))
            return false;

        boolean walkableOnTop = false;
        for (int i = 0; i < Handler.get().getWorld().getLayers().length; i++) {
            Tiles t = Handler.get().getWorld().getTile(i, x, y);
            if (t != null && t.isSolid()) {
                walkableOnTop = t.getBounds() != null && t.getBounds().intersects(getCollisionBounds(0,0));
            } else {
                if (t != null && t != Tiles.tiles[0])
                    walkableOnTop = true;
            }
        }
        return !walkableOnTop;
    }

    /**
     * If the Entity is an NPC, this function returns only the name, else it returns name + combat info.
     */
    @Override
    public String[] getEntityInfo(Entity hoveringEntity) {
        if (this.isNpc) {
            String[] name = new String[1];
            name[0] = hoveringEntity.getName();
            return name;
        }
        String[] name = new String[2];
        name[0] = hoveringEntity.getName() + " (level-" + getCombatLevel() + ")";
        name[1] = "Health: " + String.valueOf(health) + "/" + String.valueOf(maxHealth);
        return name;
    }

    /**
     * Draws an Entity's information to an overlay at the top of the screen
     *
     * @param hoveringEntity
     * @param g
     */
    public void drawEntityOverlay(Entity hoveringEntity, Graphics g) {
        int yPos = 12;
        g.drawImage(Assets.chatwindow, Handler.get().getWidth() / 2 - 100, 1, 200, 50, null);
        for (int i = 0; i < getEntityInfo(hoveringEntity).length; i++) {
            Text.drawString(g, getEntityInfo(hoveringEntity)[i], Handler.get().getWidth() / 2, yPos + (14 * i), true, Color.YELLOW, Assets.font14);
        }
        for (int i = 0; i < conditions.size(); i++) {
            conditions.get(i).render(g, Handler.get().getAbilityManager().getAbilityHUD().getBounds().x + (i * ItemSlot.SLOTSIZE), 50);
        }

        int yOffset = 0;
        if (!conditions.isEmpty()) yOffset = 1;
        for (int i = 0; i < buffs.size(); i++) {
            buffs.get(i).render(g, Handler.get().getAbilityManager().getAbilityHUD().getBounds().x + (i * ItemSlot.SLOTSIZE), 50 + (ItemSlot.SLOTSIZE * yOffset));
        }
    }

    @Override
    public void postRender(Graphics g) {
        if (AStarMap.debugMode) {
            g.setColor(Color.BLACK);
            g.drawRect((int) (radius.x - Handler.get().getGameCamera().getxOffset()), (int) (radius.y - Handler.get().getGameCamera().getyOffset()), (int) (radius.width), (int) (radius.height));

            map.render(g);

            if (nodes != null) {
                for (Node n : nodes) {
                    g.setColor(pathColour);
                    g.fillRect((int) (n.getX() * 32 - Handler.get().getGameCamera().getxOffset()), (int) (n.getY() * 32 - Handler.get().getGameCamera().getyOffset()), 32, 32);
                }
            }
        }
    }

    protected void tickProjectiles() {
        Iterator<Projectile> it = projectiles.iterator();
        Collection<Projectile> deleted = new CopyOnWriteArrayList<Projectile>();
        while (it.hasNext()) {
            Projectile p = it.next();
            if (!p.active) {
                deleted.add(p);
            }
            for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
                if (p.getCollisionBounds(0, 0).intersects(e.getCollisionBounds(0, 0)) && p.active) {
                    if (e.equals(Handler.get().getPlayer())) {
                        e.damage(DamageType.DEX, this, e);

                        int dice = Handler.get().getRandomNumber(0, 5);
                        if (dice == 1) {
                            e.addCondition(this, e, new Condition(Condition.Type.POISON, e, 5, 3));
                        }

                        p.active = false;
                    }
                    if (!e.isAttackable()) {
                        p.active = false;
                    }
                }
            }
            for (int i = 0; i < Handler.get().getWorld().getLayers().length; i++) {
                if (collisionWithTile((int) ((p.getX() + 16) / 32), (int) ((p.getY() + 16) / 32)) && p.active) {
                    p.active = false;
                }
            }
            p.tick();
        }

        projectiles.removeAll(deleted);
    }

    public void tick() {
        if (!aStarInitialized) {
            map.init();
            aStarInitialized = true;
        }
        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);
        tickProjectiles();
        combatStateManager();
    }

    /*
     * Walks into random directions at given intervals
     */
    protected void randomWalk() {
        time++;
        int i = (Handler.get().getRandomNumber(60, 90));
        if (time % i == 0) {

            int direction = Handler.get().getRandomNumber(0, 4);

            if (direction == 0) {
                xMove = 0;
                yMove = 0;
            }

            if (direction == 1) {
                xMove = -speed;
            }
            if (direction == 2) {
                xMove = +speed;
            }
            if (direction == 3) {
                yMove = -speed;
            }
            if (direction == 4) {
                yMove = +speed;
            }
            time = 0;
        }

        if (getX() > (xSpawn + xRadius)) {
            xMove = -speed;
        } else if (getX() < (xSpawn - xRadius)) {
            xMove = +speed;
        } else if (getY() > (ySpawn + yRadius)) {
            yMove = -speed;
        } else if (getY() < (ySpawn - yRadius)) {
            yMove = +speed;
        }
        move();

    }

    protected void findPath() {
        if (state == CombatState.BACKTRACK) {
            nodes = map.findPath((int) ((x + 8) / 32) - (int) (xSpawn - pathFindRadiusX) / 32, (int) ((y + 8) / 32) - (int) (ySpawn - pathFindRadiusY) / 32,
                    (int) Math.round(((xSpawn + 8) / 32)) - (int) (xSpawn - pathFindRadiusX) / 32, (int) Math.round(((ySpawn + 8) / 32)) - (int) (ySpawn - pathFindRadiusY) / 32);
        } else {
            int playerX = (int) Math.round(((Handler.get().getPlayer().getX() + 0) / 32)) - (int) (xSpawn - pathFindRadiusX) / 32;
            int playerY = (int) Math.round(((Handler.get().getPlayer().getY() + 4) / 32)) - (int) (ySpawn - pathFindRadiusY) / 32;

            if (playerX == map.getNodes().length || playerY == map.getNodes().length) {
                nodes = map.findPath((int) ((x + 8) / 32) - (int) (xSpawn - pathFindRadiusX) / 32, (int) ((y + 8) / 32) - (int) (ySpawn - pathFindRadiusY) / 32,
                        (int) Math.round(((playerX + 8) / 32)) - (int) (xSpawn - pathFindRadiusX) / 32, (int) Math.round(((playerY + 8) / 32)) - (int) (ySpawn - pathFindRadiusY) / 32);
            } else {
                nodes = map.findPath((int) ((x + 8) / 32) - (int) (xSpawn - pathFindRadiusX) / 32, (int) ((y + 8) / 32) - (int) (ySpawn - pathFindRadiusY) / 32,
                        playerX, playerY);
            }
        }
    }

    /**
     * Manages the different combat states of a Creature (IDLE, PATHFINDING, ATTACKING, BACKTRACKING)
     */
    protected void combatStateManager() {

        if (damaged) {
            state = CombatState.PATHFINDING;
        }

        // If the player is within the A* map AND moves within the aggro range, state = pathfinding (walk towards goal)
        if (Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
            state = CombatState.PATHFINDING;
        }

        // If the Creature was not following or attacking the player, move around randomly.
        if (state == CombatState.IDLE) {
            randomWalk();
            return;
        }

        // If the player has moved out of the initial aggro box, but is still within the A* map, keep following
        else if (!Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING ||
                !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.ATTACK) {
            state = CombatState.PATHFINDING;
        }

        // If the player has moved out of the aggro box and out of the A* map,
        else if (!Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING ||
                !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.ATTACK) {
            state = CombatState.BACKTRACK;
        }

        // If the player is <= X * TileWidth away from the Creature, attack him.
        if (distanceToEntity(((int) this.getX() + this.getWidth() / 2), ((int) this.getY() + +this.getHeight() / 2),
                ((int) Handler.get().getPlayer().getX() + Handler.get().getPlayer().getWidth() / 2), ((int) Handler.get().getPlayer().getY() + Handler.get().getPlayer().getHeight() / 2)) <= attackRange &&
                Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) &&
                Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
            state = CombatState.ATTACK;
            checkAttacks();
        }

        // If the Creature was attacking, but the player moved out of aggro range or out of the A* map bounds, backtrack to spawn.
        if (state == CombatState.ATTACK && !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) || state == CombatState.ATTACK && !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
            state = CombatState.BACKTRACK;
        }

        // If the Creature was following the player but he moved out of the A* map, backtrack.
        if (!Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING || state == CombatState.BACKTRACK) {
            state = CombatState.BACKTRACK;
        }

        if (state == CombatState.PATHFINDING || state == CombatState.BACKTRACK) {
            // Control the number of times we check for new path
            pathTimer++;
            if (pathTimer >= timePerPathCheck) {
                findPath();
                pathTimer = 0;
            }
            followAStar();
        }
    }

    /**
     * Override this method in the creature's class
     */
    protected void checkAttacks() {

    }

    /**
     * Movement logic for following each node in the List nodes
     */
    protected void followAStar() {
        if (nodes == null) {
            return;
        }
        if (nodes.size() <= 0) {
            return;
        }

        Node next = nodes.get(0);

        // Store the current X & Y pause to check if the entity is stuck
        int currentX = (int) x;
        int currentY = (int) y;

        // If the X pause is the same as last tick, increment stuckTimer
        if (lastX == currentX) {
            stuckTimerX++;
        }
        lastX = (int) this.x;

        // If the Y pause is the same as last tick, increment stuckTimer
        if (lastY == currentY) {
            stuckTimerY++;
        }
        lastY = (int) this.y;

        // If the entity has been stuck for .75 seconds on X-axis, move it on the Y-axis to unstuck it.
        if (stuckTimerX >= 45) {
            yMove = +speed;
            move();
            stuckTimerX = 0;
            return;
        }

        // If the entity has been stuck for .75 seconds on Y-axis, move it on the X-axis to unstuck it.
        if (stuckTimerY >= 45) {
            xMove = +speed;
            move();
            stuckTimerY = 0;
            return;
        }

        if (next.getX() != (int) ((x + 8) / 32)) {
            xMove = (next.getX() < (int) ((x + 8) / 32) ? -speed : speed);
            if (x % 32 == 8) {
                //x -= x % 32;
                if (!nodes.isEmpty())
                    nodes.remove(0);

                stuckTimerX = 0;
                yMove = 0;
            }
        }

        if (next.getY() != (int) ((y + 8) / 32)) {
            yMove = (next.getY() < (int) ((y + 8) / 32) ? -speed : speed);
            if (y % 32 == 8) {
                //y -= y % 32;
                if (!nodes.isEmpty())
                    nodes.remove(0);

                stuckTimerY = 0;
                xMove = 0;
            }
        }

        if (xMove != 0 || yMove != 0) {
            move();
        }
    }

    // GETTERS + SETTERS

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public int getCombatLevel() {
        return combatLevel;
    }

    public void setCombatLevel(int combatLevel) {
        this.combatLevel = combatLevel;
    }

    public CombatState getState() {
        return state;
    }

    public void setState(CombatState state) {
        this.state = state;
    }

    public Rectangle getRadius() {
        return radius;
    }

    public void setRadius(Rectangle radius) {
        this.radius = radius;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public AStarMap getMap() {
        return map;
    }

    public void setMap(AStarMap map) {
        this.map = map;
    }

    public Direction getLastFaced() {
        return lastFaced;
    }

    public void setLastFaced(Direction lastFaced) {
        this.lastFaced = lastFaced;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<Buff> buffs) {
        this.buffs = buffs;
    }

    public int getPathFindRadiusX() {
        return pathFindRadiusX;
    }

    public void setPathFindRadiusX(int pathFindRadiusX) {
        this.pathFindRadiusX = pathFindRadiusX;
    }

    public int getPathFindRadiusY() {
        return pathFindRadiusY;
    }

    public void setPathFindRadiusY(int pathFindRadiusY) {
        this.pathFindRadiusY = pathFindRadiusY;
    }
}
