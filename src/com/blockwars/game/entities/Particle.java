package com.blockwars.game.entities;

import java.util.Random;
import java.util.concurrent.*;

import com.blockwars.graphics.Screen;
import com.blockwars.graphics.Sprite;
import com.blockwars.state.MainGameState;

public class Particle extends Entity{
	
	public static CopyOnWriteArrayList<Particle> list=new CopyOnWriteArrayList<Particle>();
	private int life;
	private double friction=1;
	
	Random random=new Random();
	protected double xa,ya;
	public Particle(Sprite sprite,double x,double y,double friction,int life) {
		super(sprite);
		this.x=x;
		this.y=y;
		this.friction=friction;
		this.life=(int)(Math.random()*20-10)+life;
		list.add(this);
		
		this.xa=random.nextGaussian();
		this.ya=random.nextGaussian();
	}
	
	public Particle(Sprite sprite,double x,double y,double friction,int life,int amount) {
		this(sprite,x,y,friction,life);
		for(int i=0;i<amount-1;i++){
			new Particle(sprite,x,y,friction,life);
		}
	}
	
	public void move(double x,double y){
		if(collision(x,y)){
			this.xa*=-0.5;
			this.ya*=-0.5;
		}
		this.x+=xa;
		this.y+=ya;
	}
	
	public boolean collision(double xa,double ya){
		boolean solid=false;
		int x=(int) (xa/MainGameState.map.tileSize);
		int y=(int) (ya/MainGameState.map.tileSize);
		if(x<0||y<0||x>MainGameState.map.width||y>MainGameState.map.height){
			return solid;
		}
		
		for(int z=0;z<MainGameState.map.depth;z++){
			if(MainGameState.map.tiles[z][x+y*MainGameState.map.width]!=null&&MainGameState.map.tiles[z][x+y*MainGameState.map.width].solid()){
				solid=true;
			}
		}
		
		return solid;
	}
	
	public void update(){
		move(x+xa,y+ya);
		if(life--<=0){
			list.remove(this);
		}
		this.xa*=friction;
		this.ya*=friction;
	}
	
	public void render(Screen screen){
		//자신의 중앙이 x,y좌표가되며 그좌표를 중심으로 그려진다.그리고 screen의 offset에 영향을 받는다.
		screen.render(sprite, (int)this.x-sprite.SIZE/2-(int)screen.xOffset, (int)this.y-sprite.SIZE/2-(int)screen.yOffset);
	}
	
	public static void renderAll(Screen screen){
		for(Particle pc:Particle.list){
			pc.render(screen);
		}
	}
	
	public static void updateAll(){
		for(Particle pc:Particle.list){
			pc.update();
		}
	}
	

}
