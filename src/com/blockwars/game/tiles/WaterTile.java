package com.blockwars.game.tiles;

import com.blockwars.CallbackEventClass;
import com.blockwars.game.entities.mobs.Mob;
import com.blockwars.graphics.Sprite;
import com.blockwars.utils.Util;

public class WaterTile extends Tile{
	private boolean flag=true;
	
	
	public WaterTile(Sprite sprite, int x, int y, int size) {
		super(sprite, x, y, size);
	}
	
	public WaterTile(Sprite sprite,int size) {
		super(sprite,size);
	}
	
	public void collisionEvent(Mob mob){
		if(flag&&!mob.inWater){
			flag=false;
			mob.inWater=true;
			double s=mob.speed;
			Util.setTimeout(100, new CallbackEventClass(new Object[]{s}){
				@Override
				public void callbackMethod() {
					mob.speed=(double)objs[0];
					mob.inWater=false;
					flag=true;
				}
			});
			mob.speed/=2;
		}
	}
}
