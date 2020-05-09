package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Condition implements Serializable {

    private static final long serialVersionUID = -6491027693312163146L;
    private Entity receiver;
    private double duration;
    private int tickTimer;
    private boolean active;
    private int conditionDamage;
    private transient BufferedImage img;
    private double initialSpeedDecrease;
    private static final double CHILL_MOVSPD = 0.5;
    private static final double CRIPPLE_MOVSPD = 0.66;
    private Type type;

    public Condition(Type type, double durationSeconds) {
        this.type = type;
        this.img = type.getImg();
        this.duration = durationSeconds * 60d;
        this.active = true;
    }

    public Condition(Type type, double durationSeconds, int conditionDamage) {
        this.type = type;
        this.img = type.getImg();
        this.duration = durationSeconds * 60d;
        this.conditionDamage = conditionDamage;
        this.active = true;
    }

    public void tick() {
        if (this.isActive()) {
            // If the enemy died, stop ticking, but finish the render of the last condition
            if (!receiver.isActive()) {
                if (tickTimer % 60 == 0) {
                    clear();
                }
                tickTimer++;
                return;
            }

            // If the timeLeft is greater than 0 at any given time
            if (tickTimer <= duration) {
                // Tick the condition effect
                if (tickTimer == 0) {
                    apply();
                } else if (tickTimer % 60 == 0) {
                    update();
                }
                // If the condition timeLeft is 0, don't tick anymore, but let the last hitsplat disappear
            } else {
                if (tickTimer % 60 == 0) {
                    clear();
                }
            }
            tickTimer++;
        }
    }

    public void render(Graphics2D g, int x, int y) {
        if (active) {
            g.drawImage(img, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
            Text.drawString(g, String.valueOf(Handler.get().roundOff(((duration - tickTimer) / 60d) + 1.0)), x + 18, y + 26, false, Color.YELLOW, Assets.font14);
        }
    }

    private void apply() {
        receiver.tickCondition(receiver, this);
        Creature r = ((Creature) receiver);
        switch (type) {
            case CHILL:
                double currMovSpd = r.getSpeed();
                double newMovSpd = (r.getSpeed() * CHILL_MOVSPD);
                initialSpeedDecrease = currMovSpd - newMovSpd;
                r.setSpeed(newMovSpd);
                break;
            case CRIPPLED:
                double currMovSpd2 = r.getSpeed();
                double newMovSpd2 = (r.getSpeed() * CRIPPLE_MOVSPD);
                initialSpeedDecrease = currMovSpd2 - newMovSpd2;
                r.setSpeed(newMovSpd2);
                break;
        }
    }

    private void update() {
        // After 1 second, recreate the damage splat
        receiver.tickCondition(receiver, this);
    }

    public void clear() {
        tickTimer = 0;
        this.setActive(false);

        if (type == Type.CHILL || type == Type.CRIPPLED) {
            Creature r = ((Creature) receiver);
            r.setSpeed(r.getSpeed() + initialSpeedDecrease);
        }
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getConditionDamage() {
        return conditionDamage;
    }

    public void setConditionDamage(int conditionDamage) {
        this.conditionDamage = conditionDamage;
    }

    public Type getType() {
        return type;
    }

    public Entity getReceiver() {
        return receiver;
    }

    public void setReceiver(Entity receiver) {
        this.receiver = receiver;
    }

    public enum Type {
        BURNING(Assets.burnIcon, "'Burning' inflicts damage over time."),
        CHILL(Assets.chillIcon, "'Chill' decreases the receiver's movement speed by 50%."),
        CRIPPLED(Assets.crippledIcon, "'Crippled' decreases the receiver's movement speed by 33%."),
        BLEEDING(Assets.bleedIcon, "'Bleeding' inflicts damage over time."),
        POISON(Assets.poisonIcon, "'Poison' inflicts damage over time."),
        ROOTED(Assets.rootedIcon, "'Rooted' renders the receiver immobilized."),
        BLINDED(Assets.blindedIcon, "'Blinded' makes the next attack miss."),
        STUN(Assets.stunIcon, "'Stun' immobilizes and stops the receiver from attacking.");

        Type(BufferedImage img, String description) {
            this.img = img;
            this.description = description;
        }

        private BufferedImage img;
        private String description;

        public BufferedImage getImg() {
            return img;
        }

        public String getDescription() {
            return description;
        }
    }

    public int getTickTimer() {
        return tickTimer;
    }

    public void setTickTimer(int tickTimer) {
        this.tickTimer = tickTimer;
    }
}
