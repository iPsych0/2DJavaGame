package dev.ipsych0.mygame.states;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import dev.ipsych0.mygame.Handler;

public class UITransitionState extends AbstractTransitionState {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private State newState;

	public UITransitionState(Handler handler, State newState) {
		super(handler);
		this.newState = newState;
	}

	@Override
	public void tick() {
		newState.tick();
		if(alpha == 0) {
			State.setState(newState);
		}
	}

	@Override
	public void render(Graphics g) {
		newState.render(g);
		
		// Fade from black
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		((Graphics2D) g).setComposite(ac);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		if(alpha - (0.5 / 60) < 0)
			alpha = 0;
		else
			alpha -= (0.5 / 60);
		
		((Graphics2D) g).dispose();
	}

	public State getNewState() {
		return newState;
	}

	public void setNewState(State newState) {
		this.newState = newState;
	}

}
