package com.blockwars.game.entities.mobs;

import com.blockwars.game.Game;
import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.graphics.Sprite;
import com.blockwars.utils.Util;

public class Zombie extends Mob{

	public Zombie(Sprite sprite, double health, double damage, double speed) {
		super(sprite, health, damage, speed);
	}
	
	public void update(){
//		move(Util.obtainAngle(this, Game.user));
//		if(Util.hitTestBox(this, Game.user, 0)){
//			attack(Game.user);
//		}
	}
	
}
