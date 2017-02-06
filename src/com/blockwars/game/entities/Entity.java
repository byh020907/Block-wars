package com.blockwars.game.entities;

import com.blockwars.game.tiles.Map;
import com.blockwars.graphics.Sprite;
import com.blockwars.state.MainGameState;;

public class Entity {
	
	transient public Map map=MainGameState.map;
	public double id=Math.random();
	public double x;
	public double y;
	public int width;
	public int height;
	transient private boolean removed=false;
	
	transient public Sprite sprite;
	
	public Entity(Sprite sprite){
		this.sprite=sprite;
		this.width=this.height=sprite.SIZE;
	}
	
	public void remove(){
		this.removed=true;
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
//	@Override
//	public void finalize(){
//		System.out.println(this.toString());
//	}
}
