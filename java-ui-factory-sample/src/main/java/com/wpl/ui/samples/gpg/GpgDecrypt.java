package com.wpl.ui.samples.gpg;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiScrollable;
import com.wpl.ui.factory.annotations.components.JButtonProperties;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.annotations.components.JLabelProperties;
import com.wpl.ui.factory.annotations.components.JTextAreaProperties;
import com.wpl.ui.factory.annotations.components.JTextFieldProperties;
import com.wpl.ui.factory.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.factory.enums.BorderLayoutConstraint;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;

@JFrameProperties(frameCloseOperation = FrameCloseOperation.DISPOSE, title = "Decrypt", windowPosition = WindowPosition.CENTER, height = 400, width = 400)
@UiLayout(BorderLayout.class)
public class GpgDecrypt extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3789607028519367715L;

	@UiLayout(SpringLayout.class)
	private class ContentPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@JLabelProperties(text = "Input")
		JLabel inputLabel;

		@JTextFieldProperties()
		JTextField inputFile;

		@JLabelProperties(text = "Output")
		JLabel ouputLabel;

		@JTextFieldProperties()
		JTextField outputFile;

		@JLabelProperties(text = "Password")
		JLabel passwordLabel;

		JPasswordField password;

		@JLabelProperties(text = "Console")
		JLabel consoleLabel;

		@UiScrollable
		@JTextAreaProperties(rows = 10)
		JTextArea console;
	}

	@UiLayout(FlowLayout.class)
	private class CommandPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@JButtonProperties(text = "Decrypt")
		JButton decrypt;
	}

	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	ContentPanel content;

	@UiBorderLayoutConstraint(BorderLayoutConstraint.SOUTH)
	CommandPanel command;

	void onDecrypt_actionPerformed(ActionEvent e) {
		StringBuilder sb = new StringBuilder("gpg");

		sb.append(" --output ").append(content.outputFile.getText());
		sb.append(" --decrypt ").append(content.inputFile.getText());

		try {
			final Process process = Runtime.getRuntime().exec(sb.toString());

			new Thread() {
				@Override
				public void run() {
					InputStream in = process.getInputStream();
					while (true) {
						try {
							int r = in.read();
							content.console.append(String.valueOf(r));
							this.wait(1);
						} catch (IOException e) {
							e.printStackTrace();
							break;
						} catch (InterruptedException e) {
						}
					}
				}
			}.start();

			char[] password = content.password.getPassword();
			byte[] pass = new byte[password.length];
			for (int i = 0; i < password.length; i++) {
				pass[i] = (byte) password[i];
			}
			process.getOutputStream().write(pass);
			process.waitFor();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
