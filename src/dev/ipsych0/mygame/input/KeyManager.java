package dev.ipsych0.mygame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;

public class KeyManager implements KeyListener{

	private boolean[] keys;
	private Handler handler;
	public boolean up, down, left, right;
	public boolean attack;
	public boolean chat;
	public boolean pickUp;
	public boolean position;
	public boolean talk;
	
	public KeyManager(){
		keys = new boolean[256];
	}
	
	public void tick(){
		// Movement keys
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		
		// Attack keys
		attack = keys[KeyEvent.VK_Z];

		
		// Interaction keys
		chat = keys[KeyEvent.VK_C];
		pickUp = keys[KeyEvent.VK_F];
		
		// Coordinate keys
		position = keys[KeyEvent.VK_P];
		talk = keys[KeyEvent.VK_SPACE];
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
		// Inventory toggle
		if(e.getKeyCode() == KeyEvent.VK_I){
			if(!InventoryWindow.isOpen){
				InventoryWindow.isOpen = true;
			}
			else {
				InventoryWindow.isOpen = false;
			}
		}
		
		// Inventory toggle
		if(e.getKeyCode() == KeyEvent.VK_E){
			if(!EquipmentWindow.isOpen){
				EquipmentWindow.isOpen = true;
			}
			else {
				EquipmentWindow.isOpen = false;
			}
		}
		
		// Chat window toggle
		if(e.getKeyCode() == KeyEvent.VK_C){
			if(!ChatWindow.chatIsOpen){
				ChatWindow.chatIsOpen = true;
			}
			else {
				ChatWindow.chatIsOpen = false;
			}
		}
		
		// Ensures pick-up key doesn't stick to active
		if(e.getKeyCode() == KeyEvent.VK_F){
			if(!Item.pickUpKeyPressed){
				Item.pickUpKeyPressed = true;
			}
			else {
				Item.pickUpKeyPressed = false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_SPACE){
			Entity.speakingTurn++;
		}
	}

}
