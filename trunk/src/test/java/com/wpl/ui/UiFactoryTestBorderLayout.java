package com.wpl.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.Assert;
import org.junit.Test;

import com.wpl.ui.annotations.UiBorderLayoutConstraint;
import com.wpl.ui.annotations.UiFont;
import com.wpl.ui.annotations.UiInit;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.annotations.UiText;

@UiLayout(BorderLayout.class)
@UiSize(height = 480, width = 640)
public class UiFactoryTestBorderLayout extends JPanel {

	private static final long serialVersionUID = 1L;

	@UiText("Hello World!")
	@UiFont(name = "Tahoma", style = Font.BOLD, size = 36)
	@UiBorderLayoutConstraint(BorderLayout.NORTH)
	private JLabel mLabel;

	@UiText("My Button")
	@UiBorderLayoutConstraint(BorderLayout.SOUTH)
	private JButton mButton;

	@UiText("Default TEXT VALUE")
	@UiBorderLayoutConstraint(BorderLayout.CENTER)
	private JTextField mTextField;

	@SuppressWarnings("unused")
	@UiInit
	private void init() {

		Assert.assertNotNull(mLabel);
		Assert.assertNotNull(mButton);
		Assert.assertNotNull(mTextField);

	}

	@Test
	public void myPanelShow() throws InterruptedException {

		UiFactory factory = new UiFactory();

		JFrame frame = new JFrame();
		frame.getContentPane().add(
				factory.createPanel(UiFactoryTestBorderLayout.class));
		frame.setSize(1024, 768);
		frame.setLocation(0, 0);
		frame.setVisible(true);
		Thread.sleep(60000);
	}
}
