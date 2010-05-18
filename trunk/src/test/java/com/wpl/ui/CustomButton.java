package com.wpl.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class CustomButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomButton() {
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(CustomButton.this, "HELLO 2!");
			}
		});
	}
}
