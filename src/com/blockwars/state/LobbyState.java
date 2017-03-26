package com.blockwars.state;

import java.awt.Graphics2D;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.blockwars.CallbackEvent;
import com.blockwars.CallbackEvent_Argument;
import com.blockwars.UI.UI;
import com.blockwars.UI.UI_Button;
import com.blockwars.UI.UI_List;
import com.blockwars.game.Resource;
import com.blockwars.graphics.Screen;
import com.blockwars.network.Network;
import com.blockwars.network.server.Room;
import com.blockwars.network.server.RoomManager;
import com.blockwars.network.server.User;

public class LobbyState extends GameState{
	
	public static RoomManager rm=new RoomManager();
	public static double currentRoomId=1.2;
	
	UI_List list;
	UI_List playerList;
	
	public LobbyState(GameStateManager gsm){
		this.gsm=gsm;
	}

	@Override
	public void init() {
		
		list=new UI_List(Resource.blankImg,300,100,200,400);
		list.setDepth(-1);
		
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
				data1.put("protocol", "exitRoom");
				data1.put("roomId", 1.1);
				data1.put("id", LoginState.user.id);
				Network.send(data1, Network.ia, Network.port);
				for(double key:rm.getRoom(1.1).list.keySet()){
					User user=rm.getRoom(1.1).list.get(key);
					System.out.println(user.ID);
				}
			}
		};
		
		playerList=new UI_List(Resource.blankImg,500,100,200,400);
		playerList.setDepth(-1);
		
		
		network();
	}
	
	@Override
	protected void network() {
		try {
			
			Network.init();
			receiveLoop=new ReceiveLoop();
			receiveLoop.start();
			Thread.sleep(1);
			
			JSONObject data3=new JSONObject();
			data3.put("protocol", "initRoomManager");
			Network.send(data3, Network.ia, Network.port);
			Thread.sleep(1);
			
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
			gsm.setState(GameStateManager.GAMEROOM_STATE);
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
	}

	@Override
	public void sendData() {
		
	}

	@Override
	public void receiveData() {
		try {
			socket.receive(receivePacket);
			JSONObject receiveData=(JSONObject) jsonParser.parse(new String(receivePacket.getData(), 0, receivePacket.getLength()));
			System.out.println(receiveData.get("protocol"));
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
						if((double)receiveData.get("roomId")==1.1){
							playerList.addButton(null);
						}
					}
				}break;
			
				case "enterRoom":{
					JSONObject userData=(JSONObject)receiveData.get("user");
					User user=gson.fromJson(userData.toString(), User.class);
					rm.getRoom((double)receiveData.get("roomId")).addUser(user);
					
					if(LoginState.user.equals(user)&&(double)receiveData.get("roomId")!=1.1){
						currentRoomId=(double)receiveData.get("roomId");
						flag=true;
					}
					
					if((double)receiveData.get("roomId")==1.1){
						playerList.addButton(null);
					}
				}break;
				
				case "exitRoom":{
					if((double)receiveData.get("roomId")==1.1){
						playerList.removeButton(0);
					}
					rm.getRoom((double)receiveData.get("roomId")).removeUser((double)receiveData.get("id"));
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
						list.addButton(new CallbackEvent(){
							@Override
							public void callbackMethod() {
								try {
									JSONObject data1=new JSONObject();
									data1.put("protocol", "exitRoom");
									data1.put("roomId", 1.1);
									data1.put("id", LoginState.user.id);
									Network.send(data1, Network.ia, Network.port);
									Thread.sleep(1);
									
									JSONObject data=new JSONObject();
									data.put("protocol", "enterRoom");
									data.put("roomId", room.id);
									data.put("user", jsonParser.parse(gson.toJson(LoginState.user)));
									Network.send(data, ia, port);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
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
