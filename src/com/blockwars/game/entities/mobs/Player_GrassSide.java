package com.blockwars.game.entities.mobs;

import com.blockwars.CallbackEventClass;
import com.blockwars.game.weapon.Gun;
import com.blockwars.graphics.Sprite;
import com.blockwars.utils.Util;

public class Player_GrassSide extends Player{

	public Player_GrassSide(double health, double damage, double speed) {
		super(Sprite.grassSide,health, damage, speed);
		this.playerType=4;
	}
	
	public void specialSkill(){
		if(flag){
			flag=false;
			int attackSpeed=((Gun)weapons[currentSelectNum]).attackSpeed;
			Util.setTimeout(1500, new CallbackEventClass(new Object[]{attackSpeed,currentSelectNum}){
				@Override
				public void callbackMethod() {
					((Gun)weapons[(int)objs[1]]).attackSpeed=(int)objs[0];
					flag=true;
				}
			});
			((Gun)weapons[currentSelectNum]).attackSpeed/=2;
		}
	}

}
