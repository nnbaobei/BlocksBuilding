package com.daJiMu.tools;

import com.daJiMu.shapes.ShapeRoot;

public class Cell {
	//高度120，宽度180
	public static int row = 120;
	public static int col = 180;
	
	public int centerX;
	public int centerY;
	public int x;
	public int y;
	
	//每个cell中都包含一个随机的块,初始化时使用
	//public ShapeRoot shape;

	public Cell(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.centerX = x + col/2;
		this.centerY = y + row/2;
	}
	
	@Override
	public String toString() {
		return "row : "+Cell.row+", col : "+Cell.col;
	}
}
