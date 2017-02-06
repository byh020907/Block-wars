package com.blockwars.game;

public class ShareData {
	public static final int WIDTH=1000;
	public static final int HEIGHT=WIDTH/4*3;
	public static double pivotX=0;
	public static double pivotY=0;
	public static void setPivot(double x,double y,double paningSpeed){
		pivotX+=(x-pivotX)*paningSpeed;
		pivotY+=(y-pivotY)*paningSpeed;
	}
	public static boolean mousePressed=false;
	public static double userSeeAngle=0;
}
