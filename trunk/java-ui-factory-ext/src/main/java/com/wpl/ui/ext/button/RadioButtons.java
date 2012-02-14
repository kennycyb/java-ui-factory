package com.wpl.ui.ext.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.wpl.ui.events.EventHandler;

public class RadioButtons extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7363872197150033227L;

	private ButtonGroup mButtonGroup = new ButtonGroup();
	private List<JRadioButton> mRadioButtons = new ArrayList<JRadioButton>();
	private EventHandler<ActionEvent> actionPerformed = new EventHandler<ActionEvent>();

	private ActionListener mButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			actionPerformed.invoke(e);
		}
	};

	public void addRadioButton(JRadioButton button) {

		button.addActionListener(mButtonListener);

		mRadioButtons.add(button);
		mButtonGroup.add(button);
		this.add(button);
	}
}
