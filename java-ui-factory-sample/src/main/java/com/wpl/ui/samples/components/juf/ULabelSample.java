package com.wpl.ui.samples.components.juf;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.components.ILabel;
import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiInit;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.annotations.constraints.UiSpringGridConstraint;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;

@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, title = "ULabelSample", windowPosition = WindowPosition.CENTER)
@UiLayout(SpringLayout.class)
@UiSpringGridConstraint(cols = 3)
public class ULabelSample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = LoggerFactory.getLogger(ULabelSample.class);

	private ILabel myLabel;

	@UiInit
	void init() {
		LOGGER.debug("init OK");
	}

	public static void main(String[] args) {
		SwingFactory.create(ULabelSample.class).setVisible(true);
	}
}
