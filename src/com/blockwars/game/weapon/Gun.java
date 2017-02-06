package com.blockwars.game.weapon;

import com.blockwars.CallbackEvent;
import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.game.entities.mobs.Player;
import com.blockwars.graphics.Sprite;
import com.blockwars.utils.Util;

public class Gun extends Weapon{
	//ÃÑ ¿ë·®
	public int maxCapacity;
	public int currentCapacity;
	//ÅºÃ¢ ¿ë·®
	public int magazineCapacity=100;
	public int attackSpeed=100;
	public double bulletSpeed=10;
	public Gun(Player owner,int maxCapacity,int magazineCapacity,int attackSpeed,double bulletSpeed){
		super(owner);
		this.maxCapacity=this.currentCapacity=maxCapacity;
		this.magazineCapacity=magazineCapacity;
		this.attackSpeed=attackSpeed;
		this.bulletSpeed=bulletSpeed;
	}
	protected boolean flag=true;
	
	public void attack(double angle){
		
		if(currentCapacity>0&&flag){
			flag=false;
			
			currentCapacity-=1;
			attackProcess(angle);
			
			//delay
			Util.setTimeout(attackSpeed,new CallbackEvent(){
				public void callbackMethod(){
					flag=true;
				}
			});
		}
		
	}
	
	public void attackProcess(double angle){
		currentCapacity--;
		
		if(!owner.collision(owner.x+Math.cos(Util.Deg_to_Rad(180+angle))*knockBack, owner.y+Math.sin(Util.Deg_to_Rad(180+angle))*knockBack)){
			Util.move(owner,180+angle,knockBack);
		}
	}
}
