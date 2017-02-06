package com.blockwars.graphics;


public class Sprite {
	public final int SIZE;
	private int x,y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	public static Sprite voidSprite=new Sprite(16,0xffffff);
	public static Sprite grass=new Sprite(16,0,0,SpriteSheet.tiles);
	public static Sprite stone=new Sprite(16,1,0,SpriteSheet.tiles);
	public static Sprite dirt=new Sprite(16,2,0,SpriteSheet.tiles);
	public static Sprite grassSide=new Sprite(16,3,0,SpriteSheet.tiles);
	public static Sprite woodPlank=new Sprite(16,4,0,SpriteSheet.tiles);
	public static Sprite halfStoneBlockSide=new Sprite(16,5,0,SpriteSheet.tiles);
	public static Sprite halfStoneBlockTop=new Sprite(16,6,0,SpriteSheet.tiles);
	public static Sprite brick=new Sprite(16,7,0,SpriteSheet.tiles);
	public static Sprite TNTSide=new Sprite(16,8,0,SpriteSheet.tiles);
	public static Sprite TNTTop=new Sprite(16,9,0,SpriteSheet.tiles);
	public static Sprite TNTBottom=new Sprite(16,10,0,SpriteSheet.tiles);
	public static Sprite web=new Sprite(16,11,0,SpriteSheet.tiles);
	public static Sprite rose=new Sprite(16,12,0,SpriteSheet.tiles);
	public static Sprite dandelion=new Sprite(16,13,0,SpriteSheet.tiles);
	public static Sprite blueRose=new Sprite(16,14,0,SpriteSheet.tiles);
	public static Sprite OakSeedlings=new Sprite(16,15,0,SpriteSheet.tiles);
	public static Sprite water=new Sprite(16,15,15,SpriteSheet.tiles);
	public static Sprite lava=new Sprite(16,14,15,SpriteSheet.tiles);
	
	public static Sprite basicParticle=new Sprite(4,0x0f0f0f);
	public static Sprite bloodParticle=new Sprite(4,0xff0000);
	
	public static Sprite basicBullet=new Sprite(4,0x0000ff);

	public Sprite(int size,int x,int y,SpriteSheet sheet){
		SIZE=size;
		pixels=new int[SIZE*SIZE];
		this.x=x*size;
		this.y=y*size;
		this.sheet=sheet;
		load();
	}
	
	public Sprite(int size,int colour){
		SIZE=size;
		pixels=new int[SIZE*SIZE];
		setColour(colour);
	}
	
	private void setColour(int colour){
		for(int i=0;i<SIZE*SIZE;i++){
			pixels[i]=colour;
		}
	}
	
	private void load(){
		for(int y=0;y<SIZE;y++){
			for(int x=0;x<SIZE;x++){
				pixels[x+y*SIZE]=sheet.pixels[(x+this.x)+(y+this.y)*sheet.SIZE];
			}
		}
	}
}