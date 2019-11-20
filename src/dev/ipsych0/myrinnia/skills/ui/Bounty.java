package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class Bounty extends UIImageButton {

    private String task;
    private String shortDescription;
    private String fullDescription;
    private boolean accepted;
    private boolean completed;

    public Bounty(String task, String shortDescription, String fullDescription, int x, int y, int width, int height) {
        super(x, y, width, height, Assets.genericButton);
        this.task = task;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;

        if (shortDescription.length() > 100) {
            throw new IllegalArgumentException("Character limit for Bounty Panels is 100.");
        }
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);
        g.drawImage(Assets.bountyHunterIcon, x + 10, y + 16, 32, 32, null);
        Text.drawString(g, task, x + 48, y + 20, false, Color.YELLOW, Assets.font20);
        String[] text = Text.splitIntoLine(shortDescription, 50);
        for (int i = 0; i < text.length; i++) {
            Text.drawString(g, text[i], x + 48, y + 40 + (i * 16), false, Color.YELLOW, Assets.font14);
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }
}
