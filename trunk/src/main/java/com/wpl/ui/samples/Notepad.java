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
package com.wpl.ui.samples;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.UiFactory;
import com.wpl.ui.annotations.UiAutoWired;
import com.wpl.ui.annotations.UiInit;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiResource;
import com.wpl.ui.annotations.UiScrollable;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.annotations.frame.UiFrameResizable;
import com.wpl.ui.enums.BorderLayoutConstraint;
import com.wpl.ui.enums.FrameCloseOperation;
import com.wpl.ui.enums.ScrollBarPolicy;

/**
 * 
 */
@UiText("Notepad")
@UiSize(height = 800, width = 800)
@UiLayout(BorderLayout.class)
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
@UiFrameResizable
@UiAutoWired
public class Notepad extends JFrame {

	private static Logger LOGGER = LoggerFactory.getLogger(Notepad.class);

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	@UiScrollable(horizontal = ScrollBarPolicy.ALWAYS, vertical = ScrollBarPolicy.ALWAYS)
	private JTextArea content;

	@UiBorderLayoutConstraint(BorderLayoutConstraint.NORTH)
	@UiResource("Notepad-Menu.xml")
	private JMenuBar menuBar;

	@UiInit
	private void init() {
	}

	private void onMenuBar_clicked(Object sender, String menuId) {

		if (menuId == null) {
			return;
		}

		if (menuId.equals("file.new")) {
			LOGGER.debug("menu-clicked=file.new, source={}", sender);
			content.setText("");
		}

		if (menuId.equals("file.exit")) {
			LOGGER.debug("menu-clicked=file.exit, source={}", sender);
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		UiFactory factory = new UiFactory();
		JFrame notepad = factory.createFrame(Notepad.class);

		notepad.setVisible(true);
	}
}
