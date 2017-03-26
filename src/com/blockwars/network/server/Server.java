package com.blockwars.network.server;

import java.awt.Color;
import java.awt.Container;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.game.entities.mobs.Player;
import com.google.gson.Gson;

class UserIP {
	InetAddress address;
	int port;

	UserIP(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}
}

public class Server extends JFrame{

	Socket socket;
	JSONParser jsonParser = new JSONParser();
	Gson gson = new Gson();

	DatagramSocket serverSocket;
	byte[] buffer = new byte[65507];
	ArrayList<BufferedWriter> in = new ArrayList<BufferedWriter>();
	
	RoomManager rm=new RoomManager();
	
	JTextArea ta=new JTextArea();
	
	ConcurrentHashMap<Double, User> users = new ConcurrentHashMap<Double, User>();
	HashSet<UserIP> clients = new HashSet<UserIP>();
	// MainGameState
	ConcurrentHashMap<Double, Player> players = new ConcurrentHashMap<Double, Player>();
	ConcurrentHashMap<Double, Bullet> bullets = new ConcurrentHashMap<Double, Bullet>();

	interface HowToSend {
		int SEND_TO_SELF = 1;
		int SEND_TO_OTHER = 2;
		int SEND_EXCEPT_SELF = 3;
		int BROADCAST = 4;
	}

	public class GameLoop extends Thread {
		int i = 0;

		public void run() {
//			try {
//				while (true) {
//					JSONObject sendData = new JSONObject();
//					double id = Math.random();
//					sendData.put("protocol", "setItem");
//					sendData.put("itemCode", Item.MAGAZINE);
//					sendData.put("id", id);
//					sendData.put("x", Util.randomInt(0, 10));
//					sendData.put("y", Util.randomInt(0, 10));
//					Object[] objs=new Object[] { 1 };
//					sendData.put("objs", jsonParser.parse(gson.toJson(objs)));
//					broadcast(sendData);
//					Thread.sleep(5000);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
	}
	public void once(){
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane=this.getContentPane();
		setContentPane(contentPane);
		contentPane.setBackground(Color.WHITE);
//		contentPane.setLayout(new FlowLayout());
		
		ta.setSize(this.getWidth(), this.getHeight());
		JScrollPane scrollPane=new JScrollPane(ta);
		contentPane.add(scrollPane);
		
		setSize(500,500);
		setResizable(false);
		setVisible(true);
		
		Thread gameLoop = new GameLoop();
		gameLoop.start();
		rm.list.get(1.1);
	}
	public void init() {
		try {
			// 서버소켓을 생성
			serverSocket = new DatagramSocket(5000);
			DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
			int howToSend = 0;
			once();
			printLog("server start port:" + serverSocket.getLocalPort());
			while (true) {
				// 서버 소켓의 receive 메서드를 호출하여
				// 데이터가 들어올때까지 대기.
				serverSocket.receive(receivePacket);
				// 받은 데이타
				JSONObject receiveData = (JSONObject) jsonParser.parse(new String(receivePacket.getData(), 0, receivePacket.getLength()));
				// 처리
				JSONObject sendData = new JSONObject();
				printLog((String)receiveData.get("protocol"));
				switch ((String)receiveData.get("protocol")) {

					// SignUpState
					//signUp: 회원가입
					case "signUp": {
						User user=new User((String) receiveData.get("ID"),(String) receiveData.get("password"));
						sendData.put("protocol", "signUp");
						sendData.put("isExist", false);
						for(double key:users.keySet()){
							User u=users.get(key);
							if(u.equals(user)){
								sendData.put("isExist", true);
								break;
							}
						}
						if(!(boolean)sendData.get("isExist")){
							users.put(user.id, user);
						}
						howToSend = HowToSend.SEND_TO_SELF;
					}break;
					
					// LoginState
					case "login": {
						User user=new User((String) receiveData.get("ID"),(String) receiveData.get("password"));
						sendData.put("protocol", "login");
						sendData.put("isExist", false);
						for(double key:users.keySet()){
							User u=users.get(key);
							if(u.equals(user)){
								u.online=true;
								sendData.put("isExist", true);
								sendData.put("user", jsonParser.parse(gson.toJson(user)));
								break;
							}
						}
						if((boolean) sendData.get("isExist")){
							clients.add(new UserIP(receivePacket.getAddress(), receivePacket.getPort()));
						}
						howToSend = HowToSend.SEND_TO_SELF;
					}break;
					
					// LobbyState
					
					case "initRoom":{
						sendData.put("protocol", "initRoom");
						sendData.put("roomId", (double)receiveData.get("roomId"));
						JSONArray userList = new JSONArray();
						for (double key : rm.getRoom((double)receiveData.get("roomId")).list.keySet()) {
							User user = rm.getRoom((double)receiveData.get("roomId")).list.get(key);
							JSONObject userData = (JSONObject) jsonParser.parse(gson.toJson(user));
							userList.add(userData);
						}
						sendData.put("userList", userList);
						howToSend = HowToSend.SEND_TO_SELF;
					}break;
					
					case "enterRoom":{
						JSONObject userData=(JSONObject)receiveData.get("user");
						rm.getRoom((double)receiveData.get("roomId")).addUser(gson.fromJson(userData.toString(), User.class));
						
						sendData=receiveData;
						howToSend = HowToSend.BROADCAST;
					}break;
					
					case "exitRoom":{
						rm.getRoom((double)receiveData.get("roomId")).removeUser((double)receiveData.get("id"));
						
						sendData=receiveData;
						
						howToSend = HowToSend.BROADCAST;
					}break;
					
					case "initRoomManager":{
						
						sendData.put("protocol", "initRoomManager");
						JSONArray roomList = new JSONArray();
						for (double key : rm.list.keySet()) {
							Room room = rm.list.get(key);
							JSONObject roomData = (JSONObject) jsonParser.parse(gson.toJson(room));
							roomList.add(roomData);
						}
						sendData.put("roomList", roomList);
						howToSend = HowToSend.SEND_TO_SELF;
					}break;
					
					case "addRoom":{
						JSONObject roomData = (JSONObject) receiveData.get("addedRoom");
						
						Room r = gson.fromJson(roomData.toString(), Room.class);
						rm.addRoom(r);
						
						sendData.put("protocol", "addRoom");
						sendData.put("addedRoom", roomData);
						howToSend = HowToSend.BROADCAST;
					}break;
					
					case "removeRoom":{
						rm.removeRoom((double)receiveData.get("id"));
						
						sendData=receiveData;
	
						howToSend = HowToSend.BROADCAST;
					}break;
	
					// MainGameState
						
					// send
					case "init": {
						sendData.put("protocol", "init");
						JSONArray initPack = new JSONArray();
						for (double key : players.keySet()) {
							Player p = players.get(key);
							JSONObject playerData = (JSONObject) jsonParser.parse(gson.toJson(p));
							initPack.add(playerData);
						}
						sendData.put("initPack", initPack);
						
						howToSend = HowToSend.SEND_TO_SELF;
					}break;
	
					// sendExceptSelf
					case "enter": {
						JSONObject playerData = (JSONObject) receiveData.get("enteredPlayer");
	
						Player p = gson.fromJson(playerData.toString(), Player.class);
						players.put(p.id, p);
						clients.add(new UserIP(receivePacket.getAddress(), receivePacket.getPort()));
	
						sendData.put("protocol", "enter");
						sendData.put("enteredPlayer", playerData);
	
						howToSend = HowToSend.SEND_EXCEPT_SELF;
						printLog("enter:" + p.id);
					}break;
	
					case "playerMove": {
						// players.get(receiveData.get("id")).x=(double)
						// receiveData.get("x");
						// players.get(receiveData.get("id")).y=(double)
						// receiveData.get("y");
						sendData = receiveData;
	
						howToSend = HowToSend.SEND_EXCEPT_SELF;
					}break;
	
					case "playerHealth": {
						sendData = receiveData;
	
						howToSend = HowToSend.SEND_EXCEPT_SELF;
					}break;
	
					case "setBlock": {
						sendData = receiveData;
	
						howToSend = HowToSend.SEND_EXCEPT_SELF;
					}break;
	
					case "removeBlock": {
						sendData = receiveData;
	
						howToSend = HowToSend.SEND_EXCEPT_SELF;
					}break;
	
					case "attack": {
						sendData = receiveData;
						
						howToSend = HowToSend.SEND_EXCEPT_SELF;
					}break;
	
					case "exit": {
						clients.remove(receiveData.get("id"));
						players.remove(receiveData.get("id"));
						sendData = receiveData;
	
						howToSend = HowToSend.SEND_EXCEPT_SELF;
						printLog("exit:" + receiveData.get("id"));
					}break;
					
					default:{
						printLog("unknownProtocol:server");
					}break;

				// broadcast
				}
				if(sendData.get("protocol")==null){
					printLog(sendData);
				}
				// 끝
				switch (howToSend) {
				
					case HowToSend.SEND_TO_SELF: {
						send(sendData, receivePacket.getAddress(), receivePacket.getPort());
					}break;
	
					case HowToSend.SEND_TO_OTHER: {
	
					}break;
	
					case HowToSend.SEND_EXCEPT_SELF: {
						sendExceptSelf(sendData, receivePacket.getAddress(), receivePacket.getPort());
					}break;
	
					case HowToSend.BROADCAST: {
						broadcast(sendData);
					}break;
					
					default:{
						printLog("error");
					}break;
				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void send(JSONObject sendData, InetAddress address, int port) {
		try {
			DatagramPacket sendPacket = new DatagramPacket(sendData.toString().getBytes(), 0,
					sendData.toString().getBytes().length, address, port);
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void broadcast(JSONObject sendData) {
		try {
			for (UserIP ip : clients) {
				DatagramPacket sendPacket = new DatagramPacket(sendData.toString().getBytes(), 0,
						sendData.toString().getBytes().length, ip.address, ip.port);
				serverSocket.send(sendPacket);
				printLog(ip.port);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendExceptSelf(JSONObject sendData, InetAddress address, int port) {
		try {
			for (UserIP ip : clients) {

				if (!(ip.address == address && ip.port == port)) {
					DatagramPacket sendPacket = new DatagramPacket(sendData.toString().getBytes(), 0,
							sendData.toString().getBytes().length, ip.address, ip.port);
					serverSocket.send(sendPacket);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void printLog(Object content){
		ta.append(content.toString()+"\n");
		System.out.println(content);
		ta.setCaretPosition(ta.getDocument().getLength());
	}
	
	private void restart(){
		
	}

	static Server g;
	public static void main(String args[]) {
		g= new Server();
		g.init();
	}

}