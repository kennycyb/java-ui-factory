package com.wpl.ui.samples.components.swing;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.annotations.components.JRadioButtonProperties;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;
import com.wpl.ui.layout.managers.VerticalFlowLayout;

@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, title = "JRadioButtonSample", windowPosition = WindowPosition.CENTER)
@UiLayout(VerticalFlowLayout.class)
public class JRadioButtonSample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JRadioButtonProperties(text = "Male", groupId = "sex", selected = true)
	JRadioButton male;

	@JRadioButtonProperties(text = "Female", groupId = "sex")
	JRadioButton female;

	public static void main(final String[] args) {
		SwingFactory.create(JRadioButtonSample.class).setVisible(true);
	}
}
