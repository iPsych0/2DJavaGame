package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.data.AbilityManager;
import dev.ipsych0.myrinnia.abilities.effects.EffectManager;
import dev.ipsych0.myrinnia.abilities.ui.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.character.CharacterUI;
import dev.ipsych0.myrinnia.chatwindow.ChatWindow;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.entities.EntityManager;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.equipment.EquipmentWindow;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.hpoverlay.HPOverlay;
import dev.ipsych0.myrinnia.items.ItemManager;
import dev.ipsych0.myrinnia.items.ui.InventoryWindow;
import dev.ipsych0.myrinnia.quests.QuestManager;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.skills.ui.BountyBoardUI;
import dev.ipsych0.myrinnia.skills.ui.BountyContractUI;
import dev.ipsych0.myrinnia.skills.ui.BountyManager;
import dev.ipsych0.myrinnia.skills.ui.SkillsUI;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.tutorial.TutorialTipManager;
import dev.ipsych0.myrinnia.ui.CelebrationUI;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.MapLoader;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class World implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2377316128534163815L;

    private int width;
    private int height;
    private int[][][] tiles;
    private String[] layers;
    private String worldPath;
    private static boolean nightTime = false;
    private static int timeChecker = 60 * 60;
    private boolean initialized;
    private boolean hasPermissionsLayer;
    private int renderLayers;

    // Entities

    private EntityManager entityManager;

    // Items

    private ItemManager itemManager;

    private InventoryWindow inventory;
    private EquipmentWindow equipment;
    private CraftingUI craftingUI;
    private Player player;
    private ChatWindow chatWindow;
    private QuestManager questManager;
    private CharacterUI characterUI;
    private SkillsUI skillsUI;
    private HPOverlay hpOverlay;
    private AbilityManager abilityManager;
    private AbilityOverviewUI abilityOverviewUI;
    private TutorialTipManager tipManager;
    private BountyContractUI contractUI;
    private CelebrationUI celebrationUI;
    private List<ZoneTile> zoneTiles;
    private List<ZoneTile> toBeAddedZoneTiles;
    private Zone zone;
    private List<Weather> weatherEffects;
    private boolean dayNightCycle;

    private static final int radius = 800;
    private static final float[] fractions = {0.0f, 1.0f};
    private static final Color[] colors = {new Color(0, 13, 35, 96), new Color(0, 13, 35, 232)};
    private static final RadialGradientPaint paint = new RadialGradientPaint(Handler.get().getWidth() / 2f, Handler.get().
            getHeight() / 2f, radius, fractions, colors);

    public World(Zone zone, List<Weather> weatherEffects, boolean dayNightCycle, String path) {
        // First world path is already corrected per IDE/JAR
        if (!path.equalsIgnoreCase(Handler.initialWorldPath)) {
            String fixedFile;
            if (Handler.isJar) {
                fixedFile = Handler.jarFile.getParentFile().getAbsolutePath() + path;
            } else {
                fixedFile = path.replaceFirst("/", Handler.resourcePath);
            }
            this.worldPath = fixedFile;
        } else {
            this.worldPath = path;
        }
        this.zone = zone;
        this.weatherEffects = weatherEffects;
        this.dayNightCycle = dayNightCycle;

        // World-specific classes
        this.player = Handler.get().getPlayer();
        this.inventory = Handler.get().getInventory();
        this.equipment = Handler.get().getEquipment();
        this.chatWindow = Handler.get().getChatWindow();
        this.questManager = Handler.get().getQuestManager();
        this.characterUI = Handler.get().getCharacterUI();
        this.skillsUI = Handler.get().getSkillsUI();
        this.hpOverlay = Handler.get().getHpOverlay();
        this.abilityManager = Handler.get().getAbilityManager();
        this.craftingUI = Handler.get().getCraftingUI();
        this.abilityOverviewUI = Handler.get().getAbilityOverviewUI();
        this.tipManager = Handler.get().getTutorialTipManager();
        this.contractUI = Handler.get().getContractUI();
        this.celebrationUI = Handler.get().getCelebrationUI();

        // This is each world's unique manager of Entities & Items
        entityManager = new EntityManager(player);
        itemManager = new ItemManager();
        zoneTiles = new ArrayList<>();
        toBeAddedZoneTiles = new ArrayList<>();

        // Only initialize the starting world on start-up
        if (worldPath.equalsIgnoreCase(Handler.initialWorldPath)) {
            init();
        }

    }

    public World(Zone zone, String path) {
        this(zone, new ArrayList<>(), true, path);
    }

    public World(Zone zone, boolean dayNightCycle, String path) {
        this(zone, new ArrayList<>(), dayNightCycle, path);
    }

    public World(Zone zone, List<Weather> weatherEffects, String path) {
        this(zone, weatherEffects, true, path);
    }

    public void init() {
        if (!initialized) {
            MapLoader.setWorldDoc(worldPath);

            width = MapLoader.getMapWidth();
            height = MapLoader.getMapHeight();

            loadWorld();

            // Render all but the top layer if we have a permissionsLayer
            renderLayers = hasPermissionsLayer ? (layers.length - 1) : (layers.length);

            // Load in the enemies, items and zone tiles from Tiled editor
            MapLoader.initEnemiesItemsAndZoneTiles(worldPath, this);

            initialized = true;
        }
    }

    public void tick() {
        if (Handler.get().getWorld().equals(this)) {

            if (zoneTiles.addAll(toBeAddedZoneTiles)) {
                toBeAddedZoneTiles.clear();
            }

            itemManager.tick();
            entityManager.tick();
            inventory.tick();
            equipment.tick();
            craftingUI.tick();
            chatWindow.tick();
            questManager.tick();
            characterUI.tick();
            skillsUI.tick();
            hpOverlay.tick();
            abilityManager.tick();
            abilityOverviewUI.tick();
            tipManager.tick();
            contractUI.tick();
            BountyManager.get().tick();
            celebrationUI.tick();
            if (BankUI.isOpen && player.getBankEntity() != null)
                Handler.get().getBankUI().tick();
            if (ShopWindow.isOpen && player.getShopKeeper() != null)
                player.getShopKeeper().getShopWindow().tick();
            if (AbilityShopWindow.isOpen && player.getAbilityTrainer() != null) {
                player.getAbilityTrainer().getAbilityShopWindow().tick();
            }
            if (BountyBoardUI.isOpen && player.getBountyBoard() != null) {
                player.getBountyBoard().getBountyBoardUI().tick();
            }

            EffectManager.get().tick();

            // Check for night-time every minute
            if (dayNightCycle && timeChecker++ >= 60 * 60) {
                timeChecker = 0;

                // Get the current hour of day
                Calendar c = Calendar.getInstance();
                int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

                // Set to night time if between 8 PM and 8 AM
                nightTime = (timeOfDay >= 20 && timeOfDay < 24) || timeOfDay >= 0 && timeOfDay < 8;
            }

            // Check if player is moving to next zone
            for (ZoneTile zt : zoneTiles) {
                zt.tick();
            }

        }

    }

    public void render(Graphics2D g) {
        if (Handler.get().getWorld().equals(this)) {
            // Get the dimension once at the start
            int screenWidth = Handler.get().getWidth();
            int screenheight = Handler.get().getHeight();
            double xOffset = Handler.get().getGameCamera().getxOffset();
            double yOffset = Handler.get().getGameCamera().getyOffset();

            // Set variables for rendering only the tiles that show on screen
            int xStart = (int) Math.max(0, xOffset / Tile.TILEWIDTH);
            int xEnd = (int) Math.min(width, (xOffset + screenWidth) / Tile.TILEWIDTH + 1);
            int yStart = (int) Math.max(0, yOffset / Tile.TILEHEIGHT);
            int yEnd = (int) Math.min(height, (yOffset + screenheight) / Tile.TILEHEIGHT + 1);

            // Render the tiles
            List<Tile> renderOverTiles = new ArrayList<>();
            List<Integer> xCoords = new ArrayList<>();
            List<Integer> yCoords = new ArrayList<>();

            for (int i = 0; i < renderLayers; i++) {
                for (int y = yStart; y < yEnd; y++) {
                    for (int x = xStart; x < xEnd; x++) {
                        Tile t = getTile(i, x, y);
                        if (t != Tile.tiles[0]) {
                            int xPos = (int) (x * Tile.TILEWIDTH - xOffset);
                            int yPos = (int) (y * Tile.TILEHEIGHT - yOffset);
                            if (t.isPostRendered()) {
                                if (Handler.debugCollision && hasPermissionsLayer && i == (renderLayers - 1)) {
                                    Tile permission = getTile(i + 1, x, y);
                                    if (permission != Tile.tiles[0]) {
                                        renderOverTiles.add(permission);
                                        xCoords.add(xPos);
                                        yCoords.add(yPos);
                                    }
                                }
                                renderOverTiles.add(t);
                                xCoords.add(xPos);
                                yCoords.add(yPos);
                                continue;
                            }
                            t.tick();
                            t.render(g, xPos, yPos);
                            if (Handler.debugCollision) {
                                g.setColor(Colors.unwalkableColour);
                                g.drawRect(xPos, yPos, Tile.TILEWIDTH, Tile.TILEHEIGHT);
                                if (t.isSolid()) {
                                    g.fillRect(xPos, yPos, Tile.TILEWIDTH, Tile.TILEHEIGHT);
                                }
                            }
                        }

                        // Render permission tiles
                        if (Handler.debugCollision) {
                            if (hasPermissionsLayer && i == (renderLayers - 1)) {
                                int xPos = (int) (x * Tile.TILEWIDTH - xOffset);
                                int yPos = (int) (y * Tile.TILEHEIGHT - yOffset);

                                Tile permission = getTile(i + 1, x, y);
                                if (permission != Tile.tiles[0]) {
                                    permission.render(g, xPos, yPos);
                                }
                            }
                        }
                    }
                }
            }

            // Items

            itemManager.render(g);

            for (Ability a : abilityManager.getActiveAbilities()) {
                a.renderUnderEntity(g);
            }

            // Entities
            entityManager.render(g);

            EffectManager.get().render(g);

//        Composite composite = g.getComposite();
            for (int i = 0; i < renderOverTiles.size(); i++) {
                renderOverTiles.get(i).tick();
//            if(standingUnderPostRenderTile){
//                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
//                g.setComposite(ac);
//            }
                if (renderOverTiles.get(i).getPermission() != null) {
                    if (Handler.debugCollision) {
                        renderOverTiles.get(i).render(g, xCoords.get(i), yCoords.get(i));
                    }
                } else {
                    renderOverTiles.get(i).render(g, xCoords.get(i), yCoords.get(i));
                }
            }
//        g.setComposite(composite);

            for (Weather weather : weatherEffects) {
                weather.tick();
                weather.render(g);
            }

            if (dayNightCycle && nightTime) {
                renderNight(g);
            }


            for (Weather weather : weatherEffects) {
                if (weather instanceof Rain) {
                    ((Rain) weather).renderThunder(g);
                }
            }

            itemManager.postRender(g);
            entityManager.postRender(g);

            craftingUI.render(g);

            // Inventory & Equipment
            Composite current = g.getComposite();
            if (Handler.get().getGameCamera().isAtRightBound()) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
            equipment.render(g);
            inventory.render(g);
            if (Handler.get().getGameCamera().isAtRightBound()) {
                g.setComposite(current);
            }

            player.postRender(g);

            // Chat
            chatWindow.render(g);

            questManager.render(g);
            characterUI.render(g);
            skillsUI.render(g);

            abilityManager.render(g);

            abilityOverviewUI.render(g);

            hpOverlay.render(g);

            if (BankUI.isOpen && player.getBankEntity() != null) {
                Handler.get().getBankUI().render(g);
                Text.drawString(g, "Bank of Myrinnia", BankUI.x + (BankUI.width / 2), BankUI.y + 16, true, Color.YELLOW, Assets.font14);
            }
            if (ShopWindow.isOpen && player.getShopKeeper() != null) {
                ShopWindow sw = player.getShopKeeper().getShopWindow();
                sw.render(g);
                Text.drawString(g, player.getShopKeeper().getShopName(), sw.x + (sw.width / 2), sw.y + 16, true, Color.YELLOW, Assets.font14);
            }
            if (AbilityShopWindow.isOpen && player.getAbilityTrainer() != null) {
                player.getAbilityTrainer().getAbilityShopWindow().render(g);
            }
            if (BountyBoardUI.isOpen && player.getBountyBoard() != null) {
                player.getBountyBoard().getBountyBoardUI().render(g);
            }

            contractUI.render(g);

            celebrationUI.render(g);

            for (ZoneTile zt : zoneTiles) {
                zt.render(g);
            }

            tipManager.render(g);
        }
    }

    public Tile getTile(int layer, int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return null;
        return Tile.tiles[tiles[layer][x][y]];
    }

    private void renderNight(Graphics2D g) {
        Paint originalPaint = g.getPaint();
        Composite originalComposite = g.getComposite();

        g.setPaint(paint);
        g.fillOval(Handler.get().getWidth() / 2 - radius, Handler.get().getHeight() / 2 - radius, radius * 2, radius * 2);

        g.setComposite(originalComposite);
        g.setPaint(originalPaint);
    }

    private void loadWorld() {
        layers = MapLoader.getMapTiles(this);
        tiles = new int[layers.length][width][height];

        for (int i = 0; i < layers.length; i++) {
            // Splits worlds files by spaces and puts them all in an array
            layers[i] = layers[i].replace("\n", "").replace("\r", "").trim();
            String[] tokens = layers[i].split(",");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Loads in the actual tiles to the tiles[][][]
                    tiles[i][x][y] = Utils.parseInt(tokens[(x + y * width)]);
                }
            }
        }
    }

    public void addRuntimeZoneTile(ZoneTile zoneTile) {
        toBeAddedZoneTiles.add(zoneTile);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public String[] getLayers() {
        return layers;
    }

    public void setLayers(String[] layers) {
        this.layers = layers;
    }

    public InventoryWindow getInventory() {
        return inventory;
    }

    public void setInventory(InventoryWindow inventory) {
        this.inventory = inventory;
    }

    public EquipmentWindow getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentWindow equipment) {
        this.equipment = equipment;
    }

    public CraftingUI getCraftingUI() {
        return craftingUI;
    }

    public void setCraftingUI(CraftingUI craftingUI) {
        this.craftingUI = craftingUI;
    }

    public ChatWindow getChatWindow() {
        return chatWindow;
    }

    public void setChatWindow(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public void setQuestManager(QuestManager questManager) {
        this.questManager = questManager;
    }

    public String getWorldPath() {
        return worldPath;
    }

    public void setWorldPath(String worldPath) {
        this.worldPath = worldPath;
    }

    public List<ZoneTile> getZoneTiles() {
        return zoneTiles;
    }

    public void setZoneTiles(List<ZoneTile> zoneTiles) {
        this.zoneTiles = zoneTiles;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public List<Weather> getWeatherEffects() {
        return weatherEffects;
    }

    public void setWeatherEffects(List<Weather> weatherEffects) {
        this.weatherEffects = weatherEffects;
    }

    public boolean isDayNightCycle() {
        return dayNightCycle;
    }

    public void setDayNightCycle(boolean dayNightCycle) {
        this.dayNightCycle = dayNightCycle;
    }

    public boolean hasPermissionsLayer() {
        return hasPermissionsLayer;
    }

    public void setHasPermissionsLayer(boolean hasPermissionsLayer) {
        this.hasPermissionsLayer = hasPermissionsLayer;
    }
}
