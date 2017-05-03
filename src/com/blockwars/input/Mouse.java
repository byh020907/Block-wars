package com.blockwars.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.blockwars.UI.UI;
import com.blockwars.UI.UI_Button;
import com.blockwars.UI.UI_TextField;

public class Mouse implements MouseListener, MouseMotionListener{

	private static int mouseX=-1;
	private static int mouseY=-1;
	private static int mouseB=-1;
	
	private static Mouse mouse=new Mouse();
	
	private Mouse(){
		
	}
	
	public static Mouse getMouse(){
		return mouse;
	}
	
	public static int getX(){
		return mouseX;
	}
	
	public static int getY(){
		return mouseY;
	}
	
	public static int getButton(){
		return mouseB;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(double key:UI.list.keySet()){
			UI ui=UI.list.get(key);
			if(ui instanceof UI_Button){
				((UI_Button)ui).clickedEvent(mouse);
			}else if(ui instanceof UI_TextField){
				((UI_TextField)ui).clickedEvent(mouse);
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();
		mouseB=e.getButton();
		
		for(double key:UI.list.keySet()){
			UI ui=UI.list.get(key);
			if(ui instanceof UI_Button){
				((UI_Button)ui).pressedEvent(mouse);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseB=-1;
		for(double key:UI.list.keySet()){
			UI ui=UI.list.get(key);
			if(ui instanceof UI_Button){
				((UI_Button)ui).releasedEvent(mouse);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
}
