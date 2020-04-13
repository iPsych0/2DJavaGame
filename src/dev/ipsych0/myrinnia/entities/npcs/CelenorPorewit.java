package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CelenorPorewit extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    private Rectangle triggerTile = new Rectangle(1184, 3680, 736, 32);
    private Player player;
    private static boolean hasSpawned;
    private static boolean hasWarned;
    private Zone zone;
    private Map<Integer, Boolean> questionsAskedMap = new HashMap<>();
    private static boolean questionsAsked;

    public CelenorPorewit(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;

        player = Handler.get().getPlayer();
    }

    @Override
    public void tick() {
        super.tick();
        zone = Handler.get().getWorld().getZone();
    }

    @Override
    public void render(Graphics2D g) {
        if (!hasSpawned && player.getCollisionBounds(0, 0).intersects(triggerTile)) {
            hasSpawned = true;
            this.x = player.getX();
            this.y = player.getY() - (Tile.TILEHEIGHT * 1.25d);
            player.setMovementAllowed(false);
            player.setClosestEntity(this);
            interact();
        }
        // Render only when
        if (hasSpawned && !hasWarned || zone == Zone.CelenorCaves) {
            g.drawImage(getAnimationByLastFaced(),
                    (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - Handler.get().getGameCamera().getyOffset()), null);
        }
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

//    @Override
//    protected boolean choiceConditionMet(String condition) {
//        if ("hasRake".equals(condition)) {
//            return !Handler.get().playerHasItem(Item.rake, 1);
//        }
//        return false;
//    }

    @Override
    protected void updateDialogue() {
        // The warning script logic
        if (script.getDialogues().get(0).getText().equalsIgnoreCase("Halt!")) {
            switch (speakingTurn) {
                case 6:
                    hasWarned = true;
                    player.setMovementAllowed(true);
                    player.setClosestEntity(null);
                    active = false;
                    break;
                case 7:
                    player.setMovementAllowed(true);
                    player.setClosestEntity(null);
                    speakingTurn = -1;
                    hasSpawned = false;
                    Handler.get().goToWorld(Zone.Celewynn, 50, 1);
                    break;
            }
            // The grotto cave logic
        } else {
            switch (speakingTurn) {
                case 0:
                    if (quest.getQuestSteps().get(1).isFinished()) {
                        speakingTurn = 7;
                    }
                    break;
                case 2:
                case 3:
                case 4:
                    if (!questionsAsked) {
                        checkAllQuestionsAsked();
                    }
                    break;
                case 7:
                    if (!quest.getQuestSteps().get(1).isFinished()) {
                        quest.nextStep();
                    }
                    break;
            }
        }
    }

    private void checkAllQuestionsAsked() {
        questionsAskedMap.put(speakingTurn, true);

        // Check if all questions are asked
        if (!questionsAsked && questionsAskedMap.size() == 3) {
            questionsAsked = true;
            // Add option to progress the quest
            script.getDialogues().get(1).getOptions().add(0, new Choice("Can you remove the fog?", 5, null));
        }
    }
}
