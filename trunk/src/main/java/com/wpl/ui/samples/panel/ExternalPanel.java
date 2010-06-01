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
package com.wpl.ui.samples.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiScrollable;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.enums.BorderLayoutConstraint;
import com.wpl.ui.events.EventHandler;

/**
 * 
 * @since 1.0
 */
@UiLayout(BorderLayout.class)
public class ExternalPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1384552017351526875L;

	private static Logger LOGGER = LoggerFactory.getLogger(ExternalPanel.class);

	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	@UiScrollable
	JTextArea content;

	@UiBorderLayoutConstraint(BorderLayoutConstraint.SOUTH)
	@UiText("click me!")
	JButton button;

	final EventHandler<CustomEventArgs> customEvent = new EventHandler<CustomEventArgs>();

	void onButton_actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this, "click me! Clicked");
		content.append("click me! Clicked\n");

		customEvent.invoke(new CustomEventArgs(this, "Click Me"));
	}
}
