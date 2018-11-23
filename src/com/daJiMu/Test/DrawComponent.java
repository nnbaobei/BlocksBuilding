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
	 * @param serialVersionUID ���л�id
	 * @param DEFAULT_WIDTH Ĭ�ϴ��ڿ���
	 * @param DEFAULT_HIGHT Ĭ�ϴ��ڸ߶�
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HIGHT = 400;
	
	/**
	 * ��ͼ��������������ʼλ������(x,y)�Ϳ���WALL_WIDTH���߶�WALL_HEIGHT
	 */
	private static final int WALL_X = 50;
	private static final int WALL_Y = 30;
	private static final int WALL_WIDTH = 900;
	private static final int WALL_HEIGHT = 900;
	
	/**
	 * ��ѡ��λ��(x,y)�Ϳ���WAIT_WIDTH���߶�WAIT_HEIGHT
	 */
	private static final int WAIT_X = 1000;
	private static final int WAIT_Y = 30;
	private static final int WAIT_WIDTH = 780;
	private static final int WAIT_HEIGHT = 720;
	
	/**
	 * ��ѡ��״������
	 */
	private static final int NUM_OF_SHAPE = 24;
	
	/** 
	 * ���ԣ�ǽ��6�� 4�е� �������ڻ������� 
	 */
	private Cell[][] wall = new Cell[6][4];
	/**
	 * ��״�б���������״��������������ڴ����ڻ�����״
	 */
	private AreaToolModel areaTool = new AreaToolModel(NUM_OF_SHAPE);
	/**
	 * ��ǰ��������״����
	 */
	public static ShapeRoot currentShape;
	
	//public ArrayList<ShapeRoot> dropShape = new ArrayList<ShapeRoot>();
	
	private static final Shape1 BOTTOM_SP = new Shape1(WALL_WIDTH,2,WALL_WIDTH/2+WALL_X,WALL_HEIGHT+WALL_Y+1);
	
	
	private boolean bol = false;
	
	Timer timer;
	
	public DrawComponent(){
		/**
		 * ����������
		 */
		addMouseMotionListener(new MouseMotionHandler());
		initCell();//��ʼ�����񣬰�Ҫ���ɵ������״��ʼ��
		init();
	}
	
	/**
	 * ��д�ķ�����ҳ���ʼ��ʱ���ã�repaint()ʱ���ã����ڻ��ƴ���
	 */
	@Override
	public void paintComponent(Graphics g) {
		/**
		 * ���Ʊ߿����״
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
	 * ����ǽ��
	 * @param g ����
	 */
	public void paintWall(Graphics g) {
		g.drawRect(WALL_X, WALL_Y, WALL_WIDTH, WALL_HEIGHT);
	}
	/**
	 * ���������ͼ��
	 * @param g
	 */
	public void paintWait(Graphics g) {
		g.drawRect(WAIT_X, WAIT_Y, WAIT_WIDTH, WAIT_HEIGHT);
		// ���ѭ����������
		int index = 0;
		for (int i = 0; i < wall.length; i++) {
			// �ڴ�ѭ����������
			for (int j = 0; j < wall[0].length; j++) {
				//��������
				g.drawRect(wall[i][j].x, wall[i][j].y, Cell.col, Cell.row);
				//����ͼ��
				areaTool.shapes.get(index++).drawShape(g);
			}
		}
	}
	/**
	 * ��ʼ�����񣬰�Ҫ���ɵ������״��ʼ��
	 */
	public void initCell() {
		// ���ѭ����������
		for (int i = 0; i < wall.length; i++) {
			// �ڴ�ѭ����������
			for (int j = 0; j < wall[0].length; j++) {
				int x = j * (Cell.col+20) + WAIT_X;//20�Ƕ��������ˮƽ֮��ļ��
				int y = i * Cell.row + WAIT_Y;
				wall[i][j] = new Cell(x, y);
				areaTool.shapes.add(ShapeRoot.randomOne(wall[i][j].centerX,wall[i][j].centerY));
				//�����µ���������������ʱ���moveͼ��
			}
		}
	}
	
	/**
	 * ��ʱ�������ڿ�����䶯��
	 */
	public void init() {
		timer = new Timer(10, e -> {
			if (currentShape != null && currentShape.x > WALL_X && currentShape.x < WALL_X + WALL_WIDTH) {
				//���жϵ�ǰ����������������򽫵�ǰ���isDrop��Ϊtrue
				areaTool.shapes.get(areaTool.shapes.indexOf(currentShape)).isDrop = true;
				//�жϵ�ǰ����ϱ��ж��ཻ�������򽫵�ǰ���isTrack��Ϊtrue
				areaTool.shapes.get(areaTool.shapes.indexOf(currentShape)).isTrack = true;
			}else if(areaTool.shapes.contains(currentShape)){
				//��ǰ���Ѳ����������������򽫵�ǰ���isDrop��Ϊfalse
				areaTool.shapes.get(areaTool.shapes.indexOf(currentShape)).isDrop = false;
			}
			for (ShapeRoot cur : areaTool.shapes){
				/*for (ShapeRoot cu : areaTool.shapes) {
					if (!cur.toString().equals(cu.toString()) && cur.isTrack && cu.intersects(cur)) {
						cur.isDrop = false;
						//System.out.println("�Ѿ�����");
					}
				}*/
				//ѭ���ж����п飬��������������ִ�������߼����޶������ֹλ��
				if (cur.isDrop && cur.x > WALL_X && cur.x < WALL_X + WALL_WIDTH - cur.width
						&& cur.y > WALL_Y && cur.y < WALL_Y + WALL_HEIGHT - cur.hight) {
					//System.out.println("��ʼ����");
					cur.centerY ++;
					repaint();
				}
				//����ÿ�飬��Ҫ�ж����������Ŀ��Ƿ��ཻ��
			}
		});
	}
	/**
	 * ��ת���ƺ������Ƕȷ����ı�ʱ�޸Ķ�Ӧ��״�ĽǶȣ�Ȼ�����»���
	 * @param angle
	 */
	public void angleChange(int angle) {
		currentShape.angle = angle;
		repaint();
	}
	
	private class MouseMotionHandler implements MouseMotionListener{
		/**
		 * ����¼�������������ͼ�ε����ִ��
		 * @author 100622161
		 */
		@Override
		public void mouseDragged(MouseEvent event) {
			if (currentShape.contains(event.getPoint())) {
				//����Ŀ��ͼ�Σ��ҵ����
				currentShape.centerX = event.getX();
				currentShape.centerY = event.getY();
				repaint();	
			}
		}
		/**
		 * ����¼�������������ͼ��ִ��
		 * @author 100622161
		 */
		@Override
		public void mouseMoved(MouseEvent event) {
			if (areaTool.contains(event.getPoint())) {
				//�ı������״������ͼ��ʱ��ʾΪ�ƶ���ť
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				currentShape = areaTool.getCurrent();
			}else {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if (currentShape != null && currentShape.centerX > WALL_X && currentShape.centerX < WALL_X+WALL_WIDTH
						&& currentShape.centerY > WALL_Y && currentShape.centerY < WALL_Y+WALL_HEIGHT){
					//todo ��������
					bol = true;
				}
			}
		}
		
		
	}
}