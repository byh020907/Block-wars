package com.blockwars.UI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.concurrent.ConcurrentHashMap;

public class UI {
	
	private double id=Math.random();
	public Image image;
	public int x;
	public int y;
	public int width;
	public int height;
	
	public static ConcurrentHashMap<Double,UI> list=new ConcurrentHashMap<Double,UI>();
	public UI(Image image,int x,int y,int width,int height){
		this.image=image;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		list.put(this.id, this);
	}
	
	public static void renderAll(Graphics2D g2D){
		try {
			for(double key:UI.list.keySet()){
				UI ui=UI.list.get(key);
				ui.render(g2D);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateAll(){
		try {
			for(double key:UI.list.keySet()){
				UI ui=(UI.list.get(key));
				ui.update();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDepth(double depth){
		list.remove(this.id);
		list.put(depth, this);
	}
	
	public void render(Graphics2D g2D){
		AffineTransform old = g2D.getTransform();
		g2D.drawImage(image,x,y,width,height,null);
		g2D.setTransform(old);
	}
	
	public void update(){
		
	}
}
