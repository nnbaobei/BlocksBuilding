package com.daJiMu.tools;

import java.util.ArrayList;

import com.daJiMu.shapes.ShapeRoot;

public class AreaModel {
	public ArrayList<ShapeRoot> shapes;
	public boolean isDrop = false;
	
	
	public AreaModel(int listSize) {
		super();
		shapes = new ArrayList<ShapeRoot>(listSize);
	}	
}
