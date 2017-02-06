package com.blockwars.game.entities.bullets;

import com.blockwars.CallbackEvent;
import com.blockwars.game.entities.mobs.Player;
import com.blockwars.graphics.Sprite;
import com.blockwars.utils.Util;

public class ReflectBullet extends Bullet{
	
	public ReflectBullet(Sprite sprite,Player shooter,double angle,double speed) {
		super(sprite,shooter,angle,speed);
	}

	public ReflectBullet(Sprite sprite, double parentId, double id, double angle, double speed) {
		super(sprite, parentId, id, angle, speed);
	}
	
	public ReflectBullet(Sprite sprite,double id,double angle,double speed){
		super(sprite,id,angle,speed);
	}
	
	public void collisionEvent(){
//		Particle pc=new Particle(Sprite.basicParticle,x,y,0.9,15,15);
		int x=(int) (this.x/map.tileSize);
		int y=(int) (this.y/map.tileSize);
		//중점 좌표
		double xx=this.x+Math.cos(Util.Deg_to_Rad(angle))*speed/2;
		double yy=this.y+Math.sin(Util.Deg_to_Rad(angle))*speed/2;
		
		double Tangle=Util.obtainAngle(map.tiles[0][x+y*map.width].x+map.tiles[0][x+y*map.width].SIZE/2,map.tiles[0][x+y*map.width].y+map.tiles[0][x+y*map.width].SIZE/2,xx,yy);
		//-180~180->0~360
		if(Tangle<0){
			Tangle+=360;
		}
		Tangle%=360;
		angle%=360;
		//위또는 아래에서 충돌
		if((90-45<=Tangle&&Tangle<90+45)||(270-45<=Tangle&&Tangle<270+45)){
			angle=360-angle;
		}else{
			angle=180-angle;
		}
	}

}
