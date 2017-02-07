package com.blockwars.network.server;

import java.util.concurrent.ConcurrentHashMap;

public class Room {
	
	public double id=Math.random();
	
	public ConcurrentHashMap<Double,User> list=new ConcurrentHashMap<Double,User>();
	
	public Room(){
		
	}
	
	public void addUser(User user){
		list.put(user.id, user);
	}
	
	public User getUser(double id){
		return list.get(id);
	}
	
	public void removeUser(double id){
		list.remove(id);
	}


}
