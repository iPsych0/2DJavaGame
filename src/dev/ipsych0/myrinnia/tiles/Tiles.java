package dev.ipsych0.myrinnia.tiles;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.SpriteSheet;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tiles {

    // Get all Tiled firstGids

    // Set the Tiles-size to the maximum firstGID + the tilecount of the last tileset (aka the very last ID)
    public static Tiles[] tiles = new Tiles[(SpriteSheet.firstGids[SpriteSheet.firstGids.length - 1]
            + MapLoader.getTileCount(Handler.initialWorldPath, SpriteSheet.firstGids.length - 1))];

    /*
     * Class data
     */

    public static final int TILEWIDTH = 32, TILEHEIGHT = 32;

    protected BufferedImage texture;
    protected final int id;
    protected int x, y;
    protected boolean solid, postRendered;
    private Polygon bounds;

    public Tiles(BufferedImage texture, int id, boolean solid) {
        this.texture = texture;
        this.id = id;
        this.solid = solid;
    }

    public Tiles(BufferedImage texture, int id, boolean solid, int[] x, int[] y, int n) {
        this.texture = texture;
        this.id = id;
        this.solid = solid;
        this.bounds = new Polygon(x, y, n);
    }

    public Tiles(BufferedImage texture, int id, boolean solid, boolean postRendered) {
        this.texture = texture;
        this.id = id;
        // Always false, because there is no point in post-render if an Entity can't walk behind this Tile
        if(postRendered && solid) {
            this.solid = true;
            this.postRendered = false;
        }else{
            this.solid = solid;
            this.postRendered = postRendered;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick() {

    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
        if(bounds != null){
            g.setColor(Color.BLUE);
            g.drawPolygon(bounds);
        }
    }

    public void renderMiniMap(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, (int) TILEWIDTH * 25 / 100, (int) TILEHEIGHT * 25 / 100, null);
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public boolean isSolid() {
        return solid;
    }

    public static Tiles getTileByID(int id) {
        return tiles[id];
    }

    public int getId() {
        return id;
    }

    public boolean isPostRendered() {
        return postRendered;
    }

    public void setPostRendered(boolean postRendered) {
        this.postRendered = postRendered;
    }

    public Polygon getBounds() {
        return bounds;
    }

    public void setBounds(Polygon bounds) {
        this.bounds = bounds;
    }
}
