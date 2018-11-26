package com.daJiMu.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Shape2 extends ShapeRoot {
	
	//shape2 正六边型
	/**
	 *  ***
	 * *****
	 *  ***
	 */
	{
		xPoints = new int[]{width/4 + x,0 + x,width/4 + x,3*width/4 + x,width + x,3*width/4 + x};
		yPoints = new int[]{0 + y,hight/2 + y,hight + y,hight + y,hight/2 + y,0 + y};
	}
	
	/**
	 * 
	 * @param width 指定宽度
	 * @param hight 指定高度
	 * @param centerX 中心位置x
	 * @param centerY 中心位置y
	 */
	public Shape2(int width,int hight,int centerX,int centerY) {
		super(width,hight,centerX,centerY);
		x = centerX - width / 2;
		y = centerY - hight / 2;
		rect = new Ellipse2D.Double(x,y,(width+hight)/2,(width+hight)/2);
		localShape = AffineTransform.getRotateInstance(Math.toRadians(angle), centerX, centerY).createTransformedShape(rect);
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
		return new Area(localShape);
	}
	
	/**
	 * 判断某点是否在正六边形内
	 * 连接中心和目标点，依次判断与每条边是否相交
	 */
	public boolean contains(Point2D p) {
		//先旋转目标点
		/**
		 * 以重心为原点建立极坐标系
		 * 极坐标形式表示的p点坐标
		 */
		Double ro1 = p.distance(centerX,centerY);
		Double thx1 = Math.atan((p.getY() - centerY)/(p.getX() - centerX));
		/**
		 * 转换后的p'点的极坐标
		 */
		Double ro2 = ro1;
		Double thx2 = thx1 - Math.toRadians(angle);
		/**
		 * 将极坐标下的点转换到直角坐标，并移动原点
		 */
		Double xi = ro2 * Math.cos(thx2) + centerX;
		Double yi = ro2 * Math.sin(thx2) + centerY;
		
		//创建目标点一条连接中心的线
		Line2D line = new Line2D.Double(xi,yi,centerX,centerY);
		for(int i=0;i<xPoints.length;i++) {
			int tari = i+1;
			if (tari == xPoints.length) {
				tari = 0;
			}
			if (line.intersectsLine(xPoints[i],yPoints[i],xPoints[tari],yPoints[tari])) {
				return false;
			}
		}
		return true;
	}


	@Override
	public void drawShape(Graphics g) {
		//Rectangle2D rect = new Rectangle2D.Double(x,y,width,hight);
		Graphics2D g2 = (Graphics2D) g;
		rect = new Ellipse2D.Double(x,y,(width+hight)/2,(width+hight)/2);
		g2.setColor(Color.CYAN);
		x = centerX - width / 2;
		y = centerY - hight / 2;
		g2.rotate(Math.toRadians(angle),centerX,centerY);
		xPoints = new int[]{width/4 + x,0 + x,width/4 + x,3*width/4 + x,width + x,3*width/4 + x};
		yPoints = new int[]{0 + y,hight/2 + y,hight + y,hight + y,hight/2 + y,0 + y};
		
		localShape = AffineTransform.getRotateInstance(Math.toRadians(angle), centerX, centerY).createTransformedShape(rect);
		//g2.fill(localShape);
		
		g2.fillPolygon(xPoints, yPoints, 6);//使用fill方法，则创建的图形颜色为实心
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
