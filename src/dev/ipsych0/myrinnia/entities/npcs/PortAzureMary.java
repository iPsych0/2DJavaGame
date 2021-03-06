package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.*;

public class PortAzureMary extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.GatheringYourStuff);
    private boolean hasRemovedFish;

    public PortAzureMary(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;

        aDown = new Animation(250, Assets.genericFemale1Down);
        aLeft = new Animation(250, Assets.genericFemale1Left);
        aRight = new Animation(250, Assets.genericFemale1Right);
        aUp = new Animation(250, Assets.genericFemale1Up);
        aDefault = aDown;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureMary(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                if (Handler.get().questInProgress(QuestList.GatheringYourStuff) && quest.getQuestSteps().get(0).isFinished() && Handler.get().playerHasItem(Item.mackerelFish, 5)) {
                    speakingTurn = 5;
                    break;
                }
            case 6:
                if (quest.getQuestSteps().get(0).isFinished() && !hasRemovedFish && Handler.get().questInProgress(QuestList.GatheringYourStuff) && Handler.get().playerHasItem(Item.mackerelFish, 5)) {
                    Handler.get().removeItem(Item.mackerelFish, 5);
                    speakingCheckpoint = 6;
                    hasRemovedFish = true;
                }
                break;
            case 7:
                Handler.get().giveItem(Item.coins, 50);
                quest.nextStep();
                quest.setState(QuestState.COMPLETED);
                Handler.get().addTip(new TutorialTip("Press P to pause the game. Here, you can save your game or change settings."));
                speakingCheckpoint = 0;
                break;
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
