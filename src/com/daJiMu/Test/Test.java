package com.daJiMu.Test;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Test {
	

	static DrawComponent drp = new DrawComponent();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(()->{
			JFrame frame = new JFrame();
			frame.add(drp);
			frame.setTitle("画图");
			
			//得到屏幕的大小
			Toolkit kit = Toolkit.getDefaultToolkit();
			Dimension screenSize = kit.getScreenSize();
			
			Double wi = screenSize.getWidth();
			Double he = screenSize.getHeight();
			
			// 3:设置窗口的尺寸
			frame.setSize(wi.intValue(),he.intValue());

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//设置为可见
			frame.setVisible(true);
			// 4:设置窗口居中
			frame.setLocationRelativeTo(null);
			
			// 开启键盘监听事件
			keyListener(frame);
			
			
			
		});
	}
	public static void keyListener(JFrame frame) {
		KeyListener l = new KeyAdapter() {
			/*
			 * KeyPressed() 是键盘按钮 按下去所调用的方法
			 */
			public void keyPressed(KeyEvent e) {
				// 获取一下键子的代号
				int code = e.getKeyCode();
				switch (code) {
				case KeyEvent.VK_Q:
					turnRight(DrawComponent.currentShape.angle);
					break;
				case KeyEvent.VK_E:
					turnLeft(DrawComponent.currentShape.angle);
					break;
				}
			}
		};
		// 面板添加监听事件对象
		frame.addKeyListener(l);
		// 面板对象设置成焦点
		frame.requestFocus();
	}
	public static void turnRight(int degress) {
		degress --;
		degress = (degress + 360) % 360;
		drp.angleChange(degress);
	}
	public static void turnLeft(int degress) {
		degress ++;
		degress = (degress + 360) % 360;
		drp.angleChange(degress);
	}
}
