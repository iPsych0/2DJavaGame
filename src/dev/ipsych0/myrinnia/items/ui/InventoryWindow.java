package dev.ipsych0.myrinnia.items.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InventoryWindow implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2445807581436976803L;
    public static boolean isOpen = true;
    public static boolean equipPressed = false;
    public static boolean hasBeenPressed = false;

    private int x, y;
    private int width, height;

    private int numCols = 3;
    private int numRows = 10;

    private CopyOnWriteArrayList<ItemSlot> itemSlots;
    private ItemStack currentSelectedSlot;
    private ItemStack itemSwap;
    private ItemStack equipSwap;
    public static boolean itemSelected;
    private Rectangle windowBounds;
    private ItemTooltip itemTooltip;

    public InventoryWindow() {
        width = numCols * (ItemSlot.SLOTSIZE + 11) + 3;
        height = numRows * (ItemSlot.SLOTSIZE + 11) - 58;
        this.x = Handler.get().getWidth() - width - 8;
        this.y = 8;
        windowBounds = new Rectangle(x, y, width, height);
        itemSlots = new CopyOnWriteArrayList<ItemSlot>();

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                if (j == (numRows)) {
                    x += 8;
                }

                itemSlots.add(new ItemSlot(x + 17 + (i * (ItemSlot.SLOTSIZE)), y + 32 + (j * ItemSlot.SLOTSIZE), null));

                if (j == (numRows)) {
                    x -= 8;
                }
            }
        }

        itemTooltip = new ItemTooltip(x - 160, y);

        itemSlots.get(findFreeSlot(Item.regularLogs)).addItem(Item.coins, 1000);
        itemSlots.get(findFreeSlot(Item.regularLogs)).addItem(Item.regularLogs, 100);
        itemSlots.get(findFreeSlot(Item.regularOre)).addItem(Item.regularOre, 100);
        itemSlots.get(findFreeSlot(Item.testSword)).addItem(Item.testSword, 1);
        itemSlots.get(findFreeSlot(Item.purpleSword)).addItem(Item.purpleSword, 1);


    }


    public void tick() {
        if (isOpen) {
            Rectangle mouse = Handler.get().getMouse();

            for (ItemSlot is : itemSlots) {

                is.tick();

                Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);

                // If an item is dragged
                if (Handler.get().getMouseManager().isDragged()) {
                    if (slot.contains(mouse) && !hasBeenPressed && !itemSelected) {
                        hasBeenPressed = true;

                        // Stick the item to the mouse
                        if (currentSelectedSlot == null) {
                            if (is.getItemStack() != null) {
                                currentSelectedSlot = is.getItemStack();
                                is.setItemStack(null);
                                itemSelected = true;
                                BankUI.inventoryLoaded = false;
                            } else {
                                hasBeenPressed = false;
                                return;
                            }
                        }
                    }
                }

                // If the item is released
                if (itemSelected && !Handler.get().getMouseManager().isDragged()) {
                    if (slot.contains(mouse)) {
                        // If the itemstack already holds an item
                        if (is.getItemStack() != null) {
                            if (currentSelectedSlot.getItem().isStackable()) {
                                // And if the item in the slot is stackable
                                if (is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
                                    // Add the item back to the inventory
                                    currentSelectedSlot = null;
                                    itemSelected = false;
                                    hasBeenPressed = false;
                                    BankUI.inventoryLoaded = false;

                                } else {
                                    // If we cannot add the item to an existing stack
                                    hasBeenPressed = false;
                                    return;
                                }
                            } else {
                                // If the item is not stackable / we cannot add the item
                                hasBeenPressed = false;
                            }
                        } else {
                            // If the item stack == null, we can safely add it.
                            is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
                            currentSelectedSlot = null;
                            itemSelected = false;
                            hasBeenPressed = false;
                            BankUI.inventoryLoaded = false;
                        }
                    }
                }

                // If the item is dragged outside the inventory
                if (itemSelected && !Handler.get().getMouseManager().isDragged()) {
                    if (Handler.get().getMouseManager().getMouseX() <= this.x) {
                        // Drop the item
                        if (!BankUI.isOpen && !CraftingUI.isOpen) {
                            Handler.get().dropItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount(), (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY());
                            currentSelectedSlot = null;
                            itemSelected = false;
                            BankUI.inventoryLoaded = false;
                        }
                        hasBeenPressed = false;
                    }
                }

                // If item is right-clicked
                if (slot.contains(mouse) && Handler.get().getMouseManager().isRightPressed() && equipPressed && !hasBeenPressed && !Handler.get().getMouseManager().isDragged() && !CraftingUI.isOpen && !ShopWindow.isOpen) {
                    if (is.getItemStack() != null) {
                        if (Handler.get().getPlayer().isInCombat()) {
                            Handler.get().sendMsg("You cannot equip items while in combat.");
                            hasBeenPressed = false;
                            equipPressed = false;
                            return;
                        }
                        if (is.getItemStack().getItem().getEquipSlot() == EquipSlot.None.getSlotId()) {
                            // If the item's equipmentslot = 12, that means it's unequippable, so return
                            Handler.get().sendMsg("You cannot equip " + is.getItemStack().getItem().getName() + ".");
                            equipPressed = false;
                            hasBeenPressed = false;
                            return;
                        }

                        // If the item's equipmentslot is a valid slot
                        if (is.getItemStack().getItem().getEquipSlot() != EquipSlot.None.getSlotId()) {
                            if (Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack() != null &&
                                    is.getItemStack().getItem().getId() ==
                                            Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack().getItem().getId()) {
                                // If trying to equip the exact same item, return message
                                Handler.get().sendMsg("You've already equipped this item!");
                                equipPressed = false;
                                hasBeenPressed = false;
                                return;
                            }

                            // If we don't have the required level to equip that item, return
                            if (is.getItemStack().getItem().getRequirements() != null && is.getItemStack().getItem().getRequirements().length > 0) {
                                String missingReqs = "";
                                boolean missing = false;
                                for (int i = 0; i < is.getItemStack().getItem().getRequirements().length; i++) {
                                    if (is.getItemStack().getItem().getRequirements()[i].getStat().getLevel() < is.getItemStack().getItem().getRequirements()[i].getLevel()) {
                                        missing = true;
                                        if (i < is.getItemStack().getItem().getRequirements().length - 1)
                                            missingReqs += is.getItemStack().getItem().getRequirements()[i].getLevel() + " " +
                                                    is.getItemStack().getItem().getRequirements()[i].getStat().toString().toLowerCase() + " and ";
                                        else
                                            missingReqs += is.getItemStack().getItem().getRequirements()[i].getLevel() + " " +
                                                    is.getItemStack().getItem().getRequirements()[i].getStat().toString().toLowerCase() + " points";
                                    }
                                }
                                if (missing) {
                                    Handler.get().sendMsg("You need " + missingReqs + " to equip this item.");
                                    equipPressed = false;
                                    hasBeenPressed = false;
                                    return;
                                }
                            }

                            // Play the equip sound
                            Handler.get().playEffect("ui/equip.wav");

                            // If we have no item equipped in that slot
                            if (Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).equipItem(is.getItemStack().getItem())) {
                                Handler.get().getPlayer().addEquipmentStats(is.getItemStack().getItem().getEquipSlot());
                                // Add equipment stats and subtract 1 from the item in our inventory
                                if (is.getItemStack().getAmount() >= 2) {
                                    is.getItemStack().setAmount(is.getItemStack().getAmount() - 1);
                                } else {
                                    // Or if only 1 item left, set the stack to null
                                    is.setItemStack(null);
                                }
                                equipPressed = false;
                                hasBeenPressed = false;
                                BankUI.inventoryLoaded = false;
                                return;
                            } else {

                                // Set the swaps
                                itemSwap = is.getItemStack();
                                equipSwap = Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack();

                                // Remove the equipment stats
                                Handler.get().getPlayer().removeEquipmentStats(is.getItemStack().getItem().getEquipSlot());

                                // If more than one of the item
                                if (is.getItemStack().getAmount() >= 2) {
                                    // Subtract one from the inventory stack and then swap
                                    is.getItemStack().setAmount(is.getItemStack().getAmount() - 1);
                                    Handler.get().giveItem(equipSwap.getItem(), equipSwap.getAmount());
                                    Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).setItem(new ItemStack(itemSwap.getItem(), 1));

                                } else {
                                    // Otherwise, swap the items and set the inventory stack to null
                                    is.setItemStack(null);
                                    Handler.get().giveItem(equipSwap.getItem(), equipSwap.getAmount());
                                    Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(itemSwap.getItem())).setItem(itemSwap);

                                }

                                // Add the equipment stats after equipping
                                Handler.get().getPlayer().addEquipmentStats(itemSwap.getItem().getEquipSlot());

                                // Set the swaps back to null
                                equipPressed = false;
                                hasBeenPressed = false;
                                itemSwap = null;
                                equipSwap = null;
                                BankUI.inventoryLoaded = false;
                            }
                        } else {
                            equipPressed = false;
                            hasBeenPressed = false;
                            return;
                        }
                    } else {
                        equipPressed = false;
                        hasBeenPressed = false;
                        return;
                    }
                }

                // If right-clicked on an item while CraftingUI is open
                if (CraftingUI.isOpen) {
                    if (slot.contains(mouse) && Handler.get().getMouseManager().isRightPressed() && equipPressed && !hasBeenPressed && !Handler.get().getMouseManager().isDragged()) {

                        hasBeenPressed = true;
                        if (is.getItemStack() != null) {
                            if (Handler.get().getCraftingUI().findFreeSlot(is.getItemStack().getItem()) == -1) {
                                // If all crafting slots are full, return
                                hasBeenPressed = false;
                                Handler.get().sendMsg("You cannot add more than 4 items to the crafting window.");
                                equipPressed = false;
                                return;
                            } else {
                                // Otherwise, remove the stack from the inventory and put it in a crafting slot
                                Handler.get().getCraftingUI().getCraftingSlots().get(Handler.get().getCraftingUI().findFreeSlot(is.getItemStack().getItem())).addItem(is.getItemStack().getItem(), is.getItemStack().getAmount());
                                is.setItemStack(null);
                                // Update if there is a possible recipe
                                Handler.get().getCraftingUI().findRecipe();
                                hasBeenPressed = false;
                                return;
                            }
                        } else {
                            hasBeenPressed = false;
                            return;
                        }
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        if (isOpen) {
            g.drawImage(Assets.invScreen, x, y, width, height, null);
            Text.drawString(g, "Inventory", x + 37, y + 24, false, Color.YELLOW, Assets.font14);

            Rectangle mouse = Handler.get().getMouse();

            for (ItemSlot is : itemSlots) {

                is.render(g);

                if (currentSelectedSlot != null) {
                    g.drawImage(currentSelectedSlot.getItem().getTexture(), Handler.get().getMouseManager().getMouseX() - 14,
                            Handler.get().getMouseManager().getMouseY() - 14, ItemSlot.SLOTSIZE - 4, ItemSlot.SLOTSIZE - 4, null);
                    if (currentSelectedSlot.getItem().isStackable())
                        Text.drawString(g, Integer.toString(currentSelectedSlot.getAmount()), Handler.get().getMouseManager().getMouseX() - 14, Handler.get().getMouseManager().getMouseY() - 4, false, Color.YELLOW, Assets.font14);
                }

                Rectangle temp2 = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);


                // If hovering over an item in the inventory, draw the tooltip
                if (temp2.contains(mouse) && is.getItemStack() != null) {
                    itemTooltip.render(is.getItemStack().getItem(), g);
                }
            }
        }
    }

    /*
     * Finds a free slot in the inventory
     * @returns: index if found, -1 if inventory is full
     */
    public int findFreeSlot(Item item) {
        boolean firstFreeSlotFound = false;
        int index = -1;
        for (int i = 0; i < itemSlots.size(); i++) {
            if (itemSlots.get(i).getItemStack() == null) {
                if (!firstFreeSlotFound) {
                    firstFreeSlotFound = true;
                    index = i;
                }
            } else if (itemSlots.get(i).getItemStack() != null && !item.isStackable()) {
                continue;
            } else if (itemSlots.get(i).getItemStack() != null && item.isStackable()) {
                if (itemSlots.get(i).getItemStack().getItem().getId() == item.getId()) {
                    return i;
                }
            }
        }
        if (index != -1)
            return index;

        Handler.get().sendMsg("Your inventory is full.");
        return -1;
    }

    public void giveItem(Item item, int amount) {
        int playerX = (int) Handler.get().getPlayer().getX();
        int playerY = (int) Handler.get().getPlayer().getY();
        if (!item.isStackable()) {
            if (findFreeSlot(item) == -1) {
                if (amount >= 1) {
                    Handler.get().dropItem(item, amount, playerX, playerY);
                    if (amount != 1)
                        giveItem(item, (amount - 1));
                    else
                        Handler.get().sendMsg("The item(s) were dropped to the floor.");
                }
            } else {
                if (amount >= 1) {
                    getItemSlots().get(findFreeSlot(item)).addItem(item, amount);
                    giveItem(item, (amount - 1));
                }
            }
        } else {
            if (findFreeSlot(item) == -1) {
                Handler.get().dropItem(item, amount, playerX, playerY);
                Handler.get().sendMsg("The item(s) were dropped to the floor.");
            } else {
                getItemSlots().get(findFreeSlot(item)).addItem(item, amount);
            }
        }
    }

    /*
     * Checks the inventory for a given item & quantity met
     * @returns boolean: true if player has item + quantity, false if player doesn't have item or doesn't have enough
     */
    public boolean playerHasItem(Item item, int amount) {
        boolean found = false;
        int foundAmount = 0;
        for (int i = 0; i < itemSlots.size(); i++) {
            // Skip empty slots
            if (itemSlots.get(i).getItemStack() == null) {
                continue;
            }

            //If the item is found
            if (item.getId() == itemSlots.get(i).getItemStack().getItem().getId()) {
                if ((itemSlots.get(i).getItemStack().getAmount() - amount) < 0) {
                    foundAmount += itemSlots.get(i).getItemStack().getAmount();
                    if (foundAmount >= amount) {
                        found = true;
                        break;
                    }
                } else if ((itemSlots.get(i).getItemStack().getAmount() - amount) >= 0) {
                    found = true;
                    break;
                }
            } else {
                continue;
            }
        }
        return found;
    }

    /*
     * Checks if the player has the item+quantity and removes it
     * @returns boolean: true if successful, false if item+quantity requirement not met
     */
    public boolean removeItem(Item item, int amount) {
        boolean hasItem = false;
        if (!playerHasItem(item, amount)) {
            Handler.get().sendMsg("You don't have " + amount + "x " + item.getName().toLowerCase() + ".");
            return hasItem;
        }
        List<Integer> matchSlots = new ArrayList<>();
        int foundAmount = 0;
        int leftOverAmount;
        for (int i = 0; i < itemSlots.size(); i++) {
            if (itemSlots.get(i).getItemStack() == null) {
                continue;
            }
            if (item.getId() == itemSlots.get(i).getItemStack().getItem().getId()) {
                if ((itemSlots.get(i).getItemStack().getAmount() - amount) < 0) {
                    matchSlots.add(i);
                    foundAmount += itemSlots.get(i).getItemStack().getAmount();
                    if (foundAmount >= amount) {
                        leftOverAmount = foundAmount - amount;
                        hasItem = true;
                        for (int j = 0; j < matchSlots.size(); j++) {
                            if (j == matchSlots.size() - 1) {
                                itemSlots
                                        .get(matchSlots.get(j))
                                        .getItemStack()
                                        .setAmount(leftOverAmount);
                                return hasItem;
                            }
                            foundAmount -= itemSlots.get(matchSlots.get(j)).getItemStack().getAmount();
                            itemSlots.get(matchSlots.get(j)).setItemStack(null);
                        }
                    }
                } else if ((itemSlots.get(i).getItemStack().getAmount() - amount) == 0) {
                    itemSlots.get(i).setItemStack(null);
                    hasItem = true;
                    return hasItem;
                } else if ((itemSlots.get(i).getItemStack().getAmount() - amount) >= 1) {
                    itemSlots.get(i).getItemStack().setAmount(itemSlots.get(i).getItemStack().getAmount() - amount);
                    hasItem = true;
                    return hasItem;
                }
            }
        }
        return hasItem;
    }

    /*
     * Checks if the player has the item+quantity and removes it
     * @returns boolean: true if successful, false if item+quantity requirement not met
     */
    public boolean removeBankItemSlot(ItemSlot is) {
        boolean hasItem = false;
        for (int i = 0; i < itemSlots.size(); i++) {
            if (itemSlots.get(i).getItemStack() == is.getItemStack()) {
                itemSlots.get(i).setItemStack(null);
                hasItem = true;
                break;
            }
        }
        return hasItem;
    }

    /*
     * Iterates over the inventory to see if there are any free slots
     * @returns boolean: true if full, false if not full
     */
    public boolean inventoryIsFull(Item item) {
        int emptySlots = 0;
        for (int i = 0; i < itemSlots.size(); i++) {
            if (itemSlots.get(i).getItemStack() == null) {
                emptySlots++;
                continue;
            }
            if (itemSlots.get(i).getItemStack().getItem().getId() == item.getId() && item.isStackable()) {
                return false;
            }
        }
        if (emptySlots == 0) {
            Handler.get().sendMsg("Your inventory is full.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Iterates over the inventory to see if the player has an item of the specified type
     *
     * @param type - the ItemType to query for
     * @return true if player has such an item, false if not
     */
    public boolean playerHasItemType(ItemType type) {
        for (ItemSlot slot : itemSlots) {

            ItemStack is = slot.getItemStack();

            if (is == null || is.getItem().getItemTypes() == null)
                continue;

            if (is.getItem().isType(type)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Checks the equipment slot of an item
     * @returns int: index of the equipment slot to be filled
     */
    public int checkEquipmentSlot(Item item) {
        if (item.getEquipSlot() >= 0 && item.getEquipSlot() <= 11)
            return item.getEquipSlot();

        return -10;
    }

    /*
     * Returns an item based on id
     * @param: Item ID
     */
    public Item getItemByID(int id) {
        return Item.items[id];
    }


    public CopyOnWriteArrayList<ItemSlot> getItemSlots() {
        return itemSlots;
    }


    public void setItemSlots(CopyOnWriteArrayList<ItemSlot> itemSlots) {
        this.itemSlots = itemSlots;
    }


    public Rectangle getWindowBounds() {
        return windowBounds;
    }


    public void setWindowBounds(Rectangle windowBounds) {
        this.windowBounds = windowBounds;
    }


    public int getNumCols() {
        return numCols;
    }


    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }


    public int getNumRows() {
        return numRows;
    }


    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }


    public ItemStack getCurrentSelectedSlot() {
        return currentSelectedSlot;
    }


    public void setCurrentSelectedSlot(ItemStack currentSelectedSlot) {
        this.currentSelectedSlot = currentSelectedSlot;
    }


    public int getWidth() {
        return width;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }

}