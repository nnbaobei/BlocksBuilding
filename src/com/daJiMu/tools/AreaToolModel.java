package com.daJiMu.tools;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.daJiMu.shapes.ShapeRoot;

public class AreaToolModel {
	public ArrayList<ShapeRoot> shapes;

	private int current = 0;
	/**
	 * @param listSize 构造图形列表的长度
	 */
	public AreaToolModel(int listSize) {
		super();
		shapes = new ArrayList<ShapeRoot>(listSize);
	}
	
	public boolean contains(Point2D p) {
		for (int i=0;i<shapes.size();i++) {
			if (shapes.get(i).contains(p)) {
				current = i;
				return true;
			}
		}
		return false;
	}
	
	public ShapeRoot getCurrent() {
		return shapes.get(current);
	}
	
}
