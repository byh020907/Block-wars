package com.blockwars.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.blockwars.UI.UI_TextField;

public class Keyboard implements KeyListener{
	
	private static boolean[] keys=new boolean[120];
	
	private static Keyboard Keyboard=new Keyboard();
	
	private Keyboard(){
		
	}
	
	public static Keyboard getKeyboard(){
		return Keyboard;
	}
	
	public static boolean getKeyState(int keyCode){
		return keys[keyCode];
	}
	boolean backSpace=false;
	@Override
	public void keyPressed(KeyEvent e) {
		backSpace=false;
		keys[e.getKeyCode()]=true;
		
		UI_TextField tf=UI_TextField.currentSelected;
		if(tf!=null&&tf.focus){
			if(e.getKeyCode()==e.VK_BACK_SPACE){
				backSpace=true;
				if(tf.content.length()!=0){
					tf.content=new StringBuffer(tf.content.deleteCharAt(tf.content.length()-1));
				}
			}else if(e.getKeyCode()==e.VK_SHIFT){
				tf.tabAction();
			}else if(e.getKeyCode()==e.VK_ENTER){
				tf.focus=false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()]=false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		UI_TextField tf=UI_TextField.currentSelected;
		if(tf!=null&&tf.focus&&!backSpace){
			tf.content.append(e.getKeyChar());
		}
	}
	
}