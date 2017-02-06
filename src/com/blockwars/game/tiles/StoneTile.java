package com.blockwars.game.tiles;

import com.blockwars.graphics.Sprite;

public class StoneTile extends Tile{

	public StoneTile(Sprite sprite, int x, int y, int size) {
		super(sprite, x, y, size);
	}
	
	public StoneTile(Sprite sprite,int size) {
		super(sprite,size);
	}
	
	public boolean solid(){
		return true;
	}
}
