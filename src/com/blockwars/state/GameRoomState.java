package com.blockwars.state;

import java.awt.Graphics2D;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.blockwars.CallbackEvent;
import com.blockwars.UI.UI;
import com.blockwars.UI.UI_Button;
import com.blockwars.UI.UI_List;
import com.blockwars.game.Resource;
import com.blockwars.graphics.Screen;
import com.blockwars.network.Network;
import com.blockwars.network.server.Room;
import com.blockwars.network.server.RoomManager;
import com.blockwars.network.server.User;

public class GameRoomState extends GameState{
	
	public RoomManager rm=LobbyState.rm;
	
	public GameRoomState(GameStateManager gsm){
		this.gsm=gsm;
	}
	@Override
	public void init() {
		
		UI_List list=new UI_List(Resource.blankImg,300,0,200,400);
		list.setDepth(-0.001);
		
		UI_Button b1=new UI_Button(Resource.startImg,0,0,100,100);
		b1.clickEvent=new CallbackEvent(){
			@Override
			public void callbackMethod() {
				for(double key:rm.getRoom(LobbyState.currentRoomId).list.keySet()){
					User user=rm.getRoom(LobbyState.currentRoomId).list.get(key);
					System.out.println(user.ID);
				}
			}
		};
		
		UI_Button b2=new UI_Button(Resource.startImg,b1.x+100,0,100,100);
		b2.clickEvent=new CallbackEvent(){
			@Override
			public void callbackMethod() {
				list.addButton(null);
			}
		};
		
		UI_Button b3=new UI_Button(Resource.startImg,b2.x+100,0,100,100);
		b3.clickEvent=new CallbackEvent(){
			@Override
			public void callbackMethod() {
				list.removeButton(0);
			}
		};
		
		int startX=100;
		int startY=100;
		int width=100;
		int height=100;
		for(int y=0;y<2;y++){
			for(int x=0;x<2;x++){
				UI_Button b=new UI_Button(Resource.blankImg,startX+x*width,startY+y*height,width,height);
			}
		}
		
		network();
		
	}

	@Override
	protected void network() {
		try {
			//게임방에 입장 (id!=1.1)
			{
				JSONObject data1=new JSONObject();
				data1.put("protocol", "initRoom");
				data1.put("roomId", LobbyState.currentRoomId);
				System.out.println(LobbyState.currentRoomId);
				Network.send(data1, Network.ia, Network.port);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reset() {
		UI.list=new ConcurrentHashMap<Double,UI>();
	}

	@Override
	public void update() {
		UI.updateAll();
	}

	@Override
	public void render(Screen screen) {
		
	}

	@Override
	public void draw(Graphics2D g2D) {
		UI.renderAll(g2D);
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
			System.out.println(receiveData.get("protocol"));
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
					User user=gson.fromJson(userData.toString(), User.class);
					rm.getRoom((double)receiveData.get("roomId")).addUser(user);
					System.out.print(user.ID);
				}break;
				
				case "exitRoom":{
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
