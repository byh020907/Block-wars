package com.blockwars.game;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.blockwars.CallbackEvent;
import com.blockwars.graphics.Screen;
import com.blockwars.input.Keyboard;
import com.blockwars.input.Mouse;
import com.blockwars.state.GameStateManager;
import com.sun.glass.events.WindowEvent;


public class Game extends JPanel implements Runnable{
	
	//network
	
	//game
	public static int width=300;
	public static int height=width/16*9;
	public static int scale=3;
	public static String title="Block-wars";
	
	private Thread thread;
	
	private JFrame frame=new JFrame();
	public static Screen screen;
	private boolean running=false;
	
	GameStateManager gsm;
	Mouse mouse;
	Keyboard keyboard;
	
	Image bufferImage;
	Graphics2D bufferGraphics;
	Graphics2D context;
	
	public Game(){
		Dimension size=new Dimension(width*scale,height*scale);
		setPreferredSize(size);
		init();
	}
	
	private void init(){
		//윈도우바 표시여부
//		frame.setUndecorated(true);
		//
		frame.setResizable(false);
		frame.setTitle(title);
		frame.setContentPane(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		bufferImage=createImage(getWidth(),getHeight());
		bufferGraphics=(Graphics2D)bufferImage.getGraphics();
		context=(Graphics2D)this.getGraphics();
		
		keyboard=Keyboard.getKeyboard();
		addKeyListener(keyboard);
		mouse=Mouse.getMouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		screen=new Screen(width,height);
		gsm=new GameStateManager();
		start();
	}
	
	public synchronized void start(){
		running=true;
		thread=new Thread(this,"Display");
		thread.start();
	}
	
	public synchronized void stop(){
		running=false;
		try{
			thread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime=System.nanoTime();
		long timer=System.currentTimeMillis();
		final double ns=1000000000.0/60.0;
		double delta=0;
		int frames=0;
		int updates=0;
		requestFocus();
		try{
			while(running){
				long now=System.nanoTime();

				delta+=(now-lastTime)/ns;
				lastTime=now;
				while(delta>=1){
					update();
					updates++;
					delta--;
				}
				
				renderAndDraw();
				frames++;
				
				if(System.currentTimeMillis()-timer>1000){
					timer+=1000;
					frame.setTitle(title+"  |  "+updates+" ups, "+frames+" fps");
					updates=0;
					frames=0;
				}
			}
			stop();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		synchronized(GameStateManager.obj1){
			gsm.update();
		}
	}
	
	public void render(Screen screen){
		gsm.render(screen);
		
		//double buffering
		
		bufferGraphics.drawImage(screen.image,0,0,getWidth(),getHeight(),null);
//		bufferGraphics.setColor(Color.WHITE);
//		bufferGraphics.setFont(new Font("Verdana",0,50));
//		bufferGraphics.fillRect(Mouse.getX()-5, Mouse.getY()-5, 10, 10);
//		bufferGraphics.drawString("Button: "+Mouse.getButton(), 10, 50);
//		bufferGraphics.setColor(Color.BLACK);
//		bufferGraphics.drawRect(getWidth()-5, getHeight()-5, 10, 10);
	}
	
	public void draw(Graphics2D g2D){
		gsm.draw(g2D);
	}
	
	public void renderAndDraw(){
		//double buffering
		render(screen);
		draw(bufferGraphics);
		context.drawImage(bufferImage,0,0,getWidth(),getHeight(),null);
	}
	
}