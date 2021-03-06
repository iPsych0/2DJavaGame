package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class QuestHelpUI implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7088128893254827094L;
    public static boolean isOpen = false;
    private int x, y, width, height;
    private Rectangle bounds;

    public QuestHelpUI() {
        this.x = 216;
        this.y = 150;
        this.width = 208;
        this.height = 400;
        bounds = new Rectangle(x, y, width, height);
    }

    public void tick() {
        if (isOpen) {

        }
    }

    public void render(Graphics2D g, Quest selectedQuest) {
        if (isOpen) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            if (selectedQuest != null) {
                Text.drawString(g, selectedQuest.getQuestName(), x + (width / 2) + 6, y + 19, true, Color.YELLOW, Assets.font14);

                if (selectedQuest.getState() == QuestState.COMPLETED) {
                    Text.drawString(g, "Quest completed!", x + (width / 2) + 6, y + 40, true, Color.GREEN, Assets.font14);
                } else if (selectedQuest.getState() == QuestState.IN_PROGRESS) {
                    Text.drawString(g, "Quest Log:", x + (width / 2) + 6, y + 40, true, Color.YELLOW, Assets.font14);
                } else {
                    renderRequirements(g, selectedQuest);
                }

                int offset = 0;
                if (selectedQuest.getState() != QuestState.NOT_STARTED)
                    for (int j = 0; j < selectedQuest.getStep() + 1; j++) {
                        try {
                            for (int i = 0; i < Text.splitIntoLine(selectedQuest.getQuestSteps().get(j).getObjective(), 26).length; i++) {
                                if (selectedQuest.getQuestSteps().get(j).isFinished()) {
                                    Text.drawStringStrikeThru(g, Text.splitIntoLine(selectedQuest.getQuestSteps().get(j).getObjective(), 26)[i], x + (width / 2) + 6, y + 60 + (offset * 16), true, Color.GREEN, Assets.font14);
                                } else {
                                    Text.drawString(g, Text.splitIntoLine(selectedQuest.getQuestSteps().get(j).getObjective(), 26)[i], x + (width / 2) + 6, y + 60 + (offset * 16), true, Color.YELLOW, Assets.font14);
                                }
                                offset++;
                            }
                        } catch (IndexOutOfBoundsException e) {

                        }
                    }
            }
        }
    }

    private void renderRequirements(Graphics2D g, Quest selectedQuest) {
        if (selectedQuest.getState() == QuestState.NOT_STARTED) {
            Text.drawString(g, "Requirements:", x + (width / 2) + 6, y + 40, true, Color.YELLOW, Assets.font14);

            String[] text = Text.splitIntoLine(selectedQuest.getQuestStart(), 26);
            int offset = 0;
            for (int i = 0; i < text.length; i++) {
                offset = i;
                Text.drawString(g, text[i], x + (width / 2) + 6, y + 60 + (i * 16), true, Color.YELLOW, Assets.font14);
            }

            if (selectedQuest.getRequirements() != null) {
                Color requirementColor = Color.YELLOW;

                // Check all requirements
                for (int i = 0; i < selectedQuest.getRequirements().size(); i++) {
                    // Check Skill requirements
                    if (selectedQuest.getRequirements().get(i).getSkill() != null) {
                        if (Handler.get().getSkill(selectedQuest.getRequirements().get(i).getSkill()).getLevel() >= selectedQuest.getRequirements().get(i).getLevel()) {
                            requirementColor = Color.GREEN;
                        } else {
                            requirementColor = Color.RED;
                        }
                    }
                    // Check Quest requirements
                    else if (selectedQuest.getRequirements().get(i).getQuest() != null) {
                        if (Handler.get().getQuest(selectedQuest.getRequirements().get(i).getQuest()).getState() == QuestState.COMPLETED) {
                            requirementColor = Color.GREEN;
                        } else {
                            requirementColor = Color.RED;
                        }
                    } else if (selectedQuest.getRequirements().get(i).getDescription() != null) {
                        if (selectedQuest.getRequirements().get(i).isTaskDone()) {
                            requirementColor = Color.GREEN;
                        } else {
                            if (selectedQuest.getRequirements().get(i).isMandatory()) {
                                requirementColor = Color.RED;
                            } else {
                                requirementColor = Color.YELLOW;
                            }
                        }
                    }

                    // Render the requirements
                    for (int j = 0; j < Text.splitIntoLine(selectedQuest.getRequirements().get(i).getRequirement(), 26).length; j++) {
                        if (requirementColor == Color.RED)
                            Text.drawString(g, Text.splitIntoLine(selectedQuest.getRequirements().get(i).getRequirement(), 26)[j], x + (width / 2) + 6, y + 80 + (offset * 16) + (i * 16 + j * 16), true, requirementColor, Assets.font14);
                        else if (requirementColor == Color.GREEN)
                            Text.drawStringStrikeThru(g, Text.splitIntoLine(selectedQuest.getRequirements().get(i).getRequirement(), 26)[j], x + (width / 2) + 6, y + 80 + (offset * 16) + (i * 16 + j * 16), true, requirementColor, Assets.font14);
                    }
                }
            }
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}
