package com.blockwars.game.entities.items;

import java.util.concurrent.ConcurrentHashMap;

import com.blockwars.game.entities.Entity;
import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.game.entities.mobs.Player;
import com.blockwars.graphics.Screen;
import com.blockwars.graphics.Sprite;
import com.blockwars.utils.Util;

public class Item extends Entity{
	
	//����ڵ�� 0�̾ƴ� 1���ͽ���->���������� ����
	public static final int HP_POTION=1;
	public static final int MAGAZINE=2;
	
	public int itemCode=0;
	transient int lifeTime=10000;
	protected Object[] objs;
	
	public static ConcurrentHashMap<Double,Item> list=new ConcurrentHashMap<Double,Item>();
	
	public Item(Sprite sprite,int x,int y) {
		super(sprite);
		this.x=map.tileSize*x+map.tileSize/2;
		this.y=map.tileSize*y+map.tileSize/2;
		list.put(this.id, this);
	}
	
	public Item(Sprite sprite,double id,int x,int y) {
		super(sprite);
		this.id=id;
		this.x=map.tileSize*x+map.tileSize/2;
		this.y=map.tileSize*y+map.tileSize/2;
		list.put(this.id, this);
	}
	
	protected void setItemCode(int i){
		this.itemCode=i;
	}
	
	public void getEvent(Player player){
		remove();
	}
	
	public static void renderAll(Screen screen){
		for(Double key:Item.list.keySet()){
			Item i=Item.list.get(key);
			i.render(screen);
		}
	}
	
	public static void updateAll(){
		for(Double key:Item.list.keySet()){
			Item i=Item.list.get(key);
			i.update();
		}
	}
	
	private void lifeCounter(){
		if(lifeTime--<0){
			remove();
		}
	}
	
	public void render(Screen screen){
		//�ڽ��� �߾��� x,y��ǥ���Ǹ� ����ǥ�� �߽����� �׷�����.�׸��� screen�� offset�� ������ �޴´�.
		screen.render(sprite, (int)this.x-sprite.SIZE/2-(int)screen.xOffset, (int)this.y-sprite.SIZE/2-(int)screen.yOffset);
	}
	
	public void update(){
		lifeCounter();
		for(double key:Player.list.keySet()){
			Player p=Player.list.get(key);
			if(Util.hitTestBox(p,this,0)){
				getEvent(p);
			}
		}
	}
	
	public void remove(){
		super.remove();
		list.remove(this.id);
	}

}
