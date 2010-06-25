package com.wpl.ui.samples.components.swing;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.components.JFrameProperties;
import com.wpl.ui.annotations.components.JRadioButtonProperties;
import com.wpl.ui.enums.FrameCloseOperation;
import com.wpl.ui.enums.WindowPosition;
import com.wpl.ui.factory.SwingFactory;
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
