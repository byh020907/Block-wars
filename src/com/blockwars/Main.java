package com.blockwars;
import javax.swing.*;

import com.blockwars.game.Game;
import com.blockwars.game.entities.*;
import com.blockwars.game.weapon.*;
import com.blockwars.utils.Util;
public class Main{

	public static void main(String[] args) {
		//Game 객체는 인자로 대기방 플레이어의 id와 map의 종류를 받는다.
		//그리고 플레이어의 id 값에따라 플레이어의 초기 물품을 설정한다.
		try {
			Game g=new Game();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		gcc();
	}
	
	public static void gcc(){
		Util.setTimeout(10000, new CallbackEvent(){
			public void callbackMethod(){
				System.gc();
				gcc();
			}
		});
	}
}
