package dev.ipsych0.myrinnia.abilityoverview;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class AbilityOverviewUIButton {

    private int x, y, width, height;
    private CharacterStats stat;
    private Rectangle bounds;

    public AbilityOverviewUIButton(int x, int y, CharacterStats characterStats) {
        this.x = x;
        this.y = y;
        this.width = ItemSlot.SLOTSIZE * 2;
        this.height = ItemSlot.SLOTSIZE;
        this.stat = characterStats;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(Assets.genericButton[0], x, y, width, height, null);
        Text.drawString(g, stat.toString(), x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font14);
    }

    public CharacterStats getStat() {
        return stat;
    }

    public void setStat(CharacterStats stat) {
        this.stat = stat;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
