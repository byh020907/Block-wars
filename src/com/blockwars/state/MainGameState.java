package com.blockwars.state;

import java.awt.Graphics2D;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.blockwars.CallbackEvent;
import com.blockwars.UI.UI;
import com.blockwars.UI.UI_Button;
import com.blockwars.UI.UI_FollowMiniMap;
import com.blockwars.UI.UI_TextField;
import com.blockwars.game.Game;
import com.blockwars.game.Resource;
import com.blockwars.game.entities.Particle;
import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.game.entities.bullets.ExplosionBullet;
import com.blockwars.game.entities.bullets.ReflectBullet;
import com.blockwars.game.entities.items.HpPotion;
import com.blockwars.game.entities.items.Item;
import com.blockwars.game.entities.items.Magazine;
import com.blockwars.game.entities.mobs.Player;
import com.blockwars.game.entities.mobs.Player_Dirt;
import com.blockwars.game.entities.mobs.Player_Grass;
import com.blockwars.game.entities.mobs.Player_GrassSide;
import com.blockwars.game.entities.mobs.Player_Stone;
import com.blockwars.game.entities.mobs.Player_WoodPlank;
import com.blockwars.game.tiles.Map;
import com.blockwars.game.tiles.Tile;
import com.blockwars.game.tiles.WebTile;
import com.blockwars.game.weapon.BoundShotgun;
import com.blockwars.game.weapon.Gun;
import com.blockwars.game.weapon.MachineGun;
import com.blockwars.game.weapon.RocketLancher;
import com.blockwars.graphics.Screen;
import com.blockwars.graphics.Sprite;
import com.blockwars.input.Keyboard;
import com.blockwars.input.Mouse;
import com.blockwars.network.Network;
import com.blockwars.utils.Util;
import com.google.gson.Gson;
import com.sun.glass.events.KeyEvent;


public class MainGameState extends GameState{
	
	JSONParser jsonParser=Network.jsonParser;
	Gson gson=Network.gson;
	
	public DatagramSocket socket=Network.socket;
	public DatagramPacket receivePacket=Network.receivePacket;
	public InetAddress ia=Network.ia;
	public int port=Network.port;
	
	public static int width=300;
	public static int height=width/16*9;
	public static int scale=3;
	public static String title="Rain";
	
	private Screen screen;
	
	public static Player user;
	public static Map map;
	
	public MainGameState(GameStateManager gsm){
		this.gsm=gsm;
	}
	UI_TextField tf;
	@Override
	public void init() {
		//이후 이 부분은 플래이어의 정보를 생성자로받아 for문으로 생성
		screen=Game.screen;
		
		map=new Map(new String[]{
				"res/images/textures/map1_0.png",
				"res/images/textures/map1_1.png"
		},16);
		
		int playerType=(int)(Math.random()*5)+1;
//		playerType=2;
		switch(playerType){
			case Player.GRASS:{
				user=new Player_Grass(100,1,1);
			}break;
			
			case Player.STONE:{
				user=new Player_Stone(100,1,1);
			}break;
			
			case Player.DIRT:{
				user=new Player_Dirt(100,1,1);
			}break;
			
			case Player.GRASS_SIDE:{
				user=new Player_GrassSide(100,1,1);
			}break;
			
			case Player.WOOD_PLANK:{
				user=new Player_WoodPlank(100,1,1);
			}break;
			
			default:{
				System.out.println("unknownPlayerType");
			}break;
		}
		user.x=10;
		user.y=10;

		user.setWeapon(new BoundShotgun(user,30,3,500,5),0);
		user.setWeapon(new MachineGun(user,1000,100,50,12),1);
		user.setWeapon(new RocketLancher(user,10,1,1000,3),2);
		
		Item a=new HpPotion(Sprite.TNTSide,10,0,new Object[]{20});
		Item b=new Magazine(Sprite.basicBullet,10,1,new Object[]{1});
		
//		UI_MiniMap miniMap=new UI_MiniMap(Resource.miniMap,this.width*this.scale-150,0,150,150);
		UI_FollowMiniMap miniMap=new UI_FollowMiniMap(Resource.blankImg,this.width*this.scale-150,0,150,150,user);
		UI_Button bb=new UI_Button(Resource.homeImg,0,0,150,150);
		
		bb.clickEvent=new CallbackEvent(){
			public void callbackMethod(){
				JSONObject data=new JSONObject();
				data.put("protocol", "exit");
				data.put("id", user.id);
				Network.send(data,Network.ia,Network.port);
				gsm.setState(GameStateManager.MENU_STATE);
			}
		};
		
		tf=new UI_TextField(Resource.blankImg,200,0,100,30);
		//thread start and frame set
		network();
	}
	
	private void network(){
		try{
			JSONObject data1=new JSONObject();
			data1.put("protocol", "enter");
			data1.put("enteredPlayer", jsonParser.parse(gson.toJson(user)));
			JSONObject data2=new JSONObject();
			data2.put("protocol", "init");
			Network.send(data1, ia, port);
			Thread.sleep(1);
			Network.send(data2, ia, port);
			sendLoop=new SendLoop();
			sendLoop.start();
			receiveLoop=new ReceiveLoop();
			receiveLoop.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		//exit
		if(Keyboard.getKeyState(KeyEvent.VK_ESCAPE)){
			JSONObject data=new JSONObject();
			data.put("protocol", "exit");
			data.put("id", user.id);
			Network.send(data,Network.ia,Network.port);
			System.exit(0);
		}
		
		if(Keyboard.getKeyState(KeyEvent.VK_A)){
			user.move(180);
		}
		if(Keyboard.getKeyState(KeyEvent.VK_W)){
			user.move(270);
		}
		if(Keyboard.getKeyState(KeyEvent.VK_D)){
			user.move(0);
		}
		if(Keyboard.getKeyState(KeyEvent.VK_S)){
			user.move(90);
		}
		if(Keyboard.getKeyState(KeyEvent.VK_SPACE)){
			user.specialSkill();
		}
		if(Keyboard.getKeyState(KeyEvent.VK_1)){
			user.changeWeapon(0);
		}
		if(Keyboard.getKeyState(KeyEvent.VK_2)){
			user.changeWeapon(1);
		}
		if(Keyboard.getKeyState(KeyEvent.VK_3)){
			user.changeWeapon(2);
		}
		screen.xOffset+=(user.x-screen.width/2-screen.xOffset)*0.05;
		screen.yOffset+=(user.y-screen.height/2-screen.yOffset)*0.05;
		Bullet.updateAll();
		Particle.updateAll();
		Item.updateAll();
		UI.updateAll();
		if(Mouse.getButton()==1){
			user.attack(Util.obtainAngle(width*scale/2, height*scale/2, Mouse.getX(), Mouse.getY()));
		}
		tf.content=new StringBuffer((((Gun)(user.getWeapon())).currentCapacity%((Gun)(user.getWeapon())).magazineCapacity)+"/"+(((Gun)(user.getWeapon())).currentCapacity/((Gun)(user.getWeapon())).magazineCapacity)*((Gun)(user.getWeapon())).magazineCapacity);
	}
	
	@Override
	public void render(Screen screen) {
		screen.clear();
		try {
			map.render(screen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Bullet.renderAll(screen);
		Player.renderAll(screen);
		Particle.renderAll(screen);
		Item.renderAll(screen);
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
	public void reset() {
		sendLoop.stop();
		receiveLoop.stop();
		
		if(screen!=null){
			screen.clear();
		}
		user=null;
		Player.list=new ConcurrentHashMap<Double,Player>();
		Bullet.list=new ConcurrentHashMap<Double,Bullet>();
		Particle.list=new CopyOnWriteArrayList<Particle>();
		Item.list=new ConcurrentHashMap<Double,Item>();
		UI.list=new ConcurrentHashMap<Double,UI>();
		map=null;
	}
	int count=1;
	@Override
	public void sendData() {
		try {
			if(count%6==0){
				JSONObject sendData=new JSONObject();
				sendData.put("protocol", "playerMove");
				sendData.put("id", user.id);
				sendData.put("x", user.x);
				sendData.put("y", user.y);
				Network.send(sendData, ia, port);
			}
			
			if(count%60==0){
				JSONObject sendData2=new JSONObject();
				sendData2.put("protocol", "playerHealth");
				sendData2.put("id", user.id);
				sendData2.put("currentHealth", user.currentHealth);
				Network.send(sendData2, ia, port);
			}
			
			count++;
			if(count>60){
				count=1;
			}
			Thread.sleep(1000/60);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receiveData() {
		try {
			socket.receive(receivePacket);
			JSONObject receiveData=(JSONObject) jsonParser.parse(new String(receivePacket.getData(), 0, receivePacket.getLength()));
			//처리
			switch((String)receiveData.get("protocol")){
				
				case "init":{
					JSONArray a=(JSONArray)(receiveData.get("initPack"));
					for(Object key:a.toArray()){
						JSONObject b=(JSONObject) key;
						
						Player player=gson.fromJson(b.toString(), Player.class);
						player.dummy=true;
						switch(player.playerType){
							case Player.GRASS:{
								player.sprite=Sprite.grass;
							}break;
							
							case Player.STONE:{
								player.sprite=Sprite.stone;
							}break;
							
							case Player.DIRT:{
								player.sprite=Sprite.dirt;
							}break;
							
							case Player.GRASS_SIDE:{
								player.sprite=Sprite.grassSide;
							}break;
							
							case Player.WOOD_PLANK:{
								player.sprite=Sprite.woodPlank;
							}break;
							
							case 6:{
								player.sprite=Sprite.halfStoneBlockSide;
							}break;
							
							case 7:{
								player.sprite=Sprite.halfStoneBlockTop;
							}break;
							
							case 8:{
								player.sprite=Sprite.brick;
							}break;
							
							case 9:{
								player.sprite=Sprite.TNTSide;
							}break;
							
							case 10:{
								player.sprite=Sprite.web;
							}break;
							
							default:{
								System.out.println("unknownPlayerType");
							}break;
						}
					
						if(Player.list.get(player.id)==null){
							Player.list.put(player.id,player);
						}
					}
				}break;
				
				case "enter":{
					JSONObject playerData=(JSONObject)receiveData.get("enteredPlayer");
					
					Player player=gson.fromJson(playerData.toString(), Player.class);
					player.dummy=true;
					switch(player.playerType){
						case Player.GRASS:{
							player.sprite=Sprite.grass;
						}break;
						
						case Player.STONE:{
							player.sprite=Sprite.stone;
						}break;
						
						case Player.DIRT:{
							player.sprite=Sprite.dirt;
						}break;
						
						case Player.GRASS_SIDE:{
							player.sprite=Sprite.grassSide;
						}break;
						
						case Player.WOOD_PLANK:{
							player.sprite=Sprite.woodPlank;
						}break;
						
						case 6:{
							player.sprite=Sprite.halfStoneBlockSide;
						}break;
						
						case 7:{
							player.sprite=Sprite.halfStoneBlockTop;
						}break;
						
						case 8:{
							player.sprite=Sprite.brick;
						}break;
						
						case 9:{
							player.sprite=Sprite.TNTSide;
						}break;
						
						case 10:{
							player.sprite=Sprite.web;
						}break;
						
						default:{
							System.out.println("unknownPlayerType");
						}break;
					}
					
					Player p=Player.list.get(player.id);
					if(p==null){
						Player.list.put(player.id, player);
					}
				}break;

				case "playerMove":{
					//ConcurrentHashMap데이터를 받아 데드레커닝  기법으로 처리한다.
					Player p=Player.list.get((double)receiveData.get("id"));
					if(p!=null){
						p.x=(double)receiveData.get("x");
						p.y=(double)receiveData.get("y");
					}
				}break;
				
				case "playerHealth":{
					Player p=Player.list.get((double)receiveData.get("id"));
					if(p!=null){
						p.currentHealth=(double)receiveData.get("currentHealth");
					}
				}break;
				
				case "setBlock":{
					int x=Util.toInt(receiveData.get("x"));
					int y=Util.toInt(receiveData.get("y"));
					int depth=Util.toInt(receiveData.get("depth"));
					int tileNum=Util.toInt(receiveData.get("tileNum"));
					switch(tileNum){
						case Tile.WEB_TILE:{
							map.setTile(new WebTile(Sprite.web,map.tileSize), x, y, depth);
						}break;
						
						default:{
							System.out.println("wrongTileNum");
						}break;
					}
				}break;
				
				case "removeBlock":{
					int x=Util.toInt(receiveData.get("x"));
					int y=Util.toInt(receiveData.get("y"));
					int depth=Util.toInt(receiveData.get("depth"));
					map.removeTile(x, y, depth);
				}break;
				
				case "setItem":{
					int x=Util.toInt(receiveData.get("x"));
					int y=Util.toInt(receiveData.get("y"));
					Object[] objs=gson.fromJson(receiveData.get("objs").toString(), Object[].class);
					int itemCode=Util.toInt(receiveData.get("itemCode"));
					switch(itemCode){
						case Item.HP_POTION:{
							Item i=new HpPotion(Sprite.TNTSide,(double)receiveData.get("id"),x,y,objs);
						}break;
						
						case Item.MAGAZINE:{
							Item i=new Magazine(Sprite.basicBullet,(double)receiveData.get("id"),x,y,objs);
						}break;
						
						default:{
							System.out.println("wrongItemNum");
						}break;
					}
				}break;
				
				case "attack":{
					//ConcurrentHashMap데이터를 받아 데드레커닝  기법으로 처리한다.
					if(Player.list.get(receiveData.get("parentId"))!=null){
						int bulletType=Util.toInt(receiveData.get("bulletType"));
						switch(bulletType){
							case Bullet.NORMAL_BULLET:{
								Bullet b=new Bullet(Sprite.basicBullet,(double)receiveData.get("parentId"),(double)receiveData.get("id"),(double)receiveData.get("angle"),(double)receiveData.get("speed"));
							}break;
							
							case Bullet.EXPLOSION_BULLET:{
								ExplosionBullet b=new ExplosionBullet(Sprite.TNTTop,(double)receiveData.get("parentId"),(double)receiveData.get("id"),(double)receiveData.get("angle"),(double)receiveData.get("speed"));
							}break;
							
							case Bullet.REFLECT_BULLET:{
								ReflectBullet b=new ReflectBullet(Sprite.basicBullet,(double)receiveData.get("parentId"),(double)receiveData.get("id"),(double)receiveData.get("angle"),(double)receiveData.get("speed"));
							}break;
							
							default:{
								System.out.println("attack:unknownBullet");
							}break;
						}
					}
				}break;
				
				case "exit":{
					Player.list.remove((double)receiveData.get("id"));
				}break;
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
