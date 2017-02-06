package com.blockwars.state;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.blockwars.graphics.Screen;

public class GameStateManager {
	public static Object obj1=new Object();
	private ArrayList<GameState> gameStates=new ArrayList<GameState>();
	private int currentState;
	
	public static final int MENU_STATE = 0;
	public static final int LOGIN_STATE=1;
	public static final int MAINGAME_STATE=2;
	
	public GameStateManager(){
		gameStates.add(new MenuState(this));
		gameStates.add(new LoginState(this));
		gameStates.add(new MainGameState(this));
		setState(MENU_STATE);
	}
	
	public void setState(int state) {
		synchronized(obj1){
			gameStates.get(currentState).reset();
			currentState = state;
			gameStates.get(currentState).init();
		}
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void render(Screen screen) {
		gameStates.get(currentState).render(screen);
	}
	
	public void draw(Graphics2D g2D) {
		gameStates.get(currentState).draw(g2D);
	}
}	
