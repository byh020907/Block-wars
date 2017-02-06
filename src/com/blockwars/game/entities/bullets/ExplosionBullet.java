package com.blockwars.game.entities.bullets;

import com.blockwars.game.entities.Particle;
import com.blockwars.game.entities.mobs.Mob;
import com.blockwars.game.entities.mobs.Player;
import com.blockwars.graphics.Sprite;
import com.blockwars.sound.AudioPlayer;

public class ExplosionBullet extends Bullet{
	public ExplosionBullet(Sprite sprite,Player shooter,double angle,double speed) {
		super(sprite,shooter,angle,speed);
	}
	
	public ExplosionBullet(Sprite sprite,double parentId,double id,double angle,double speed){
		super(sprite,parentId,id,angle,speed);
	}
	
	public ExplosionBullet(Sprite sprite,double id,double angle,double speed){
		super(sprite,id,angle,speed);
	}
	
	public void attack(Mob target){
		for(int i=0;i<360;i+=5){
			ReflectBullet b=new ReflectBullet(Sprite.basicBullet,shooter,i,speed);
			b.x=this.x;
			b.y=this.y;
			list.remove(this.id);
		}
	}
	
	public void collisionEvent(){
		Particle pc=new Particle(Sprite.basicParticle,x,y,0.9,70,50);
		attack(null);
	}
}
