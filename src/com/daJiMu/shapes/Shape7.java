package com.daJiMu.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Shape7 extends ShapeRoot {
	
	RectangularShape rect1;
	RectangularShape rect2;
	
	//shape7 半圆
	
	/**
	 * 
	 * @param width 指定宽度
	 * @param hight 指定高度
	 * @param centerX 中心位置x
	 * @param centerY 中心位置y
	 */
	public Shape7(int width,int hight,int centerX,int centerY) {
		super(width,hight,centerX,centerY);
		x = centerX - width / 2;
		y = centerY - hight / 2;
		rect1 = new Rectangle2D.Double(x,y+hight,width,hight);
		rect2 = new Ellipse2D.Double(x,y,width,hight * 2);
	}
	
	/**
	 * 判断与其他矩形是否相撞
	 * @param rec
	 * @return
	 */
	public boolean intersects(ShapeRoot rec) {
		Area a = new Area(rect2);
		a.subtract(new Area(rect1));
		a.intersect(new Area(rec));
		return !a.isEmpty();
	}
	
	/**
	 * 判断与其他域是否相撞
	 * @param rec 参数形状
	 * @return
	 */
	public boolean intersects(Area area) {
		Area as = this.toArea();
		as.intersect(area);
		return !as.isEmpty();
	}
	
	/**
	 * 将目标图形转化为区域
	 */
	@Override
	public Area toArea() {
		Area a = new Area(rect2);
		a.subtract(new Area(rect1));
		return a;
	}
	
	@Override
	public void drawShape(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		rect1 = new Rectangle2D.Double(x,y+hight,width,hight);
		rect2 = new Ellipse2D.Double(x,y,width,hight*2);
		Area a = new Area(rect2);
		a.subtract(new Area(rect1));
		g2.setColor(Color.PINK);
		x = centerX - width / 2;
		y = centerY - hight / 2;
		g2.rotate(Math.toRadians(angle),centerX,centerY);
		//画一个半圆
		g2.fill(a);
		g2.setColor(Color.BLACK);
		g2.rotate(Math.toRadians(-angle),centerX,centerY);
	}
	
	/**
	 * 返回一个完全包围 Shape 的整型 Rectangle
	 */
	@Override
	public Rectangle getBounds() {
		return rect.getBounds();
	}

	/**
	 * 返回一个高精度的、比 getBounds 方法更准确的 Shape 边界框
	 */
	@Override
	public Rectangle2D getBounds2D() {
		return rect.getBounds2D();
	}

	/**
	 * 测试指定坐标是否在 Shape 的边界内
	 */
	@Override
	public boolean contains(double x, double y) {
		return this.contains(new Point2D.Double(x,y));
	}

	/**
	 * 测试 Shape 内部是否与指定矩形区域的内部相交
	 */
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return rect.intersects(x, y, w, h);
	}

	/**
	 * 测试 Shape 内部是否与指定 Rectangle2D(矩形) 内部相交
	 */
	@Override
	public boolean intersects(Rectangle2D r) {
		return rect.intersects(r);
	}

	/**
	 * 测试 Shape 内部是否完全包含指定矩形区域
	 */
	@Override
	public boolean contains(double x, double y, double w, double h) {
		return rect.contains(x, y, w, h);
	}

	/**
	 * 测试 Shape 内部是否完全包含指定的 Rectangle2D(矩形)
	 */
	@Override
	public boolean contains(Rectangle2D r) {
		return rect.contains(r);
	}

	/**
	 * 返回一个沿着 Shape 边界迭代并提供对 Shape 轮廓几何形状的访问的迭代器对象
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return rect.getPathIterator(at);
	}

	/**
	 * 返回一个沿着 Shape 边界迭代并提供对 Shape 轮廓几何形状的平面视图访问的迭代器对象
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return rect.getPathIterator(at, flatness);
	}

}
