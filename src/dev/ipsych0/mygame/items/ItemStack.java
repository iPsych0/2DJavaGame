package dev.ipsych0.mygame.items;

import java.io.Serializable;

public class ItemStack implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int amount;
	private Item item;
	
	public ItemStack(Item item){
		this.item = item;
		this.amount = 1;
	}
	
	public ItemStack(Item item, int amount){
		this.item = item;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
