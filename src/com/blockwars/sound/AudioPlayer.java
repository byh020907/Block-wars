package com.blockwars.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;

public class AudioPlayer {
	
//	public static CopyOnWriteArrayList<AudioPlayer> list=new CopyOnWriteArrayList<AudioPlayer>();
//	
//	public static final AudioPlayer BACKGROUND1=new AudioPlayer("res/audios/Yumetourou Acoustic Ver.wav",false);
//	
//	public static final AudioPlayer SHOT1=new AudioPlayer("res/audios/shot1.wav",false);
//	
//	public Clip clip;
//	AudioInputStream ais;
//	
//	public static int allSoundNum=0;
//	
//	public AudioPlayer(String file, boolean loop) {
//		// 사운드재생용메소드
//		// 메인 클래스에 추가로 메소드를 하나 더 만들었습니다.
//		// 사운드파일을받아들여해당사운드를재생시킨다.
//		
//		try {
//			ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
//			DataLine.Info info=new DataLine.Info(Clip.class,ais.getFormat());
//			
//			clip=(Clip)AudioSystem.getLine(info);
//			clip.open(ais);
//			
//			clip.addLineListener(new LineListener(){
//				public void update(LineEvent e){
//					if(e.getType()==LineEvent.Type.STOP){
//						e.getLine().close();
//						try {
//							ais.close();
//						} catch (IOException e1) {
//							e1.printStackTrace();
//						}
//						list.remove(this);
//						System.out.println(list.size());
//					}
//				}
//			});
//			
//			if(loop){
//				clip.loop(Clip.LOOP_CONTINUOUSLY);
//			}
//			
////			if(list.size()>=50){
////				list.get(0).stop();
////				list.remove(0);
////			}
//			list.add(this);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void play(){
//		if(clip==null)return;
//		stop();
//		clip.setFramePosition(0);
//		clip.start();
//	}
//	
//	public void stop(){
//		if(clip.isRunning()){
//			clip.stop();
//		}
//	}
//	
//	public void close(){
//		stop();
//		clip.close();
//	}
//	
//	@Override
//	public void finalize(){
//		System.out.println(this.toString());
//	}
	
	private AudioClip clip;
	
	public static final AudioPlayer BACKGROUND=new AudioPlayer("res/audios/POL-galactic-chase-short.wav");
	
	public static final AudioPlayer SHOT1=new AudioPlayer("res/audios/Gun2.wav");
	
	public static final AudioPlayer HIT1=new AudioPlayer("res/audios/Realistic_Punch.wav");
	

	public AudioPlayer(String name) {
		try {
			clip = Applet.newAudioClip(new File(name).toURL());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
