package com.blockwars.UI;

import java.awt.Image;

import com.blockwars.utils.Util;

public class UI_Background extends UI{

	public UI_Background(Image image, int x, int y, int width, int height) {
		super(image, x, y, width, height);
	}
	
	public void move(double angle,double speed){
		double rad=Util.Deg_to_Rad(angle);
		this.x+=Math.cos(rad)*speed;
		this.y+=Math.sin(rad)*speed;
	}
	
	public void update(){
		move(0,1);
	}
	
}
