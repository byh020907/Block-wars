package com.blockwars.game;

import java.awt.Image;
import javax.swing.ImageIcon;

import com.blockwars.utils.Util;

public interface Resource {
	Image startImg=Util.loadImage(new ImageIcon("res/images/UI/시작.png"));
	Image pauseImg=Util.loadImage(new ImageIcon("res/images/UI/일시정지.png"));
	Image homeImg=Util.loadImage(new ImageIcon("res/images/UI/홈.png"));
	Image UI_background=Util.loadImage(new ImageIcon("res/images/UI/UI_background1.png"));
	Image blankImg=Util.loadImage(new ImageIcon("res/images/UI/blankImage.png"));
}
