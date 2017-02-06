package com.blockwars.UI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.blockwars.game.entities.Particle;
import com.blockwars.game.entities.bullets.Bullet;
import com.blockwars.game.entities.mobs.Player;
import com.blockwars.game.tiles.Map;
import com.blockwars.state.MainGameState;
import com.blockwars.utils.Point;
import com.blockwars.utils.Util;

public class UI_MiniMap extends UI{
	
	protected Map map=MainGameState.map;
	public double scale;
	
	public UI_MiniMap(Image image, int x, int y, int width, int height) {
		super(image, x, y, width, height);
		scale=(double)this.width/(map.width*map.tileSize);
	}
	
	public void render(Graphics2D g2D){
		AffineTransform old = g2D.getTransform();
		g2D.drawImage(image,x,y,width,height,null);
		renderPlayers(g2D);
		renderBullets(g2D);
		renderParticles(g2D);
		g2D.setTransform(old);
	}
	
	public void renderPlayers(Graphics2D g2D){
		g2D.setPaint(Color.LIGHT_GRAY);
		for(Double key:Player.list.keySet()){
			Player p=Player.list.get(key);
			Point point=calculateMiniMapPosition(p.x,p.y);
			
			if(Util.hitTestPoint(x, y, width, height, point)){
				double w;
				double h=w=p.sprite.SIZE*scale;
				if(w<1){
					w=h=1;
				}
				int x=(int)(point.x-w/2);
				int y=(int)(point.y-h/2);
				g2D.fillRect(x, y, (int)w, (int)h);
			}
		}
	}
	
	public void renderBullets(Graphics2D g2D){
		for(Double key:Bullet.list.keySet()){
			Bullet b=Bullet.list.get(key);
			Point point=calculateMiniMapPosition(b.x,b.y);
			
			if(MainGameState.user.id==b.parentId){
				g2D.setPaint(Color.BLUE);
			}else{
				g2D.setPaint(Color.RED);
			}
			
			if(Util.hitTestPoint(x, y, width, height, point)){
				double w;
				double h=w=b.sprite.SIZE*scale;
				if(w<1){
					w=h=1;
				}
				int x=(int)(point.x-w/2);
				int y=(int)(point.y-h/2);
				g2D.fillRect(x, y, (int)w, (int)h);
			}
		}
	}
	
	public void renderParticles(Graphics2D g2D){
		g2D.setPaint(Color.BLACK);
		for(Particle pc:Particle.list){
			Point point=calculateMiniMapPosition(pc.x,pc.y);
			
			if(Util.hitTestPoint(x, y, width, height, point)){
				double w;
				double h=w=pc.sprite.SIZE*scale;
				if(w<1){
					w=h=1;
				}
				int x=(int)(point.x-w/2);
				int y=(int)(point.y-h/2);
				g2D.fillRect(x, y, (int)w, (int)h);
			}
		}
	}
	
	public void renderTiles(Graphics2D g2D){
		g2D.setPaint(Color.BLACK);
		for(int z=0;z<map.depth;z++){
			for(int i=0;i<map.width*map.height;i++){
				if(map.tiles[z][i]!=null&&map.tiles[z][i].solid()){
					Point point=calculateMiniMapPosition(map.tiles[z][i].x,map.tiles[z][i].y);
					
					if(Util.hitTestPoint(x, y, width, height, point)){
						double w;
						double h=w=map.tileSize*scale;
						if(w<1){
							w=h=1;
						}
						int x=(int)(point.x);
						int y=(int)(point.y);
						g2D.fillRect(x, y, (int)w, (int)h);
					}
				}
			}
		}
	}
	
	public Point calculateMiniMapPosition(double x,double y){
		double xp=x*scale+this.x;
		double yp=y*scale+this.y;
		
		return new Point(xp,yp);
	}
	
}
