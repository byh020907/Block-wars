package com.blockwars.game.entities;

import com.blockwars.game.Game;
import com.blockwars.graphics.Sprite;
import com.blockwars.state.MainGameState;

public class MoveEntity extends Entity{
	
	public double speed=2;
	public double px,py;
	
	public MoveEntity(Sprite sprite,double speed){
		super(sprite);
		this.speed=speed;
	}
	
	public void move(double angle){
		double rad=angle*Math.PI/180;
		if(!collision(this.x+Math.cos(rad)*speed,this.y+Math.sin(rad)*speed)){
			px=this.x;
			py=this.y;
 			this.x+=Math.cos(rad)*speed;
			this.y+=Math.sin(rad)*speed;
		}else{
			collisionEvent();
		}
	}
	
	public void collisionEvent(){
		Particle pc=new Particle(Sprite.basicParticle,x,y,0.9,5,15);
	}
	
	public boolean collision(double xa,double ya){
		boolean solid=false;
		int x=(int) (xa/map.tileSize);
		int y=(int) (ya/map.tileSize);
		if(x<0||y<0||x>=map.width||y>=map.height){
			return solid;
		}
		
		for(int z=0;z<map.depth;z++){
			if(map.tiles[z][x+y*map.width]!=null){
				map.tiles[z][x+y*map.width].collisionEvent(this);
				
				if(map.tiles[z][x+y*map.width].solid()){
					solid=true;
				}
			}
		}
		
		return solid;
	}
	
}
