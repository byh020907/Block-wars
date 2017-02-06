package com.blockwars.game.entities.bullets;

import java.util.concurrent.ConcurrentHashMap;

import com.blockwars.game.entities.MoveEntity;
import com.blockwars.game.entities.Particle;
import com.blockwars.game.entities.mobs.Mob;
import com.blockwars.game.entities.mobs.Player;
import com.blockwars.graphics.Screen;
import com.blockwars.graphics.Sprite;
import com.blockwars.sound.AudioPlayer;
import com.blockwars.utils.Util;

public class Bullet extends MoveEntity{
	
	public static final int NORMAL_BULLET=0;
	public static final int EXPLOSION_BULLET=1;
	public static final int REFLECT_BULLET=2;
	
	transient Player shooter;
	double angle;
	double damage;
	transient int lifeTime=100;
	public double parentId;
	public static ConcurrentHashMap<Double,Bullet> list=new ConcurrentHashMap<Double,Bullet>();
	public Bullet(Sprite sprite,Player shooter,double angle,double speed) {
		super(sprite,speed);
		this.shooter=shooter;
		this.parentId=shooter.id;
		this.damage=shooter.damage;
		this.angle=angle;
		this.x=shooter.x;
		this.y=shooter.y;
		list.put(this.id, this);
	}
	
	public Bullet(Sprite sprite,double parentId,double id,double angle,double speed) {
		super(sprite,speed);
		this.shooter=Player.list.get(parentId);
		this.parentId=parentId;
		this.id=id;
		this.damage=(shooter!=null)?shooter.damage:0;
		this.angle=angle;
		this.x=(shooter!=null)?shooter.x:0;
		this.y=(shooter!=null)?shooter.y:0;
		list.put(this.id, this);
	}
	
	public Bullet(Sprite sprite,double id,double angle,double speed) {
		super(sprite,speed);
		this.id=id;
		this.angle=angle;
		list.put(this.id, this);
	}
	
	public void collisionEvent(){
		Particle pc=new Particle(Sprite.basicParticle,x,y,0.9,15,15);
		list.remove(this.id);
	}
	
	public static void renderAll(Screen screen){
		for(Double key:Bullet.list.keySet()){
			Bullet b=Bullet.list.get(key);
			b.render(screen);
		}
	}
	
	public static void updateAll(){
		for(Double key:Bullet.list.keySet()){
			Bullet b=Bullet.list.get(key);
			b.update();
		}
	}
	
	private void lifeCounter(){
		if(lifeTime--<0){
			remove();
		}
	}
	
	public void render(Screen screen){
		//자신의 중앙이 x,y좌표가되며 그좌표를 중심으로 그려진다.그리고 screen의 offset에 영향을 받는다.
		screen.render(sprite, (int)this.x-sprite.SIZE/2-(int)screen.xOffset, (int)this.y-sprite.SIZE/2-(int)screen.yOffset);
	}
	
	public void attack(Mob target){
		Particle pc=new Particle(Sprite.bloodParticle,x,y,0.85,17,10);
		if(target.currentHealth-damage>0){
			target.currentHealth-=damage;
			AudioPlayer.HIT1.play();
		}else{
			target.remove();
		}
		this.remove();
	}
	
	public void update(){
		move(angle);
		lifeCounter();
		for(double key:Player.list.keySet()){
			Player p=Player.list.get(key);
			if(p!=shooter&&Util.hitTestBox(p,this,0)){
				attack(p);
			}
		}
	}
	
	public void remove(){
		super.remove();
		list.remove(this.id);
	}
	
}
