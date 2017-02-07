package com.blockwars.state;

import java.awt.Graphics2D;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.blockwars.CallbackEvent;
import com.blockwars.UI.UI;
import com.blockwars.UI.UI_Background;
import com.blockwars.UI.UI_Button;
import com.blockwars.UI.UI_TextField;
import com.blockwars.game.Game;
import com.blockwars.game.Resource;
import com.blockwars.game.entities.Particle;
import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.game.entities.mobs.Player;
import com.blockwars.graphics.Screen;
import com.blockwars.sound.AudioPlayer;


public class MenuState extends GameState{
	
	public MenuState(GameStateManager gsm) {
		this.gsm=gsm;
	}

	@Override
	public void init() {
		UI_Background background=new UI_Background(Resource.UI_background,-1000,0,2000,800);
		background.setDepth(0.1);
		UI_Button bb=new UI_Button(Resource.startImg,(Game.width*Game.scale)/2-75,(Game.height*Game.scale)/2-75,150,150);
		bb.setDepth(0.2);
		bb.clickEvent=new CallbackEvent(){
			public void callbackMethod(){
				gsm.setState(GameStateManager.SIGN_UP_STATE);
			}
		};
		AudioPlayer.BACKGROUND.play();
	}
	
	@Override
	protected void network() {
		
	}
	
	@Override
	public void reset() {
		UI.list=new ConcurrentHashMap<Double,UI>();
	}
	
	@Override
	public void update() {
		UI.updateAll();
	}
	
	@Override
	public void render(Screen screen) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Graphics2D g2D) {
		try {
			UI.renderAll(g2D);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveData() {
		// TODO Auto-generated method stub
		
	}

}
