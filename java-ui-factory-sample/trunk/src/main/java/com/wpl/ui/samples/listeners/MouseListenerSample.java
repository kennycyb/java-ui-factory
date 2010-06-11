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
import java.awt.event.MouseWheelEvent;

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

	void onMouseListenerSample_mouseDragged(final MouseEvent e) {
		LOGGER.debug("onMouseListenerSample_mouseDragged: {}", e.getPoint());
	}

	void onMouseListenerSample_mouseMoved(final MouseEvent e) {
		LOGGER.debug("onMouseListenerSample_mouseMoved: {}", e.getPoint());

	}

	void onMouseListenerSample_mouseClicked(final MouseEvent e) {
		// Invoked when the mouse button has been clicked (pressed and released)
		// on a component.
		LOGGER.debug("onMouseListenerSample_mouseClicked");
	}

	void onMouseListenerSample_mouseEntered(final MouseEvent e) {
		// Invoked when the mouse enters a component.
		LOGGER.debug("onMouseListenerSample_mouseEntered");
	}

	void onMouseListenerSample_mouseExited(final MouseEvent e) {
		// Invoked when the mouse exits a component.
		LOGGER.debug("onMouseListenerSample_mouseExited");
	}

	void onMouseListenerSample_mousePressed(final MouseEvent e) {
		// Invoked when a mouse button has been pressed on a component.
		LOGGER.debug("onMouseListenerSample_mousePressed");
	}

	void onMouseListenerSample_mouseReleased(final MouseEvent e) {
		// Invoked when a mouse button has been released on a component.
		LOGGER.debug("onMouseListenerSample_mouseReleased");
	}

	void onMouseListenerSample_mouseWheelMoved(final MouseWheelEvent e) {
		// Invoked when the mouse wheel is rotated.
		LOGGER.debug("onMouseListenerSample_mouseWheelMoved");
	}

	public static void main(final String[] args) {
		SwingFactory.create(MouseListenerSample.class).setVisible(true);
	}
}
