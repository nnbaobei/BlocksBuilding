package com.daJiMu.shapes;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

/**
 * 所有图形类的父类，包含图形的全部参数等
 * @author 100622161
 *
 */
public abstract class ShapeRoot implements Shape{
	/**
	 * 起始位置坐标(x,y)
	 */
	public int x;
	public int y;
	/**
	 * 重心位置坐标(centerX,centerY)
	 */
	public int centerX;
	public int centerY;
	/**
	 * 旋转角度
	 */
	public int angle = 0;
	/**
	 * 上底，宽度，高度
	 */
	public int up;
	public int width;
	public int hight;
	/**
	 * 扇形的开始角度startAngle，持续角度arcAngle
	 */
	public int arcAngle;
	public int startAngle;
	/**
	 * 绘制不规则图形(梯形和正六边形)的点集
	 */
	public int[] xPoints;
	public int[] yPoints;
	
	protected RectangularShape rect;
	
	/**
	 * 与BASE_AREA的接触区域，提取点集
	 */
	protected Rectangle2D touchArea;
	
	/**
	 * 用状态码的形式来保存状态，修改简单，使用灵活
	 */
	public int shapeState = DEFALUT_STATE;
	
	public static final int DEFALUT_STATE = 1;
	public static final int DROP_STATE = 2;
	public static final int CROSS_STATE = 3;
	public static final int STABLE_STATE = 4;
	
	protected Shape localShape;
	
	/**
	 * 构造方法，初始化时指定
	 * @param width 宽度
	 * @param hight 高度
	 */
	public ShapeRoot(int width,int hight){
		this.width = width;
		this.hight = hight;
	}
	/**
	 * 构造方法，初始化时指定
	 * @param width 宽度
	 * @param hight 高度
	 * @param up 上底
	 * @param centerX 重心坐标x
	 * @param centerY 重心坐标y
	 */
	public ShapeRoot(int width, int hight,int up,int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.hight = hight;
		this.up = up;
	}
	/**
	 * 构造方法，初始化时指定
	 * @param width 宽度
	 * @param hight 高度
	 * @param centerX 重心坐标x
	 * @param centerY 重心坐标y
	 */
	public ShapeRoot(int width, int hight,int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.hight = hight;
	}
	/**
	 * 随机生成一个形状
	 * @param centerX 指定绘画时的重心坐标x
	 * @param centerY 指定绘画时的重心坐标y
	 * @return
	 */
	public static ShapeRoot randomOne(int centerX,int centerY) {
		ShapeRoot  s = null;
		int num = (int)(Math.random()*10);
		switch(num) {
		case 0:s = new Shape1(30,60,centerX,centerY);break;//长方形1
		case 1:s = new Shape2(60,52,centerX,centerY);break;//正六边形
		case 2:s = new Shape3(40,30,29,centerX,centerY);break;//梯型
		case 3:s = new Shape4(60,30,centerX,centerY);break;//等腰直角三角形
		case 4:s = new Shape5(90,15,centerX,centerY);break;//长方形2
		case 5:s = new Shape6(40,35,centerX,centerY);break;//等边三角形
		case 6:s = new Shape5(45,15,centerX,centerY);break;//长方形3
		case 7:s = new Shape1(30,30,centerX,centerY);break;//正方形
		case 8:s = new Shape7(30,15,centerX,centerY);break;//半圆
		case 9:s = new Shape8(60,30,centerX,centerY);break;//拱桥
		}
		return s;
	}
	/**
	 * 判断某点（鼠标点）在不在该图形内部，用于鼠标变形方法
	 * 该方法会被每个子类重写，用于做特征点的旋转
	 * @param p 待检测的点
	 * @return
	 */
	public boolean contains(Point2D p) {
		if (x < p.getX() && (x+width) > p.getX() && y < p.getY() && (y+hight) > p.getY()) {
			return true;
		}
		return false;
	}
	/**
	 * 画图方法，按照自己的参数画图
	 * @param g 画笔
	 */
	public abstract void drawShape(Graphics g);
	
	/**
	 * 判断与其他形状是否相撞
	 * @param rec 参数形状
	 * @return
	 */
	public boolean intersects(ShapeRoot rec) {
		return false;
	}
	/**
	 * 判断与其他域是否相撞
	 * @param rec 参数形状
	 * @return
	 */
	public boolean intersects(Area area) {
		area.intersect(new Area(this));
		return !area.isEmpty();
	}
	
	@Override
	public String toString() {
		return ""+width+"-"+hight;
	}
	
	public abstract Area toArea();
	
}
