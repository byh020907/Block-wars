package com.blockwars.state;

import java.awt.Graphics2D;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.blockwars.CallbackEvent;
import com.blockwars.UI.UI;
import com.blockwars.UI.UI_Button;
import com.blockwars.game.Resource;
import com.blockwars.graphics.Screen;
import com.blockwars.network.Network;
import com.blockwars.network.server.Room;
import com.blockwars.network.server.RoomManager;
import com.blockwars.network.server.User;
import com.google.gson.Gson;

public class LobbyState extends GameState{
	
	JSONParser jsonParser=Network.jsonParser;
	Gson gson=Network.gson;
	
	public DatagramSocket socket=Network.socket;
	public DatagramPacket receivePacket=Network.receivePacket;
	public InetAddress ia=Network.ia;
	public int port=Network.port;
	
	public RoomManager rm=new RoomManager();
	
	public LobbyState(GameStateManager gsm){
		this.gsm=gsm;
	}

	@Override
	public void init() {
		UI_Button b1=new UI_Button(Resource.startImg,0,0,100,100);
		b1.clickEvent=new CallbackEvent(){
			@Override
			public void callbackMethod() {
				try {
					JSONObject data1=new JSONObject();
					data1.put("protocol", "addRoom");
					Room room=new Room();
					data1.put("addedRoom", jsonParser.parse(gson.toJson(room)));
					Network.send(data1, Network.ia, Network.port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		UI_Button b2=new UI_Button(Resource.startImg,100,0,100,100);
		b2.clickEvent=new CallbackEvent(){
			@Override
			public void callbackMethod() {
				JSONObject data1=new JSONObject();
				data1.put("protocol", "removeRoom");
				data1.put("id", 0.2);
				Network.send(data1, Network.ia, Network.port);
				for(double key:rm.getRoom(1.1).list.keySet()){
					User user=rm.getRoom(1.1).list.get(key);
					System.out.println(user.ID);
				}
			}
		};
		
		network();
	}
	
	@Override
	protected void network() {
		try {
			
			Network.init();
			receiveLoop=new ReceiveLoop();
			receiveLoop.start();
			
			for(int i=0;i<2;i++){
				JSONObject data3=new JSONObject();
				data3.put("protocol", "initRoomManager");
				Network.send(data3, Network.ia, Network.port);
				Thread.sleep(1);
			}
			
			//로비에 입장 (id=1.1)
			{
				JSONObject data1=new JSONObject();
				data1.put("protocol", "initRoom");
				data1.put("roomId", 1.1);
				Network.send(data1, Network.ia, Network.port);
				Thread.sleep(1);
				
				JSONObject data2=new JSONObject();
				data2.put("protocol", "enterRoom");
				data2.put("roomId", 1.1);
				data2.put("user", jsonParser.parse(gson.toJson(LoginState.user)));
				Network.send(data2, Network.ia, Network.port);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reset() {
		receiveLoop.stop();
		receiveLoop=null;
		UI.list=new ConcurrentHashMap<Double,UI>();
	}
	boolean flag=false;
	@Override
	public void update() {
		UI.updateAll();
		if(flag){
			gsm.setState(GameStateManager.MAINGAME_STATE);
		}
	}

	@Override
	public void render(Screen screen) {
		
	}

	@Override
	public void draw(Graphics2D g2D) {
		try {
			UI.renderAll(g2D);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int i=0;
		for(double key:rm.list.keySet()){
			Room room=rm.getRoom(key);
			g2D.drawImage(Resource.pauseImg,300,i*100,200,100,null);
			i++;
		}
	}

	@Override
	public void sendData() {
		
	}

	@Override
	public void receiveData() {
		try {
			socket.receive(receivePacket);
			JSONObject receiveData=(JSONObject) jsonParser.parse(new String(receivePacket.getData(), 0, receivePacket.getLength()));
			//처리
			switch((String)receiveData.get("protocol")){
			
				case "initRoom":{
					JSONArray userList = (JSONArray)receiveData.get("userList");
					for(Object key:userList.toArray()){
						JSONObject ob=(JSONObject) key;
						
						User user=gson.fromJson(ob.toString(), User.class);
						if(rm.getRoom((double)receiveData.get("roomId")).getUser(user.id)==null){
							rm.getRoom((double)receiveData.get("roomId")).addUser(user);
						}
					}
				}break;
			
				case "enterRoom":{
					JSONObject userData=(JSONObject)receiveData.get("user");
					rm.getRoom((double)receiveData.get("roomId")).addUser(gson.fromJson(userData.toString(), User.class));
				}break;
				
				case "initRoomManager":{
					JSONArray roomList=(JSONArray)receiveData.get("roomList");
					for(Object key:roomList.toArray()){
						JSONObject ob=(JSONObject) key;
						
						Room room=gson.fromJson(ob.toString(), Room.class);
						
						if(rm.getRoom(room.id)==null){
							rm.addRoom(room);
						}
					}
				}break;
				
				case "addRoom":{
					JSONObject roomData=(JSONObject)receiveData.get("addedRoom");
					
					Room room=gson.fromJson(roomData.toString(), Room.class);
					
					if(rm.getRoom(room.id)==null){
						rm.addRoom(room);
					}
				}break;
				
				case "removeRoom":{
					rm.removeRoom((double)receiveData.get("id"));
				}break;
				
				default:{
					System.out.println("unknownProtocol:"+receiveData.get("protocol"));
				}break;
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
