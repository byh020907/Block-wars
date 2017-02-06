package com.blockwars.game.entities.mobs;

import org.json.simple.JSONObject;

import com.blockwars.CallbackEvent;
import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.game.entities.bullets.ExplosionBullet;
import com.blockwars.graphics.Sprite;
import com.blockwars.network.Network;
import com.blockwars.utils.Util;

public class Player_Dirt extends Player{

	public Player_Dirt(double health, double damage, double speed) {
		super(Sprite.dirt, health, damage, speed);
		this.playerType=3;
	}
	
	public void specialSkill(){
		if(flag){
			for(int i=0;i<360;i+=30){
				flag=false;
				JSONObject data=new JSONObject();
				double id=Math.random();
				double bulletSpeed=1.7;
				data.put("protocol", "attack");
				data.put("bulletType", Bullet.EXPLOSION_BULLET);
				data.put("parentId", this.id);
				data.put("id", id);
				data.put("x", this.x);
				data.put("y", this.y);
				data.put("angle", (double)i);
				data.put("speed", bulletSpeed);
				Network.send(data,Network.ia,Network.port);
				
				ExplosionBullet b=new ExplosionBullet(Sprite.TNTTop,this.id,id,i,bulletSpeed);
			}
			
			Util.setTimeout(5000, new CallbackEvent(){
				@Override
				public void callbackMethod() {
					flag=true;
				}
			});
		}
	}

}
