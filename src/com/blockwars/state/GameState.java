package com.blockwars.state;

import java.awt.Graphics2D;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.json.simple.parser.JSONParser;

import com.blockwars.graphics.Screen;
import com.blockwars.network.Network;
import com.google.gson.Gson;

public abstract class GameState {

	protected GameStateManager gsm;
	
	JSONParser jsonParser=Network.jsonParser;
	Gson gson=Network.gson;
	
	public DatagramSocket socket=Network.socket;
	public DatagramPacket receivePacket=Network.receivePacket;
	public InetAddress ia=Network.ia;
	public int port=Network.port;
	
	public abstract void init();
	protected abstract void network();
	public abstract void reset();
	public abstract void update();
	public abstract void render(Screen screen);
	public abstract void draw(Graphics2D g2D);
	public abstract void sendData();
	public abstract void receiveData();
}
