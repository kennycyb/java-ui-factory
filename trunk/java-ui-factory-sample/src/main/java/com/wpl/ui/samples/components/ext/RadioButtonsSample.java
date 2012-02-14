package com.wpl.ui.samples.components.ext;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.ext.button.RadioButtons;
import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiInit;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.factory.enums.BorderLayoutConstraint;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;

@UiLayout(BorderLayout.class)
@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, windowPosition = WindowPosition.CENTER)
public class RadioButtonsSample extends JFrame {

	private static Logger LOGGER = LoggerFactory
			.getLogger(RadioButtonsSample.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	RadioButtons radioButtons;

	@UiInit
	void uiInit() {

		radioButtons.addRadioButton(new JRadioButton("Male"));
		radioButtons.addRadioButton(new JRadioButton("Female"));
	}

	/**
	 * Callback method when any radio button is clicked.
	 * 
	 * @param e
	 */
	void onRadioButtons_actionPerformed(ActionEvent e) {

		LOGGER.debug("actionPerformed: {}", e.getActionCommand());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingFactory.create(RadioButtonsSample.class).setVisible(true);
	}
}
