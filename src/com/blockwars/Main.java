package com.blockwars;
import javax.swing.*;

import com.blockwars.game.Game;
import com.blockwars.game.entities.*;
import com.blockwars.game.weapon.*;
import com.blockwars.utils.Util;
public class Main{

	public static void main(String[] args) {
		//Game ��ü�� ���ڷ� ���� �÷��̾��� id�� map�� ������ �޴´�.
		//�׸��� �÷��̾��� id �������� �÷��̾��� �ʱ� ��ǰ�� �����Ѵ�.
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
