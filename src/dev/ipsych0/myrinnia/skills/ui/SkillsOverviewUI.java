package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.skills.Skill;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.ui.ScrollBar;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class SkillsOverviewUI implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5483332963034314338L;
    public int x = 280;
    public int y = 180;
    private int width = 384;
    private int height = 320;
    public static boolean isOpen = false;
    private Skill selectedSkill;
    private SkillCategory selectedCategory;
    private CategoryButton selectedButton;
    public static boolean hasBeenPressed = false;
    private ScrollBar scrollBar;
    private static final int maxPerScreen = 8;
    private ArrayList<CategoryButton> categories;
    private Rectangle bounds;
    private UIImageButton exit;
    private UIManager uiManager;

    public SkillsOverviewUI() {

        categories = new ArrayList<>();

        bounds = new Rectangle(x, y, width, height);
        scrollBar = new ScrollBar(x + width - 40, y + 40, 32, 256, 0, maxPerScreen, bounds);

        exit = new UIImageButton(x + width - 34, y + 10, 24, 24, Assets.genericButton);

        uiManager = new UIManager();
        uiManager.addObject(exit);
    }

    public void tick() {
        if (isOpen) {
            uiManager.tick();

            Rectangle mouse = Handler.get().getMouse();

            if (selectedSkill != null) {
                scrollBar.tick();
                int yPos = 0;

                for (CategoryButton cb : categories) {
                    if (selectedButton == null) {
                        selectedButton = cb;
                    }
                    if (cb.getBounds().contains(mouse)) {
                        cb.setHovering(true);
                        if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                            hasBeenPressed = false;
                            selectedCategory = cb.getCategory();
                            selectedButton = cb;
                            if (selectedSkill == Handler.get().getSkillsUI().getSkill(SkillsList.CRAFTING)) {
                                scrollBar.setIndex(0);
                                scrollBar.setListSize(Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).size());
                                scrollBar.setScrollMaximum(Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).size());
                            } else {
                                scrollBar.setIndex(0);
                                scrollBar.setListSize(selectedSkill.getListByCategory(selectedCategory).size());
                                scrollBar.setScrollMaximum(selectedSkill.getListByCategory(selectedCategory).size());
                            }
                        }
                    } else {
                        cb.setHovering(false);
                    }

                    cb.tick();
                }

                if (selectedSkill == Handler.get().getSkillsUI().getSkill(SkillsList.CRAFTING)) {
                    for (int i = scrollBar.getIndex(); i < scrollBar.getScrollMaximum(); i++) {

                        Rectangle slot = new Rectangle(categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32);

                        if (slot.contains(mouse) && Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).isDiscovered() && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                            Handler.get().sendMsg(Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).toString());
                            hasBeenPressed = false;
                        } else if (slot.contains(mouse) && !Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).isDiscovered() && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                            Handler.get().sendMsg("Explore the world or do quests to unlock recipes!");
                            hasBeenPressed = false;
                        }
                        yPos++;
                    }
                } else {
                    for (int i = scrollBar.getIndex(); i < scrollBar.getScrollMaximum(); i++) {

                        Rectangle slot = new Rectangle(categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32);

                        if (slot.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                            Handler.get().sendMsg("You can find this resource in: [Zones]");
                            hasBeenPressed = false;
                        }

                        yPos++;
                    }
                }
            }
        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            Rectangle mouse = Handler.get().getMouse();

            uiManager.render(g);

            if (selectedSkill != null) {
                Text.drawString(g, selectedSkill.toString(), x + width / 2, y + 20, true, Color.YELLOW, Assets.font20);
                int yPos = 0;

                if (selectedButton != null) {
                    g.setColor(Colors.selectedColor);
                    g.fillRoundRect(selectedButton.x, selectedButton.y, selectedButton.width, selectedButton.height, 4, 4);
                }

                for (CategoryButton cb : categories) {
                    cb.render(g);
                }


                if (exit.contains(mouse)) {
                    if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                        MouseManager.justClosedUI = true;
                        isOpen = false;
                        hasBeenPressed = false;
                        return;
                    }
                }
                Text.drawString(g, "X", exit.x + 11, exit.y + 11, true, Color.YELLOW, Assets.font20);

                if (selectedSkill == Handler.get().getSkillsUI().getSkill(SkillsList.CRAFTING)) {
                    if (Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).size() > maxPerScreen) {
                        for (int i = scrollBar.getIndex(); i < scrollBar.getIndex() + maxPerScreen; i++) {

                            Rectangle slot = new Rectangle(categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32);

                            g.drawImage(Assets.genericButton[1], categories.get(0).x + categories.get(0).width + 8, (y + 40) + (yPos * 32), 32, 32, null);
                            if (slot.contains(mouse)) {
                                g.drawImage(Assets.genericButton[0], categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32, null);
                            } else {
                                g.drawImage(Assets.genericButton[1], categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32, null);
                            }

                            if (Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).isDiscovered()) {
                                g.drawImage(Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).getResult().getItem().getTexture(), categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32, null);
                                Text.drawString(g, Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).getResult().getItem().getName(), (slot.x + slot.width + 8), (y + 40) + (yPos * 32) + 20, false, Color.YELLOW, Assets.font14);
                            } else {
                                g.drawImage(Assets.undiscovered, categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32, null);
                                Text.drawString(g, "Unknown", (slot.x + slot.width + 8), (y + 40) + (yPos * 32) + 20, false, Color.YELLOW, Assets.font14);
                            }
                            Text.drawString(g, String.valueOf(Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).getRequiredLevel()), categories.get(0).x + categories.get(0).width + 24, (y + 40) + (yPos * 32) + 16, true, Color.YELLOW, Assets.font20);

                            scrollBar.render(g);

                            yPos++;
                        }
                    } else {
                        for (int i = 0; i < Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).size(); i++) {

                            Rectangle slot = new Rectangle(categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (i * 32), 32, 32);

                            g.drawImage(Assets.genericButton[1], categories.get(0).x + categories.get(0).width + 8, (y + 40) + (yPos * 32), 32, 32, null);
                            if (slot.contains(mouse)) {
                                g.drawImage(Assets.genericButton[0], categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (i * 32), 32, 32, null);
                            } else {
                                g.drawImage(Assets.genericButton[1], categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (i * 32), 32, 32, null);
                            }

                            if (Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).isDiscovered()) {
                                g.drawImage(Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).getResult().getItem().getTexture(), categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (i * 32), 32, 32, null);
                                Text.drawString(g, Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).getResult().getItem().getName(), (slot.x + slot.width + 8), (y + 40) + (yPos * 32) + 20, false, Color.YELLOW, Assets.font14);
                            } else {
                                g.drawImage(Assets.undiscovered, categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32, null);
                                Text.drawString(g, "Unknown", (slot.x + slot.width + 8), (y + 40) + (yPos * 32) + 20, false, Color.YELLOW, Assets.font14);
                            }
                            Text.drawString(g, String.valueOf(Handler.get().getCraftingUI().getCraftingManager().getListByCategory(selectedCategory).get(i).getRequiredLevel()), categories.get(0).x + categories.get(0).width + 24, (y + 40) + (i * 32) + 16, true, Color.YELLOW, Assets.font20);

                            scrollBar.render(g);

                            yPos++;
                        }
                    }
                } else {
                    if (selectedSkill.getListByCategory(selectedCategory).size() > maxPerScreen) {
                        for (int i = scrollBar.getIndex(); i < scrollBar.getIndex() + maxPerScreen; i++) {
                            Rectangle slot = new Rectangle(x + categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32);

                            g.drawImage(Assets.genericButton[1], categories.get(0).x + categories.get(0).width + 8, (y + 40) + (yPos * 32), 32, 32, null);
                            if (slot.contains(mouse)) {
                                g.drawImage(Assets.genericButton[0], categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32, null);
                            } else {
                                g.drawImage(Assets.genericButton[1], categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32, null);
                            }

                            g.drawImage(selectedSkill.getListByCategory(selectedCategory).get(i).getItem().getTexture(), categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (yPos * 32), 32, 32, null);
                            Text.drawString(g, String.valueOf(selectedSkill.getListByCategory(selectedCategory).get(i).getLevelRequirement()), categories.get(0).x + categories.get(0).width + 24, (y + 40) + (yPos * 32) + 16, true, Color.YELLOW, Assets.font20);
                            Text.drawString(g, selectedSkill.getListByCategory(selectedCategory).get(i).getItem().getName(), (slot.x + slot.width + 8), (y + 40) + (yPos * 32) + 20, false, Color.YELLOW, Assets.font14);
                            scrollBar.render(g);

                            yPos++;
                        }
                    } else {
                        for (int i = 0; i < selectedSkill.getListByCategory(selectedCategory).size(); i++) {
                            Rectangle slot = new Rectangle(categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (i * 32), 32, 32);

                            g.drawImage(Assets.genericButton[1], categories.get(0).x + categories.get(0).width + 8, (y + 40) + (yPos * 32), 32, 32, null);
                            if (slot.contains(mouse)) {
                                g.drawImage(Assets.genericButton[0], categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (i * 32), 32, 32, null);
                            } else {
                                g.drawImage(Assets.genericButton[1], categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (i * 32), 32, 32, null);
                            }

                            g.drawImage(selectedSkill.getListByCategory(selectedCategory).get(i).getItem().getTexture(), categories.get(0).x + categories.get(0).width + 32 + 8, (y + 40) + (i * 32), 32, 32, null);
                            Text.drawString(g, String.valueOf(selectedSkill.getListByCategory(selectedCategory).get(i).getLevelRequirement()), categories.get(0).x + categories.get(0).width + 24, (y + 40) + (i * 32) + 16, true, Color.YELLOW, Assets.font20);
                            Text.drawString(g, selectedSkill.getListByCategory(selectedCategory).get(i).getItem().getName(), (slot.x + slot.width + 8), (y + 40) + (yPos * 32) + 20, false, Color.YELLOW, Assets.font14);

                            scrollBar.render(g);
                            yPos++;
                        }
                    }
                }
            }
        }
    }

    public Skill getSelectedSkill() {
        return selectedSkill;
    }

    public void setSelectedSkill(Skill selectedSkill) {
        this.selectedSkill = selectedSkill;
    }

    public ScrollBar getScrollBar() {
        return scrollBar;
    }

    public void setScrollBar(ScrollBar scrollBar) {
        this.scrollBar = scrollBar;
    }

    public SkillCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(SkillCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public ArrayList<CategoryButton> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryButton> categories) {
        this.categories = categories;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public CategoryButton getSelectedButton() {
        return selectedButton;
    }

    public void setSelectedButton(CategoryButton selectedButton) {
        this.selectedButton = selectedButton;
    }
}
