package com.daJiMu.Test;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

class DrawFrame extends JFrame {
	public DrawFrame() {
		add(new DrawComponent());
		pack();
	}
}