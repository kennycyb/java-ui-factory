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

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.components.JFrameProperties;
import com.wpl.ui.enums.FrameCloseOperation;
import com.wpl.ui.enums.WindowPosition;
import com.wpl.ui.factory.SwingFactory;

/**
 * 
 * @since 1.0
 */
@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, height = 600, width = 800, title = "MouseMotionListener", windowPosition = WindowPosition.CENTER)
public class MouseListenerSample extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory
			.getLogger(MouseListenerSample.class);

	void onMouseMotionListenerSample_mouseDragged(MouseEvent e) {
		LOGGER.debug("onMouseMotionListenerSample_mouseDragged: {}", e
				.getPoint());
	}

	void onMouseMotionListenerSample_mouseMoved(MouseEvent e) {
		LOGGER
				.debug("onMouseMotionListenerSample_mouseMoved: {}", e
						.getPoint());

	}

	void onMouseMotionListenerSample_mouseClicked(MouseEvent e) {
		// Invoked when the mouse button has been clicked (pressed and released)
		// on a component.
		LOGGER.debug("onMouseMotionListenerSample_mouseClicked");
	}

	void onMouseMotionListenerSample_mouseEntered(MouseEvent e) {
		// Invoked when the mouse enters a component.
		LOGGER.debug("onMouseMotionListenerSample_mouseEntered");
	}

	void onMouseMotionListenerSample_mouseExited(MouseEvent e) {
		// Invoked when the mouse exits a component.
		LOGGER.debug("onMouseMotionListenerSample_mouseExited");
	}

	void onMouseMotionListenerSample_mousePressed(MouseEvent e) {
		// Invoked when a mouse button has been pressed on a component.
		LOGGER.debug("onMouseMotionListenerSample_mousePressed");
	}

	void onMouseMotionListenerSample_mouseReleased(MouseEvent e) {
		// Invoked when a mouse button has been released on a component.
		LOGGER.debug("onMouseMotionListenerSample_mouseReleased");
	}

	public static void main(String[] args) {
		SwingFactory.create(MouseListenerSample.class).setVisible(true);
	}
}
