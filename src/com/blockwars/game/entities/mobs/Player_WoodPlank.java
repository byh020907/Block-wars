package com.blockwars.game.entities.mobs;

import org.json.simple.JSONObject;

import com.blockwars.CallbackEvent;
import com.blockwars.game.tiles.Tile;
import com.blockwars.game.tiles.WebTile;
import com.blockwars.graphics.Sprite;
import com.blockwars.network.Network;
import com.blockwars.utils.Util;

public class Player_WoodPlank extends Player{

	public Player_WoodPlank(double health, double damage, double speed) {
		super(Sprite.woodPlank, health, damage, speed);
		this.playerType=5;
	}
	
	public void specialSkill(){
		if(flag){
			flag=false;
			int x=(int)(this.x/map.tileSize);
			int y=(int)(this.y/map.tileSize);
			for(int yy=y-2;yy<=y+2;yy++){
				if(yy<0||yy>=map.height)continue;
				for(int xx=x-2;xx<=x+2;xx++){
					if(xx<0||xx>=map.height)continue;
					if(map.tiles[1][xx+yy*map.width]==null){
						JSONObject data=new JSONObject();
						data.put("protocol", "setBlock");
						data.put("id", this.id);
						data.put("tileNum", Tile.WEB_TILE);
						data.put("x", xx);
						data.put("y", yy);
						data.put("depth", 1);
						Network.send(data, Network.ia, Network.port);
						map.setTile(new WebTile(Sprite.web,map.tileSize), xx, yy, 1);
					}
				}
			}
			Util.setTimeout(7500, new CallbackEvent(){
				@Override
				public void callbackMethod() {
					flag=true;
				}
			});
		}
	}

}
