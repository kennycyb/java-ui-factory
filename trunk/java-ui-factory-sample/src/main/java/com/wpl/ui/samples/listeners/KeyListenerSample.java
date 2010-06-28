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
package com.wpl.ui.samples.listeners;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiLayout;
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
@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, height = 600, width = 800, title = "KeyListenerSample", windowPosition = WindowPosition.CENTER)
@UiLayout(BorderLayout.class)
public class KeyListenerSample extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory
			.getLogger(KeyListenerSample.class);

	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	@UiScrollable(horizontal = ScrollBarPolicy.ALWAYS, vertical = ScrollBarPolicy.ALWAYS)
	JTextArea textarea;

	void onTextarea_keyPressed(final KeyEvent e) {
		// Invoked when a key has been pressed.
		LOGGER.debug("onTextarea_keyPressed: {}", e.getKeyChar());
	}

	void onTextarea_keyReleased(final KeyEvent e) {
		// Invoked when a key has been released.
		LOGGER.debug("onTextarea_keyReleased: {}", e.getKeyChar());
	}

	void onTextarea_keyTyped(final KeyEvent e) {
		// Invoked when a key has been typed.
		LOGGER.debug("onTextarea_keyTyped: {}", e.getKeyChar());
	}

	public static void main(final String[] args) {
		SwingFactory.create(KeyListenerSample.class).setVisible(true);
	}
}
