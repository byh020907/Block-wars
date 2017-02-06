package com.blockwars.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;


public class Screen {
	
	public int width;
	public int height;
	
	public BufferedImage image;
	public int[] pixels;
	
	public double yOffset;
	public double xOffset;
	
	public Screen(int width,int height){
		this.width=width;
		this.height=height;
		image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		pixels=((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}
	
	public void clear(){
		for(int i=0;i<pixels.length;i++){
			pixels[i]=0x000000;
		}
	}
	
	public void render(Sprite sprite,int xx,int yy){
		for(int y=0;y<sprite.SIZE;y++){
			int yp=y+yy;
			if(yp<0||yp>=height)continue;
			for(int x=0;x<sprite.SIZE;x++){
				int xp=x+xx;
				if(xp<0||xp>=width)continue;
				
				if(sprite.pixels[x+y*sprite.SIZE]!=0x00000000){
					pixels[xp+yp*width]=sprite.pixels[x+y*sprite.SIZE];
				}
			}
		}
	}
	
	public void setOffset(double xOffset,double yOffset){
		this.xOffset=xOffset;
		this.yOffset=yOffset;
	}
}
