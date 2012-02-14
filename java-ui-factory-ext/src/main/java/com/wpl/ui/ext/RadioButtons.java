package com.wpl.ui.ext;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RadioButtons extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7363872197150033227L;

	private ButtonGroup mButtonGroup = new ButtonGroup();
	private List<JRadioButton> mRadioButtons = new ArrayList<JRadioButton>();

	public void addRadioButton(JRadioButton button) {
		mRadioButtons.add(button);
		mButtonGroup.add(button);
		this.add(button);
	}
}
