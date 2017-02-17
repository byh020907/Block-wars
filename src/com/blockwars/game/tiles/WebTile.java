package com.blockwars.game.tiles;

import org.json.simple.JSONObject;

import com.blockwars.CallbackEvent_Argument;
import com.blockwars.game.entities.MoveEntity;
import com.blockwars.game.entities.mobs.Mob;
import com.blockwars.graphics.Sprite;
import com.blockwars.network.Network;
import com.blockwars.state.MainGameState;
import com.blockwars.utils.Util;

public class WebTile extends Tile {
	private boolean flag=true;
	public WebTile(Sprite sprite, int x, int y, int size) {
		super(sprite, x, y, size);
	}

	public WebTile(Sprite sprite,int size) {
		super(sprite,size);
	}
	
	public void collisionEvent(MoveEntity moveEntity){
		moveEntity.speed=0;
	}
	
	public void collisionEvent(Mob mob){
		if(flag&&!mob.inWeb){
			flag=false;
			mob.inWeb=true;
			double s=mob.speed;
			Util.setTimeout(1000, new CallbackEvent_Argument(new Object[]{s}){
				@Override
				public void callbackMethod() {
					Map map=MainGameState.map;
					int xx=(int)(x/map.tileSize);
					int yy=(int)(y/map.tileSize);
					JSONObject data=new JSONObject();
					data.put("protocol", "removeBlock");
					data.put("id", -1);
					data.put("x", xx);
					data.put("y", yy);
					data.put("depth", 1);
					Network.send(data, Network.ia, Network.port);
					map.removeTile(xx, yy, 1);
					mob.speed=(double)objs[0];
					mob.inWeb=false;
					flag=true;
				}
			});
			mob.speed/=4;
		}
	}

}
