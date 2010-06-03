/*
 * Copyright 2010 Kenny Chong (wongpeiling.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wpl.ui.samples.components;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiEnabled;
import com.wpl.ui.annotations.UiIcon;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.UiToolTip;
import com.wpl.ui.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.annotations.frame.UiWindowPosition;
import com.wpl.ui.enums.FrameCloseOperation;
import com.wpl.ui.enums.WindowPosition;
import com.wpl.ui.factory.SwingFactory;

/**
 * 
 * @since 1.0
 */
@UiWindowPosition(WindowPosition.CENTER)
@UiText("Sample Button")
@UiLayout(FlowLayout.class)
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
public class SampleButton extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory.getLogger(SampleButton.class);

	@UiToolTip("hello tooltip")
	@UiText("hello")
	JButton hello;

	@UiEnabled(false)
	@UiText("disabled")
	JButton disabled;

	@UiIcon("iMac.icon")
	JButton iconButton;

	@UiText("iconWithText")
	@UiIcon("iMac.icon")
	JButton iconWithText;

	void onHello_actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this, "onHello_actionPerformed");
	}

	void onIconWithText_actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this, "onIconWithText_actionPerformed");
	}

	public static void main(String[] args) {
		SwingFactory.create(SampleButton.class).setVisible(true);
	}
}
