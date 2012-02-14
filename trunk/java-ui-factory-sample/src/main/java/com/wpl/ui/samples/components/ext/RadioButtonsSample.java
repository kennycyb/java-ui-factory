package com.wpl.ui.samples.components.ext;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import com.wpl.ui.ext.RadioButtons;
import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.factory.enums.BorderLayoutConstraint;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;

@UiLayout(BorderLayout.class)
@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, windowPosition = WindowPosition.CENTER)
public class RadioButtonsSample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	RadioButtons radioButtons;

	void radioButtons_init() {

		radioButtons.addRadioButton(new JRadioButton("Male"));
		radioButtons.addRadioButton(new JRadioButton("Female"));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingFactory.create(RadioButtonsSample.class).setVisible(true);
	}

}
