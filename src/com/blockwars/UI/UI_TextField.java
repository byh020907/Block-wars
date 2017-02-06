package com.blockwars.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.blockwars.CallbackEvent;
import com.blockwars.input.Keyboard;
import com.blockwars.input.Mouse;
import com.blockwars.utils.Util;

public class UI_TextField extends UI{
	
	public static UI_TextField currentSelected; 
	public StringBuffer content=new StringBuffer();
	public String fontName=null;
	public int fontStyle=Font.PLAIN;
	public Color textColor=Color.BLACK;
	
	public boolean focus=false;
	public boolean lock=false;
	public CallbackEvent tabAction;
	
	public UI_TextField(Image image, int x, int y, int width, int height) {
		super(image, x, y, width, height);
	}
	
	public UI_TextField(Image image, int x, int y, int width, int height,String s) {
		super(image, x, y, width, height);
		content.append(s);
		lock=true;
	}
	
	public void clickedEvent(Mouse mouse){
		if(!lock&&Util.hitTestPoint(mouse, this)){
			focus=true;
			currentSelected=this;
		}else{
			focus=false;
		}
	}
	
	public void setFocus(){
		for(double key:UI.list.keySet()){
			UI ui=UI.list.get(key);
			if(ui instanceof UI_TextField){
				((UI_TextField)ui).focus=false;
			}
		}
		currentSelected=this;
		focus=true;
	}
	
	public void tabAction(){
		if(tabAction!=null){
			tabAction.callbackMethod();
		}
	}
	
	public void render(Graphics2D g2D){
		AffineTransform old = g2D.getTransform();
		g2D.drawImage(image,x,y,width,height,null);
		if(focus){
			g2D.setColor(new Color(10,10,10,200));
			g2D.fillRect(x, y, width, height);
		}
		g2D.setFont(new Font(fontName,fontStyle,height));
		g2D.setColor(textColor);
		g2D.drawString(content.toString(), x, y+height);
		g2D.setTransform(old);
	}
	
	public void update(){
		
	}
	
}
