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

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiCursor;
import com.wpl.ui.factory.annotations.UiEnabled;
import com.wpl.ui.factory.annotations.UiIcon;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiText;
import com.wpl.ui.factory.annotations.UiToolTip;
import com.wpl.ui.factory.annotations.UiType;
import com.wpl.ui.factory.annotations.constraints.UiSpringGridConstraint;
import com.wpl.ui.factory.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.factory.annotations.frame.UiWindowPosition;
import com.wpl.ui.factory.enums.CursorType;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;
import com.wpl.ui.samples.components.swing.CustomJButton;

/**
 * 
 * @since 1.0
 */
@UiWindowPosition(WindowPosition.CENTER)
@UiText("Sample Button")
@UiLayout(SpringLayout.class)
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
@UiSpringGridConstraint(cols = 2)
public class SampleButton extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory.getLogger(SampleButton.class);

	@UiText("Button with tooltip")
	JLabel label1;

	@UiToolTip("hello tooltip")
	@UiText("hello")
	JButton hello;

	@UiText("Button disabled")
	JLabel label2;

	@UiEnabled(false)
	@UiText("disabled")
	JButton disabled;

	@UiText("Button with icon only")
	JLabel label3;

	@UiIcon("iMac.icon")
	JButton iconButton;

	@UiText("Button with icon and text")
	JLabel label4;
	@UiText("iconWithText")
	@UiIcon("iMac.icon")
	JButton iconWithText;

	@UiText("Custom Button")
	JLabel label5;

	@UiType(CustomJButton.class)
	@UiText("custom button")
	@UiCursor(CursorType.HAND)
	JButton customButton;

	void onHello_actionPerformed(final ActionEvent e) {
		JOptionPane.showMessageDialog(this, "onHello_actionPerformed");
	}

	void onIconWithText_actionPerformed(final ActionEvent e) {
		JOptionPane.showMessageDialog(this, "onIconWithText_actionPerformed");
	}

	public static void main(final String[] args) {
		SwingFactory.create(SampleButton.class).setVisible(true);
	}
}
