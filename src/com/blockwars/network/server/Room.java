package com.blockwars.network.server;

import java.util.concurrent.ConcurrentHashMap;

public class Room {
	
	public boolean isEmpty=false;
	
	public double id=Math.random();
	
	public ConcurrentHashMap<Double,User> list=new ConcurrentHashMap<Double,User>();
	
	public Room(){
		
	}
	
	public void addUser(User user){
		list.put(user.id, user);
		isEmpty=false;
		System.out.println("addUser"+user.ID);
	}
	
	public User getUser(double id){
		return list.get(id);
	}
	
	public void removeUser(double id){
		list.remove(id);
		if(list.size()==0){
			isEmpty=true;
		}
	}


}
