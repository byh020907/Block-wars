package com.blockwars.network.server;

import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {
	public ConcurrentHashMap<Double,Room> list=new ConcurrentHashMap<Double,Room>();
	
	public RoomManager(){
		//lobby
		Room lobby=new Room();
		lobby.id=1.1;
		addRoom(lobby);
	}
	
	public void addRoom(Room room){
		list.put(room.id, room);
	}
	
	public Room getRoom(double id){
		return list.get(id);
	}
	
	public void removeRoom(double id){
		list.remove(id);
	}
}
