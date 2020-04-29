package dev.ipsych0.myrinnia.ui.custom;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BookUI {

    private boolean open;
    public static boolean anyInterfaceOpen;
    public static boolean hasPressed;
    private int x, y, width, height;
    private PageType pageType;
    private String title;
    private String[] content;
    private UIImageButton exitButton;
    private UIImageButton forwardButton;
    private UIImageButton backButton;
    private UIImageButton header;
    private UIManager uiManager;
    private int pageNumbers;
    private int currentPage = 0;

    public BookUI(PageType pageType, String title, String... content) {
        this.pageType = pageType;
        this.title = title;
        this.content = content;

        // Center the UI
        this.width = pageType.width;
        this.height = pageType.height;
        this.x = Handler.get().getWidth() / 2 - (this.width / 2);
        this.y = Handler.get().getHeight() / 2 - (this.height / 2);

        pageNumbers = content.length;

        // Init buttons
        uiManager = new UIManager();
        exitButton = new UIImageButton(x + width - 44, y + 28, 16, 16, Assets.genericButton);
        forwardButton = new UIImageButton(x + width - 68, y + height - 64, 48, 32, Assets.genericButton);
        backButton = new UIImageButton(x + 16, y + height - 64, 48, 32, Assets.genericButton);
        header = new UIImageButton(x, y - 32, width, 32, Assets.genericButton);

        uiManager.addObject(exitButton);
        if (pageNumbers > 2) {
            uiManager.addObject(forwardButton);
        }
    }

    public void tick() {
        uiManager.tick();

        // Close when moving / escape pressed
        if (Player.isMoving || Handler.get().getKeyManager().escape) {
            close();
        }

        // Close when exit clicked
        Rectangle mouse = Handler.get().getMouse();
        if (exitButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasPressed) {
            close();
            MouseManager.justClosedUI = true;
        }

        if (pageNumbers > 2) {
            // If we press next
            if (uiManager.getObjects().contains(forwardButton) && forwardButton.contains(mouse) &&
                    Handler.get().getMouseManager().isLeftPressed() && hasPressed) {
                hasPressed = false;
                goToNextPage();
            }

            // If the back-button is there and we've pressed it, return
            if (uiManager.getObjects().contains(backButton) && backButton.contains(mouse) &&
                    Handler.get().getMouseManager().isLeftPressed() && hasPressed) {
                hasPressed = false;
                returnLastPage();
            }
        }
    }

    private void close() {
        open = false;
        hasPressed = false;
        currentPage = 0;
        // If the back button was pressed, add the forward button again
        if (pageNumbers > 2 && !uiManager.getObjects().contains(forwardButton)) {
            uiManager.addObject(forwardButton);
        }
        if (pageNumbers > 2 && uiManager.getObjects().contains(backButton)) {
            uiManager.removeObject(backButton);
        }

        anyInterfaceOpen = false;
    }

    private void returnLastPage() {
        currentPage -= 2;

        // If we're on the first page again, remove the back button
        if (currentPage == 0) {
            uiManager.removeObject(backButton);
        }

        // If the back button was pressed, add the forward button again
        if (!uiManager.getObjects().contains(forwardButton)) {
            uiManager.addObject(forwardButton);
        }

        Handler.get().playEffect("ui/turn_page.ogg", 0.65f);
    }

    private void goToNextPage() {
        currentPage += 2;

        // Add the back-button on first-flip
        if (!uiManager.getObjects().contains(backButton)) {
            uiManager.addObject(backButton);
        }

        // If we've reached the last page, remove the forward button
        if (currentPage == pageNumbers - 1 || currentPage == pageNumbers - 2) {
            uiManager.removeObject(forwardButton);
        }

        Handler.get().playEffect("ui/turn_page.ogg", 0.65f);
    }

    public void render(Graphics2D g) {
        g.drawImage(pageType.img, x, y, width, height, null);
        uiManager.render(g);

        // Draw header
        g.drawImage(Assets.genericButton[0], header.x, header.y, header.width, header.height, null);
        g.drawImage(Assets.genericButton[0], header.x, header.y, header.width, header.height, null);
        Text.drawStringInBox(g, title, header, Color.YELLOW, Assets.font24);

        // Draw text in buttons
        Text.drawStringInBox(g, "X", exitButton, Color.YELLOW, Assets.font20);
        if (uiManager.getObjects().contains(backButton)) {
            Text.drawStringInBox(g, "Back", backButton, Color.YELLOW, Assets.font20);
        }
        if (uiManager.getObjects().contains(forwardButton)) {
            Text.drawStringInBox(g, "Next", forwardButton, Color.YELLOW, Assets.font20);
        }

        // Arrange the lines for page 1 and/or 2
        String[] page1 = null, page2 = null;
        try {
            page1 = Text.splitIntoLine(content[currentPage], 20);
            page2 = Text.splitIntoLine(content[currentPage + 1], 20);
        } catch (IndexOutOfBoundsException e) {
            page1 = Text.splitIntoLine(content[currentPage], 20);
        }

        for (int i = 0; i < page1.length; i++) {
            Text.drawString(g, page1[i], x + 32, y + 64 + (i * 16), false, Color.YELLOW, Assets.font14);
        }
        if (page2 != null) {
            for (int i = 0; i < page2.length; i++) {
                Text.drawString(g, page2[i], x + width / 2 + 32, y + 64 + (i * 16), false, Color.YELLOW, Assets.font14);
            }
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        if (open) {
            BookUI.anyInterfaceOpen = true;
        }
        this.open = open;
    }

    public enum PageType {
        FULL_BOOK(Assets.fullBookUI, Tile.TILEWIDTH * 12, Tile.TILEHEIGHT * 9),
        SINGLE_PAGE(Assets.singlePageBookUI, Tile.TILEWIDTH * 5, Tile.TILEHEIGHT * 7);
        int width, height;

        BufferedImage img;

        PageType(BufferedImage img, int width, int height) {
            this.img = img;
            this.width = width;
            this.height = height;
        }

    }
}
