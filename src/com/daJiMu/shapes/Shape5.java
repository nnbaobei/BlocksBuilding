package com.daJiMu.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Shape5 extends ShapeRoot {

	//shape5 ������
	/**
	 * *****
	 * *****
	 */
	{		
		width = 180;
		hight = 30;
	}
	
	/**
	 * 
	 * @param width ָ������
	 * @param hight ָ���߶�
	 * @param centerX ����λ��x
	 * @param centerY ����λ��y
	 */
	public Shape5(int width,int hight,int centerX,int centerY) {
		super(width,hight,centerX,centerY);
		x = centerX - width / 2;
		y = centerY - hight / 2;
		rect = new Rectangle2D.Double(x,y,width,hight);
	}
	
	/**
	 * �ж������������Ƿ���ײ
	 * @param rec
	 * @return
	 */
	public boolean intersects(ShapeRoot rec) {
		Area a = new Area(this.rect);
		a.intersect(new Area(rec));
		return !a.isEmpty();
	}

	@Override
	public void drawShape(Graphics g) {
		Rectangle2D rect = new Rectangle2D.Double(x,y,width,hight);
		Graphics2D g2 = (Graphics2D) g;
		rect = new Rectangle2D.Double(x,y,width,hight);
		g2.setColor(Color.MAGENTA);
		x = centerX - width / 2;
		y = centerY - hight / 2;
		g2.rotate(Math.toRadians(angle),centerX,centerY);
		g2.fill(rect);//ʹ��fill�������򴴽���ͼ����ɫΪʵ��	
		g2.setColor(Color.BLACK);
		g2.rotate(Math.toRadians(-angle),centerX,centerY);
	}
	
	/**
	 * �ж�ĳ�����Ƿ��ھ�����
	 */
	@Override
	public boolean contains(Point2D p) {
		//����תĿ���
		/**
		 * ������Ϊԭ�㽨��������ϵ
		 * ��������ʽ��ʾ��p������
		 */
		Double ro1 = p.distance(centerX,centerY);
		Double thx1 = Math.atan((p.getY() - centerY)/(p.getX() - centerX));
		/**
		 * ת�����p'��ļ�����
		 */
		Double ro2 = ro1;
		Double thx2 = thx1 - Math.toRadians(angle);
		/**
		 * ���������µĵ�ת����ֱ�����꣬���ƶ�ԭ��
		 */
		Double xi = ro2 * Math.cos(thx2) + centerX;
		Double yi = ro2 * Math.sin(thx2) + centerY;
		
		return rect.contains(new Point2D.Double(xi,yi));
	}
	
	/**
	 * ����һ����ȫ��Χ Shape ������ Rectangle
	 */
	@Override
	public Rectangle getBounds() {
		return rect.getBounds();
	}

	/**
	 * ����һ���߾��ȵġ��� getBounds ������׼ȷ�� Shape �߽��
	 */
	@Override
	public Rectangle2D getBounds2D() {
		return rect.getBounds2D();
	}

	/**
	 * ����ָ�������Ƿ��� Shape �ı߽���
	 */
	@Override
	public boolean contains(double x, double y) {
		return this.contains(new Point2D.Double(x,y));
	}

	/**
	 * ���� Shape �ڲ��Ƿ���ָ������������ڲ��ཻ
	 */
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return rect.intersects(x, y, w, h);
	}

	/**
	 * ���� Shape �ڲ��Ƿ���ָ�� Rectangle2D(����) �ڲ��ཻ
	 */
	@Override
	public boolean intersects(Rectangle2D r) {
		return rect.intersects(r);
	}

	/**
	 * ���� Shape �ڲ��Ƿ���ȫ����ָ����������
	 */
	@Override
	public boolean contains(double x, double y, double w, double h) {
		return rect.contains(x, y, w, h);
	}

	/**
	 * ���� Shape �ڲ��Ƿ���ȫ����ָ���� Rectangle2D(����)
	 */
	@Override
	public boolean contains(Rectangle2D r) {
		return rect.contains(r);
	}

	/**
	 * ����һ������ Shape �߽�������ṩ�� Shape ����������״�ķ��ʵĵ���������
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return rect.getPathIterator(at);
	}

	/**
	 * ����һ������ Shape �߽�������ṩ�� Shape ����������״��ƽ����ͼ���ʵĵ���������
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return rect.getPathIterator(at, flatness);
	}

}