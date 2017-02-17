package com.blockwars.utils;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.blockwars.CallbackEvent;
import com.blockwars.CallbackEvent_Argument;
import com.blockwars.UI.UI;
import com.blockwars.game.entities.Entity;
import com.blockwars.game.tiles.Tile;
import com.blockwars.input.Mouse;

public class Util {
	
	public static Image loadImage(ImageIcon icon){
		return icon.getImage();
	}
	
	public static void setTimeout(int time,CallbackEvent callbackEvent){
		Thread t=new Thread(){
			public void run(){
				try {
					Thread.sleep(time);
					callbackEvent.callbackMethod();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	
	public static void setTimeout(int time,CallbackEvent_Argument callbackEventClass){
		Thread t=new Thread(){
			public void run(){
				try {
					Thread.sleep(time);
					callbackEventClass.callbackMethod();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	
	public static void move(Entity entity,double angle,double speed){
		double rad=angle*Math.PI/180;
		entity.x+=Math.cos(rad)*speed;
		entity.y+=Math.sin(rad)*speed;
	}
	
	public static int randomInt(int start,int end){
		if(start>end){
			int temp=start;
			start=end;
			end=temp;
		}
		
		return (int)(Math.random()*((end-start)+1))+start;
	}
	
	public static int toInt(Object obj){
		
		if(obj instanceof Integer){
			return ((Integer) obj).intValue();
		}
		
		if(obj instanceof Long){
			return ((Long)obj).intValue();
		}
		
		if(obj instanceof Double){
			return ((Double) obj).intValue();
		}
		return -99999;
	}
	
	//hittest
	
	public static boolean hitTestPoint(Mouse mouse,UI ui){
		if(ui.x<mouse.getX()&&mouse.getX()<ui.x+ui.width&&ui.y<mouse.getY()&&mouse.getY()<ui.y+ui.height){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean hitTestPoint(double x,double y,double width,double height,Entity e){
		if(x<e.x&&e.x<x+width&&y<e.y&&e.y<y+height){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean hitTestPoint(double x,double y,double width,double height,Point p){
		if(x<p.x&&p.x<x+width&&y<p.y&&p.y<y+height){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean hitTestCircle(Entity entity1,Entity entity2,double distance){
		if(entity1!=entity2&&Math.sqrt(Math.pow(entity1.x-entity2.x,2)+Math.pow(entity1.y-entity2.y,2))<distance){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean hitTestBox(Entity entity1,Entity entity2,double outline){
		if(entity1.x-entity1.width/2+outline<entity2.x+entity2.width/2-outline&&entity2.x-entity2.width/2+outline<entity1.x+entity1.width/2-outline&&
			entity1.y-entity1.height/2+outline<entity2.y+entity2.height/2-outline&&entity2.y-entity2.height/2+outline<entity1.y+entity1.height/2-outline){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean hitTestBox(Entity entity1,double x,double y,double width,double height,double outline){
		if(entity1.x-entity1.width/2+outline<x+width/2-outline&&x-width/2+outline<entity1.x+entity1.width/2-outline&&
			entity1.y-entity1.height/2+outline<y+height/2-outline&&y-height/2+outline<entity1.y+entity1.height/2-outline){
			return true;
		}else{
			return false;
		}
	}
	
	//angle
	
	public static double Rad_to_Deg(double angle){
		return angle*180/Math.PI;
	}
	
	public static double Deg_to_Rad(double angle){
		return angle*Math.PI/180;
	}
	
	public static double obtainDistance(Entity entity1,Entity entity2){
		return Math.sqrt(Math.pow(entity1.x-entity2.x,2)+Math.pow(entity1.y-entity2.y,2));
	}
	
	public static double obtainAngle(Entity entity1,Entity entity2){
		return Math.atan2(entity2.y-entity1.y,entity2.x-entity1.x)*180/Math.PI;
	}
	
	public static double obtainAngle(double x1,double y1,double x2,double y2){
		return Math.atan2(y2-y1,x2-x1)*180/Math.PI;
	}
}