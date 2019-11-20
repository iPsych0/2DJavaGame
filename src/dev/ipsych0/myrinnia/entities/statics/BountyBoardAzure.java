package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.skills.ui.BountyBoardUI;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class BountyBoardAzure extends BountyBoard {

    private static final long serialVersionUID = 4925882540927003315L;

    private BountyBoardUI bountyBoardUI;

    public BountyBoardAzure(float x, float y, int width, int height) {
        super(x, y, width, height);

        bounds.x = 0;
        bounds.y = height / 2;
        bounds.height = height / 2;
        bounds.width = width;

        attackable = false;
        isNpc = true;

        bountyBoardUI = new BountyBoardUI(Zone.PortAzure);
        bountyBoardUI.addPanel("Cut the Crab", "A giant crab attacked me in Sunset Cove! I lost my axe. Someone please help!", "I was attacked by a giant crab in Sunset Cove! When I fled, I left behind my axe. Someone please retrieve my axe, you will be rewarded.\n\n- {Insert Owner's Name}");
    }

    @Override
    public BountyBoardUI getBountyBoardUI() {
        return bountyBoardUI;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.bountyBoard2, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }
}
