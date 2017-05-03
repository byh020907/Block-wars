package com.blockwars.state;

import java.awt.Graphics2D;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.blockwars.CallbackAble;
import com.blockwars.CallbackEvent;
import com.blockwars.UI.UI;
import com.blockwars.UI.UI_Background;
import com.blockwars.UI.UI_Button;
import com.blockwars.UI.UI_TextField;
import com.blockwars.game.Game;
import com.blockwars.game.Resource;
import com.blockwars.graphics.Screen;
import com.blockwars.network.Network;
import com.google.gson.Gson;

public class SignUpState extends GameState{
	
	UI_TextField tf1;
	UI_TextField tf2;
	UI_TextField tf3;
	

	public SignUpState(GameStateManager gsm){
		this.gsm=gsm;
	}
	@Override
	public void init() {
		UI_Background background=new UI_Background(Resource.UI_background,-1000,0,2000,800);
		background.setDepth(0.1);
		tf1=new UI_TextField(Resource.blankImg,(Game.width*Game.scale)/2-125,(Game.height*Game.scale)/2-75,250,30);
		tf1.tabAction=new CallbackEvent(){
			@Override
			public void callbackMethod() {
				tf2.setFocus();
			}
		};
		tf1.setDepth(0.2);
		tf2=new UI_TextField(Resource.blankImg,(Game.width*Game.scale)/2-125,(Game.height*Game.scale)/2,250,30);
		tf2.tabAction=new CallbackEvent(){
			@Override
			public void callbackMethod() {
				tf3.setFocus();
			}
		};
		tf2.setDepth(0.21);
		tf3=new UI_TextField(Resource.blankImg,(Game.width*Game.scale)/2-125,(Game.height*Game.scale)/2+75,250,30);
		tf3.tabAction=new CallbackEvent(){
			@Override
			public void callbackMethod() {
				tf1.setFocus();
			}
		};
		tf3.setDepth(0.22);
		UI_Button b1=new UI_Button(Resource.startImg,(Game.width*Game.scale)/2-75,(Game.height*Game.scale)/2+120,150,50);
		b1.setDepth(1.3);
		b1.clickEvent=new CallbackEvent(){
			@Override
			public void callbackMethod(){
				if(tf2.content.toString().equals(tf3.content.toString())){
					JSONObject sendData=new JSONObject();
					sendData.put("protocol", "signUp");
					sendData.put("ID", tf1.content.toString());
					sendData.put("password", tf2.content.toString());
					Network.send(sendData, Network.ia, Network.port);
				}
			}
		};
		
		network();
	}
	
	@Override
	protected void network() {
		Network.init();
	}
	
	@Override
	public void reset() {
		UI.list=new ConcurrentHashMap<Double,UI>();
	}
	boolean flag=false;
	@Override
	public void update() {
		UI.updateAll();
		if(flag){
			gsm.setState(GameStateManager.LOGIN_STATE);
		}
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
	public void render(Screen screen) {
		
	}

	@Override
	public void sendData() {
		
	}

	@Override
	public void receiveData() {
		try {
			socket.receive(receivePacket);
			JSONObject receiveData=(JSONObject) jsonParser.parse(new String(receivePacket.getData(), 0, receivePacket.getLength()));
			//ó��
			System.out.println((String)receiveData.get("protocol"));
			switch((String)receiveData.get("protocol")){
				case "signUp":{
					if(!(boolean)receiveData.get("isExist")){
						flag=true;
						System.out.println("회원가입성공");
					}else{
						System.out.println("이미존재하는 아이디입니다.");
					}
				}break;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
