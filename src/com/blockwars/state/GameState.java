package com.blockwars.state;

import java.awt.Graphics2D;

import com.blockwars.graphics.Screen;
import com.blockwars.state.GameState.ReceiveLoop;
import com.blockwars.state.GameState.SendLoop;
import com.sun.glass.events.WindowEvent;

public abstract class GameState {

	protected GameStateManager gsm;
	
	public abstract void init();
	public abstract void reset();
	public abstract void update();
	public abstract void render(Screen screen);
	public abstract void draw(Graphics2D g2D);
	public abstract void sendData();
	public abstract void receiveData();
	
	public SendLoop sendLoop;
	public ReceiveLoop receiveLoop;
	
	public class SendLoop extends Thread{
		@Override
		public void run() {
			while(true){
				sendData();
			}
		}
	}
	
	public class ReceiveLoop extends Thread{
		@Override
		public void run() {
			while(true){
				receiveData();
			}
		}
	}
}
