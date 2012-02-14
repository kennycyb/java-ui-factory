package com.wpl.ui.samples.mysql;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;

@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, height = 400, width = 400, windowPosition = WindowPosition.CENTER)
public class MySQLConsole extends JFrame {

	class Content extends JPanel {

	}

	public static void main(String[] args) {
		SwingFactory.create(MySQLConsole.class).setVisible(true);
	}
}
