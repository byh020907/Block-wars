package com.blockwars.UI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.blockwars.CallbackEvent;
import com.blockwars.input.Mouse;
import com.blockwars.utils.Util;

	
public class UI_Button extends UI{
	
	public CallbackEvent clickEvent;
	private boolean entered=false;
	private boolean pressed=false;
	public UI_Button(Image image,int x,int y,int width,int height){
		super(image,x,y,width,height);
	}
	
	public void enterEvent(Mouse mouse){
		if(Util.hitTestPoint(mouse, this)){
			entered=true;
		}
	}
	
	public void exitEvent(Mouse mouse){
		if(entered&&!Util.hitTestPoint(mouse, this)){
			entered=false;
		}
	}
	
	public void clickedEvent(Mouse mouse){
		if(clickEvent!=null&&Util.hitTestPoint(mouse, this)){
			clickEvent.callbackMethod();
		}
	}
	
	public void pressedEvent(Mouse mouse){
		if(Util.hitTestPoint(mouse, this)){
			pressed=true;
		}
	}
	
	public void releasedEvent(Mouse mouse){
		pressed=false;
	}
	
	public void render(Graphics2D g2D){
		AffineTransform old = g2D.getTransform();
		g2D.drawImage(image,x,y,width,height,null);
		if(pressed){
			g2D.setColor(new Color(10,10,10,200));
			g2D.fillRect(x, y, width, height);
		}else if(entered){
			g2D.setColor(new Color(10,10,10,100));
			g2D.fillRect(x, y, width, height);
		}
		g2D.setTransform(old);
	}
	
	public void update(){
		//exitEvent
		for(double key:UI.list.keySet()){
			UI ui=UI.list.get(key);
			if(ui instanceof UI_Button){
				((UI_Button)ui).exitEvent(Mouse.getMouse());
			}
		}
				
		//enterEvent
		for(double key:UI.list.keySet()){
			UI ui=UI.list.get(key);
			if(ui instanceof UI_Button){
				((UI_Button)ui).enterEvent(Mouse.getMouse());
			}
		}
	}
	
}