package com.blockwars.state;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.blockwars.graphics.Screen;

public class GameStateManager {
	
	private ArrayList<GameState> list=new ArrayList<>();
	public static Object obj1=new Object();
	private int currentState;
	
	public static final int MENU_STATE=0;
	public static final int SIGN_UP_STATE=1;
	public static final int LOGIN_STATE=2;
	public static final int LOBBY_STATE=3;
	public static final int GAMEROOM_STATE=4;
	public static final int MAINGAME_STATE=5;
	
	public GameStateManager(){
		list.add(new MenuState(this));
		list.add(new SignUpState(this));
		list.add(new LoginState(this));
		list.add(new LobbyState(this));
		list.add(new GameRoomState(this));
		list.add(new MainGameState(this));
		setState(MENU_STATE);
	}
	
	public void setState(int state) {
		synchronized(obj1){
			list.get(currentState).reset();
			currentState = state;
			list.get(currentState).init();
		}
	}
	
	public void update() {
		list.get(currentState).update();
	}
	
	public void render(Screen screen) {
		list.get(currentState).render(screen);
	}
	
	public void draw(Graphics2D g2D) {
		list.get(currentState).draw(g2D);
	}
}	
