package com.blockwars.game.weapon;

import com.blockwars.game.entities.mobs.Player;

public class Weapon {
	
	public static Shotgun SHOTGUN=new Shotgun(null,100,100,500,5);
	public static MachineGun MACHINEGUN=new MachineGun(null,1000,1000,50,12);
	public static RocketLancher ROCKETLANCHER=new RocketLancher(null,10,10,1000,3);
	
	public Player owner;
	public double damage;
	public double knockBack;
	protected Weapon(Player owner){
		this.owner=owner;
	}
	
	public void attack(){
		
	}
	
	public void attack(double angle){
		
	}
}
