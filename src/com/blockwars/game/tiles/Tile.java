package com.blockwars.game.tiles;

import com.blockwars.game.entities.MoveEntity;
import com.blockwars.game.entities.mobs.Mob;
import com.blockwars.graphics.Screen;
import com.blockwars.graphics.Sprite;

public class Tile{
	
	public Sprite sprite;
	public int x;
	public int y;
	public final int SIZE;
	
	public static final int VOID_TILE=1;
	public static final int GRASS_TILE=0xff00f000;
	public static final int STONE_TILE=0xff5f5f5f;//80+15
	public static final int WATER_TILE=0xff0000ff;
	public static final int LAVA_TILE=0xffff0000;
	public static final int WEB_TILE=0xfff0f0f0;
	public static final int ROSE_TILE=0xfff00000;
	
	public Tile(Sprite sprite,int x,int y,int size) {
		this.sprite=sprite;
		this.x=x;
		this.y=y;
		SIZE=size;
	}
	
	public Tile(Sprite sprite,int size) {
		this.sprite=sprite;
		SIZE=size;
	}
	
	public void render(Screen screen){
		//자신의 왼쪽상단이 x,y좌표가되며 그좌표를 기준으로 그려진다.그리고 screen의 offset에 영향을 받는다.
		screen.render(sprite, (int)this.x-(int)screen.xOffset, (int)this.y-(int)screen.yOffset);
	}
	
	public void collisionEvent(MoveEntity moveEntity){
		
	}
	
	public void collisionEvent(Mob mob){
		
	}
	
	public boolean solid(){
		return false;
	}

}
