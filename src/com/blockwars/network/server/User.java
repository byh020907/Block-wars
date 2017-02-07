package com.blockwars.network.server;

import java.net.InetAddress;
import java.util.Objects;

public class User {
	
	public boolean online=false;
	public InetAddress address;
	public int port;
	
	public String ID;
	public String password;
	
	public double id=Math.random();
	
	public User(String ID,String password){
		this.ID=ID;
		this.password=password;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof User){
			return obj.hashCode()==this.hashCode();
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		//인자로 들어간  내용에따라 값이바뀐다.
		return Objects.hash(ID,password);
	}
}
