package com.wpl.ui.samples.gpg;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.components.JButtonProperties;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;

@UiLayout(FlowLayout.class)
@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, windowPosition = WindowPosition.CENTER, title = "GPG", height = 400, width = 300)
public class GpgConsole extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4416152086085983920L;

	@JButtonProperties(text = "Decrypt")
	JButton decrypt;

	void onDecrypt_actionPerformed(ActionEvent e) {
		SwingFactory.create(GpgDecrypt.class).setVisible(true);
	}

	public static void main(String[] args) {
		SwingFactory.create(GpgConsole.class).setVisible(true);
	}
}
