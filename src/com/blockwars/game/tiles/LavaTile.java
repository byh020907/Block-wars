package com.blockwars.game.tiles;

import com.blockwars.CallbackEvent;
import com.blockwars.game.entities.MoveEntity;
import com.blockwars.game.entities.mobs.Mob;
import com.blockwars.graphics.Sprite;
import com.blockwars.utils.Util;

public class LavaTile extends Tile {

	public LavaTile(Sprite sprite, int x, int y, int size) {
		super(sprite, x, y, size);
	}
	
	public LavaTile(Sprite sprite,int size) {
		super(sprite,size);
	}
	
	public void collisionEvent(Mob mob){
		tick(mob,15);
	}
	
	private void tick(Mob mob,int count){
		Util.setTimeout(100, new CallbackEvent(){
			@Override
			public void callbackMethod() {
				mob.currentHealth-=0.1;
				if(mob.currentHealth<0){
					mob.remove();
				}
				if(count!=0){
					tick(mob,count-1);
				}
			}
		});
	}

}
