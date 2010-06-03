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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiInit;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiResource;
import com.wpl.ui.annotations.UiScrollable;
import com.wpl.ui.annotations.components.JFrameProperties;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.enums.BorderLayoutConstraint;
import com.wpl.ui.enums.FrameCloseOperation;
import com.wpl.ui.enums.ScrollBarPolicy;
import com.wpl.ui.enums.WindowPosition;
import com.wpl.ui.factory.SwingFactory;

/**
 * 
 * @since 1.0
 */
@JFrameProperties(height = 600, width = 800, frameCloseOperation = FrameCloseOperation.EXIT, title = "JToolBarSample", windowPosition = WindowPosition.CENTER)
@UiLayout(BorderLayout.class)
public class JToolBarSample extends JFrame {

	private static Logger LOGGER = LoggerFactory
			.getLogger(JToolBarSample.class);

	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	@UiScrollable(horizontal = ScrollBarPolicy.ALWAYS, vertical = ScrollBarPolicy.ALWAYS)
	JTextArea text;

	@UiResource("JToolbarSample-Toolbar.xml")
	@UiBorderLayoutConstraint(BorderLayoutConstraint.NORTH)
	JToolBar toolbar;

	@UiInit
	void init() {
	}

	void onToolbar_actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this, "onToolbar_actionPerformed: "
				+ e.getActionCommand());
	}

	public static void main(final String[] args) {
		SwingFactory.create(JToolBarSample.class).setVisible(true);
	}
}
