package com.blockwars.game.entities.mobs;

import org.json.simple.JSONObject;

import com.blockwars.CallbackEvent_Argument;
import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.game.entities.bullets.ReflectBullet;
import com.blockwars.graphics.Sprite;
import com.blockwars.network.Network;
import com.blockwars.utils.Util;

public class Player_Stone extends Player{

	public Player_Stone(double health, double damage, double speed) {
		super(Sprite.stone, health, damage, speed);
		this.playerType=2;
	}
	
	public void specialSkill(){
		if(flag){
			flag=false;
			double s=speed;
			double d=damage;
			Util.setTimeout(1000, new CallbackEvent_Argument(new Object[]{s,d}){
				@Override
				public void callbackMethod() {
					speed=(double)objs[0];
					damage=(double)objs[1];
					flag=true;
				}
			});
			speed*=0.7;
			damage*=2;
			
			for(int i=0;i<360;i+=5){
				flag=false;
				JSONObject data=new JSONObject();
				double id=Math.random();
				double bulletSpeed=5;
				data.put("protocol", "attack");
				data.put("bulletType", Bullet.REFLECT_BULLET);
				data.put("parentId", this.id);
				data.put("id", id);
				data.put("x", this.x);
				data.put("y", this.y);
				data.put("angle", (double)i);
				data.put("speed", bulletSpeed);
				Network.send(data,Network.ia,Network.port);
				
				ReflectBullet b=new ReflectBullet(Sprite.basicBullet,this.id,id,i,bulletSpeed);
			}
			
		}
	}
}
