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
package com.wpl.ui.samples.components.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiInit;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiResource;
import com.wpl.ui.factory.annotations.UiScrollable;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.factory.enums.BorderLayoutConstraint;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.ScrollBarPolicy;
import com.wpl.ui.factory.enums.WindowPosition;

/**
 * 
 * @since 1.0
 */
@JFrameProperties(height = 600, width = 800, frameCloseOperation = FrameCloseOperation.EXIT, title = "JToolBarSample", windowPosition = WindowPosition.CENTER)
@UiLayout(BorderLayout.class)
public class JToolBarSample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		JOptionPane.showMessageDialog(this,
				"onToolbar_actionPerformed: " + e.getActionCommand());
	}

	void onToolbar_itemStateChanged(final ItemEvent e) {
		LOGGER.debug(
				"onToolbar_itemStateChanged: {} ({})",
				e.getItem(),
				e.getStateChange() == ItemEvent.DESELECTED ? "DESELECTED"
						: e.getStateChange() == ItemEvent.ITEM_FIRST ? "ITEM_FIRST"
								: e.getStateChange() == ItemEvent.ITEM_LAST ? "ITEM_LAST"
										: e.getStateChange() == ItemEvent.ITEM_STATE_CHANGED ? "ITEM_STATE_CHANGED"
												: e.getStateChange() == ItemEvent.SELECTED ? "SELECTED"
														: "UNKNOWN");
	}

	public static void main(final String[] args) {
		SwingFactory.create(JToolBarSample.class).setVisible(true);
	}
}
