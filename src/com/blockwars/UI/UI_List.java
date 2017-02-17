package com.blockwars.UI;

import java.awt.Image;
import java.util.ArrayList;

import com.blockwars.CallbackAble;
import com.blockwars.game.Resource;

public class UI_List extends UI{
	
	public ArrayList<UI_Button> btnList=new ArrayList<UI_Button>();
	
	int buttonWidth=width-20;
	int buttonHeight=50;
	int gap=10;

	public UI_List(Image image, int x, int y, int width, int height) {
		super(image, x, y, width, height);
	}
	
	public void addButton(CallbackAble c){
		UI_Button btn=new UI_Button(Resource.homeImg,this.x+gap,this.y+gap+btnList.size()*(buttonHeight+gap),buttonWidth,buttonHeight);
		btn.setDepth(1+Math.random());
		btn.clickEvent=c;
		btnList.add(btn);
	}
	
	public void removeButton(int index){
		btnList.remove(index);
		for(int i=0;i<btnList.size();i++){
			UI_Button btn=btnList.get(i);
			btn.y=this.y+gap+i*(buttonHeight+gap);
		}
	}
}
