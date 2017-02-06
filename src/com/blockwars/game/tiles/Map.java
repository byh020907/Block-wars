package com.blockwars.game.tiles;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.blockwars.graphics.Screen;
import com.blockwars.graphics.Sprite;

public class Map{

	public Tile[][] tiles;
	private int[][] levelPixels;
	public final int tileSize;
	public int depth;
	public int width;
	public int height;
	
	public Map(String[] paths,int tileSize) {
		this.tileSize=tileSize;
		this.depth=paths.length;
		tiles=new Tile[depth][];
		levelPixels=new int[depth][];
		
		loadLevels(paths);
		generateMap();
	}
	
	private void loadLevel(String path,int depth){
		
		try{
			BufferedImage image=ImageIO.read(new File(path));
			width=image.getWidth();
			height=image.getHeight();
			tiles[depth]=new Tile[width*height];
			levelPixels[depth]=new int[width*height];
			image.getRGB(0,0,width,height,levelPixels[depth],0,width);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void loadLevels(String[] paths){
		for(int z=0;z<paths.length;z++){
			loadLevel(paths[z],z);
		}
	}
	
	
	
	public void generateMap(){
		for(int z=0;z<depth;z++){
			for(int y=0;y<height;y++){
				for(int x=0;x<width;x++){
					switch(levelPixels[z][x+y*width]){
					
						case Tile.VOID_TILE:{
							setTile(new VoidTile(Sprite.voidSprite,tileSize),x,y,z);
						}break;
					
						case Tile.GRASS_TILE:{
							setTile(new GrassTile(Sprite.grass,tileSize),x,y,z);
						}break;
						
						case Tile.STONE_TILE:{
							setTile(new StoneTile(Sprite.stone,tileSize),x,y,z);
						}break;
						
						case Tile.WATER_TILE:{
							setTile(new WaterTile(Sprite.water,tileSize),x,y,z);
						}break;
						
						case Tile.LAVA_TILE:{
							setTile(new LavaTile(Sprite.lava,tileSize),x,y,z);
						}break;
						
						case Tile.WEB_TILE:{
							setTile(new WebTile(Sprite.web,tileSize),x,y,z);
						}break;
						
						case Tile.ROSE_TILE:{
							setTile(new RoseTile(Sprite.rose,tileSize),x,y,z);
						}break;
						
						default:{
							
						}break;
						
					}
				}
			}
		}
	}
	
	public void setTile(Tile tile,int x,int y,int depth){
		tile.x=x*tileSize;
		tile.y=y*tileSize;
		tiles[depth][x+y*width]=tile;
	}
	
	public void removeTile(int x,int y,int depth){
		tiles[depth][x+y*width]=null;
	}
	
	public void render(Screen screen){
		for(int z=0;z<depth;z++){
			for(int y=0;y<height;y++){
				for(int x=0;x<width;x++){
					if(tiles[z][x+y*width]!=null){
						tiles[z][x+y*width].render(screen);
					}
				}
			}
		}
	}
	
}
