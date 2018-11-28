package com.daJiMu.tools;

/**
 * 该类定义了候选区的网格大小和位置
 * @author 100622161
 *
 */
public class Cell {
	//高度120，宽度180
	public static int row = 60;
	public static int col = 90;
	
	public int centerX;
	public int centerY;
	public int x;
	public int y;
	

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
