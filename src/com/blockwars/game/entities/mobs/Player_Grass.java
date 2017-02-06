package com.blockwars.game.entities.mobs;

import com.blockwars.CallbackEventClass;
import com.blockwars.graphics.Sprite;
import com.blockwars.utils.Util;

public class Player_Grass extends Player{
	
	public Player_Grass(double health, double damage, double speed) {
		super(Sprite.grass, health, damage, speed);
		playerType=1;
	}
	
	public void specialSkill(){
		if(flag){
			flag=false;
			double s=speed;
			Util.setTimeout(1000, new CallbackEventClass(new Object[]{s}){
				@Override
				public void callbackMethod() {
					speed=(double)objs[0];
					flag=true;
				}
			});
			speed*=2;
		}
	}
	
}
