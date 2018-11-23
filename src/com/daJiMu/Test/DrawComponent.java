package com.daJiMu.Test;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import com.daJiMu.shapes.*;
import com.daJiMu.tools.*;

public class DrawComponent extends JComponent {
	/**
	 * @author 100622161
	 * @param serialVersionUID 序列化id
	 * @param DEFAULT_WIDTH 默认窗口宽度
	 * @param DEFAULT_HIGHT 默认窗口高度
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HIGHT = 400;
	
	/**
	 * 视图左面下落区的起始位置坐标(x,y)和宽度WALL_WIDTH，高度WALL_HEIGHT
	 */
	private static final int WALL_X = 50;
	private static final int WALL_Y = 30;
	private static final int WALL_WIDTH = 900;
	private static final int WALL_HEIGHT = 900;
	
	/**
	 * 待选区位置(x,y)和宽度WAIT_WIDTH，高度WAIT_HEIGHT
	 */
	private static final int WAIT_X = 1000;
	private static final int WAIT_Y = 30;
	private static final int WAIT_WIDTH = 780;
	private static final int WAIT_HEIGHT = 720;
	
	/**
	 * 待选形状的数量
	 */
	private static final int NUM_OF_SHAPE = 24;
	
	/** 
	 * 属性：墙，6行 4列的 表格，用于绘制网格 
	 */
	private Cell[][] wall = new Cell[6][4];
	/**
	 * 形状列表，包含形状和其操作函数。在此用于绘制形状
	 */
	private AreaToolModel areaTool = new AreaToolModel(NUM_OF_SHAPE);
	/**
	 * 当前操作的形状对象
	 */
	public static ShapeRoot currentShape;
	
	//public ArrayList<ShapeRoot> dropShape = new ArrayList<ShapeRoot>();
	
	private static final Shape1 BOTTOM_SP = new Shape1(WALL_WIDTH,2,WALL_WIDTH/2+WALL_X,WALL_HEIGHT+WALL_Y+1);
	
	
	private boolean bol = false;
	
	Timer timer;
	
	public DrawComponent(){
		/**
		 * 添加鼠标监听
		 */
		addMouseMotionListener(new MouseMotionHandler());
		initCell();//初始化网格，把要生成的随机形状初始化
		init();
	}
	
	/**
	 * 重写的方法，页面初始化时调用，repaint()时调用，用于绘制窗体
	 */
	@Override
	public void paintComponent(Graphics g) {
		/**
		 * 绘制边框和形状
		 */
		paintWall(g);
		paintWait(g);
		BOTTOM_SP.drawShape(g);
		if (bol) {
			bol = false;
			timer.start();
		}
		bol = true;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH,DEFAULT_HIGHT);
	}
	/**
	 * 绘制墙壁
	 * @param g 画笔
	 */
	public void paintWall(Graphics g) {
		g.drawRect(WALL_X, WALL_Y, WALL_WIDTH, WALL_HEIGHT);
	}
	/**
	 * 绘制网格和图形
	 * @param g
	 */
	public void paintWait(Graphics g) {
		g.drawRect(WAIT_X, WAIT_Y, WAIT_WIDTH, WAIT_HEIGHT);
		// 外层循环控制行数
		int index = 0;
		for (int i = 0; i < wall.length; i++) {
			// 内存循环控制列数
			for (int j = 0; j < wall[0].length; j++) {
				//绘制网格
				g.drawRect(wall[i][j].x, wall[i][j].y, Cell.col, Cell.row);
				//绘制图形
				areaTool.shapes.get(index++).drawShape(g);
			}
		}
	}
	/**
	 * 初始化网格，把要生成的随机形状初始化
	 */
	public void initCell() {
		// 外层循环控制行数
		for (int i = 0; i < wall.length; i++) {
			// 内存循环控制列数
			for (int j = 0; j < wall[0].length; j++) {
				int x = j * (Cell.col+20) + WAIT_X;//20是定义的网格水平之间的间隔
				int y = i * Cell.row + WAIT_Y;
				wall[i][j] = new Cell(x, y);
				areaTool.shapes.add(ShapeRoot.randomOne(wall[i][j].centerX,wall[i][j].centerY));
				//添加新的区域，用于鼠标进入时变成move图标
			}
		}
	}
	
	/**
	 * 定时器，用于块的下落动画
	 */
	public void init() {
		timer = new Timer(10, e -> {
			if (currentShape != null && currentShape.x > WALL_X && currentShape.x < WALL_X + WALL_WIDTH) {
				//先判断当前块符合下落条件，则将当前块的isDrop设为true
				areaTool.shapes.get(areaTool.shapes.indexOf(currentShape)).isDrop = true;
				//判断当前块符合被判断相交条件，则将当前块的isTrack设为true
				areaTool.shapes.get(areaTool.shapes.indexOf(currentShape)).isTrack = true;
			}else if(areaTool.shapes.contains(currentShape)){
				//当前块已不符合下落条件，则将当前块的isDrop设为false
				areaTool.shapes.get(areaTool.shapes.indexOf(currentShape)).isDrop = false;
			}
			for (ShapeRoot cur : areaTool.shapes){
				/*for (ShapeRoot cu : areaTool.shapes) {
					if (!cur.toString().equals(cu.toString()) && cur.isTrack && cu.intersects(cur)) {
						cur.isDrop = false;
						//System.out.println("已经触底");
					}
				}*/
				//循环判断所有块，满足下落条件的执行下落逻辑，限定下落截止位置
				if (cur.isDrop && cur.x > WALL_X && cur.x < WALL_X + WALL_WIDTH - cur.width
						&& cur.y > WALL_Y && cur.y < WALL_Y + WALL_HEIGHT - cur.hight) {
					//System.out.println("开始下落");
					cur.centerY ++;
					repaint();
				}
				//对于每块，都要判断它与其他的块是否相交了
			}
		});
	}
	/**
	 * 旋转控制函数，角度发生改变时修改对应形状的角度，然后重新绘制
	 * @param angle
	 */
	public void angleChange(int angle) {
		currentShape.angle = angle;
		repaint();
	}
	
	private class MouseMotionHandler implements MouseMotionListener{
		/**
		 * 鼠标事件监听，鼠标进入图形点击后执行
		 * @author 100622161
		 */
		@Override
		public void mouseDragged(MouseEvent event) {
			if (currentShape.contains(event.getPoint())) {
				//进入目标图形，且点击后
				currentShape.centerX = event.getX();
				currentShape.centerY = event.getY();
				repaint();	
			}
		}
		/**
		 * 鼠标事件监听，鼠标进入图形执行
		 * @author 100622161
		 */
		@Override
		public void mouseMoved(MouseEvent event) {
			if (areaTool.contains(event.getPoint())) {
				//改变鼠标形状，进入图形时显示为移动按钮
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				currentShape = areaTool.getCurrent();
			}else {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if (currentShape != null && currentShape.centerX > WALL_X && currentShape.centerX < WALL_X+WALL_WIDTH
						&& currentShape.centerY > WALL_Y && currentShape.centerY < WALL_Y+WALL_HEIGHT){
					//todo 触发下落
					bol = true;
				}
			}
		}
		
		
	}
}
