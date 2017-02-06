package com.blockwars.game.entities.mobs;

import java.util.concurrent.ConcurrentHashMap;

import com.blockwars.game.entities.MoveEntity;
import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.graphics.Screen;
import com.blockwars.graphics.Sprite;
import com.blockwars.state.MainGameState;

public class Mob extends MoveEntity{
	
	public boolean inWater;
	public boolean inWeb;
	
	public double maxHealth;
	public double currentHealth;
	
	public int HealthbarSize=20;
	
	public double damage;
	public Mob(Sprite sprite,double health,double damage,double speed) {
		super(sprite,speed);
		maxHealth=health;
		currentHealth=health;
		this.damage=damage;
	}
	
	public void attack(Mob target){
		if(target.currentHealth-damage>0){
			target.currentHealth-=damage;
		}else{
			target.remove();
		}
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
	
	public void render(Screen screen){
		//자신의 중앙이 x,y좌표가되며 그좌표를 중심으로 그려진다.그리고 screen의 offset에 영향을 받는다.
		screen.render(sprite, (int)this.x-sprite.SIZE/2-(int)screen.xOffset, (int)this.y-sprite.SIZE/2-(int)screen.yOffset);
		//healthbar
		for(int y=0;y<3;y++){
			int yp=y+(int)this.y-3/2-(int)screen.yOffset-12;
			if(yp<0||yp>=screen.height)continue;
			for(int x=0;x<(int)((currentHealth/maxHealth)*HealthbarSize);x++){
				int xp=x+(int)this.x-(int)((currentHealth/maxHealth)*HealthbarSize/2)-(int)screen.xOffset;
				if(xp<0||xp>=screen.width)continue;
				screen.pixels[xp+yp*screen.width]=0xff0000;
			}
		}
	}
	
	public void update(){
		
	}
	
	
	
	
}
