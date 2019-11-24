package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.skills.SkillsList;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Tree extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -524381157898161854L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private boolean isWoodcutting = false;
    private int woodcuttingTimer = 0;
    private int minAttempts = 3, maxAttempts = 6;
    private int random = 0;
    private int attempts = 0;
    private Item logs;
    private int experience;

    public Tree(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        isNpc = true;
        attackable = false;

        if (name.equalsIgnoreCase("Weak Palm Tree")) {
            logs = Item.regularLogs;
            experience = 10;
        } else {
            throw new IllegalArgumentException("Tree name not found: " + name);
        }

    }

    @Override
    public void tick() {
        if (isWoodcutting) {
            if (Handler.get().invIsFull(logs)) {
                woodcuttingTimer = 0;
                speakingTurn = -1;
                interact();
                isWoodcutting = false;
            }
            if (Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
                    !Handler.get().getPlayer().hasLeftClickedUI(new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1))) {
                woodcuttingTimer = 0;
                speakingTurn = 0;
                isWoodcutting = false;
                return;
            }
            if (random != 0) {
                if (attempts == random) {
                    attempts = 0;
                    isWoodcutting = false;
                    this.active = false;
                    this.die();
                }
            }

            woodcuttingTimer++;

            if (woodcuttingTimer >= 180) {
                System.out.println(random + " and " + attempts);
                int roll = Handler.get().getRandomNumber(1, 100);
                if (roll < 70) {
                    Handler.get().giveItem(logs, Handler.get().getRandomNumber(1, 3));
                    Handler.get().sendMsg("You succesfully chopped some logs.");
                    Handler.get().getSkillsUI().getSkill(SkillsList.WOODCUTTING).addExperience(experience);
                    attempts++;

                } else {
                    Handler.get().sendMsg("Your hatchet bounced off the tree...");
                    attempts++;
                }
                speakingTurn = 0;
                woodcuttingTimer = 0;

                if (attempts == minAttempts - 1) {
                    random = Handler.get().getRandomNumber(minAttempts, maxAttempts);
                }
            }

        }
    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.tree, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        if (this.speakingTurn == -1) {
            speakingTurn++;
            return;
        }
        if (this.speakingTurn == 0) {
            if (Handler.get().playerHasSkillLevel(SkillsList.WOODCUTTING, logs)) {
                if (Handler.get().playerHasItemType(ItemType.AXE)) {
                    Handler.get().sendMsg("Chop chop...");
                    speakingTurn = 1;
                    isWoodcutting = true;
                } else {
                    Handler.get().sendMsg("You need an axe to chop this tree.");
                }
            } else {
                Handler.get().sendMsg("You need a woodcutting level of " + Handler.get().getSkillResource(SkillsList.WOODCUTTING, logs).getLevelRequirement() + " to chop this tree.");
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        if (isWoodcutting) {
            g.drawImage(Assets.woodcuttingIcon, (int) (Handler.get().getPlayer().getX() - Handler.get().getGameCamera().getxOffset()), (int) (Handler.get().getPlayer().getY() - Handler.get().getGameCamera().getyOffset() - 32), 32, 32, null);
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Tree(xSpawn, ySpawn, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {

    }

}
