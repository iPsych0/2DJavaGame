package dev.ipsych0.myrinnia.entities.npcs;

import java.util.ArrayList;

import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.items.ItemStack;
import dev.ipsych0.myrinnia.shops.ShopWindow;

public abstract class ShopKeeper extends Creature {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3802705595380640443L;
	protected ShopWindow shopWindow;
	protected String shopName;

	public ShopKeeper(String shopName, float x, float y, int width, int height) {
		super(x, y, width, height);

		attackable = false;
		isNpc = true;

		this.shopName = shopName;
		shopWindow = new ShopWindow(new ArrayList<ItemStack>());
		
	}
	
	public ShopWindow getShopWindow() {
		return shopWindow;
	}

	public void setShopWindow(ShopWindow shopWindow) {
		this.shopWindow = shopWindow;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}