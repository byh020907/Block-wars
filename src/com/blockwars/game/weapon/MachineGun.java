package com.blockwars.game.weapon;

import org.json.simple.JSONObject;

import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.game.entities.bullets.ReflectBullet;
import com.blockwars.game.entities.mobs.Player;
import com.blockwars.graphics.Sprite;
import com.blockwars.network.Network;
import com.blockwars.sound.AudioPlayer;
import com.blockwars.utils.Util;

public class MachineGun extends Gun{

	public MachineGun(Player owner, int maxCapacity, int magazineCapacity, int attackSpeed, double bulletSpeed) {
		super(owner, maxCapacity, magazineCapacity, attackSpeed, bulletSpeed);
		this.knockBack=0.5;
	}
	
	public void attackProcess(double angle){
		JSONObject data=new JSONObject();
		double id=Math.random();
		data.put("protocol", "attack");
		data.put("bulletType", Bullet.NORMAL_BULLET);
		data.put("parentId", owner.id);
		data.put("id", id);
		data.put("x", owner.x);
		data.put("y", owner.y);
		data.put("angle", angle);
		data.put("speed", bulletSpeed);
		Network.send(data,Network.ia,Network.port);
		
		Bullet b=new Bullet(Sprite.basicBullet,owner.id,id,angle,bulletSpeed);
		
		AudioPlayer.SHOT1.play();
		
		if(!owner.collision(owner.x+Math.cos(Util.Deg_to_Rad(180+angle))*knockBack, owner.y+Math.sin(Util.Deg_to_Rad(180+angle))*knockBack)){
			Util.move(owner,180+angle,knockBack);
		}
	}

}
