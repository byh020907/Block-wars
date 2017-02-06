package com.blockwars.game.entities.mobs;

import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;

import com.blockwars.CallbackEvent;
import com.blockwars.game.Game;
import com.blockwars.game.weapon.Weapon;
import com.blockwars.graphics.Screen;
import com.blockwars.graphics.Sprite;
import com.blockwars.network.Network;
import com.blockwars.state.MainGameState;
import com.blockwars.utils.Util;

public class Player extends Mob{
	
	public static final int GRASS=1;
	public static final int STONE=2;
	public static final int DIRT=3;
	public static final int GRASS_SIDE=4;
	public static final int WOOD_PLANK=5;
	
	transient protected Weapon[] weapons=new Weapon[3];
	transient protected int currentSelectNum;
	public int playerType;
	public boolean dummy=false;
	
	protected boolean flag=true;
	
	public static ConcurrentHashMap<Double,Player> list=new ConcurrentHashMap<Double,Player>();
	public Player(Sprite sprite,double health,double damage,double speed){
		super(sprite,health,damage,speed);
		list.put(this.id, this);
	}
	
	public void attack(double angle){
		if(weapons[currentSelectNum]!=null){
			weapons[currentSelectNum].attack(angle);
		}
	}
	
	public void setWeapon(Weapon weapon,int index){
		weapons[index]=weapon;
		weapons[index].owner=this;
	}
	
	public void changeWeapon(int index){
		currentSelectNum=index;
	}
	
	public Weapon getWeapon(){
		return weapons[currentSelectNum];
	}
	
	public void specialSkill(){
		
	}
	
	public void update(){
		
	}
	
	public static void updateAll(){
		for(Double key:Player.list.keySet()){
			Player p=Player.list.get(key);
			p.update();
		}
	}
	
	public static void renderAll(Screen screen){
		for(Double key:Player.list.keySet()){
			Player p=Player.list.get(key);
			p.render(screen);
		}
	}
	
	public void remove(){
		do{
			currentHealth=maxHealth;
			x=Math.random()*700;
			y=Math.random()*700;
		}while(MainGameState.map.tiles[0][(int)(x/MainGameState.width)+(int)(y/MainGameState.height)*MainGameState.map.width].solid());
	}
}
