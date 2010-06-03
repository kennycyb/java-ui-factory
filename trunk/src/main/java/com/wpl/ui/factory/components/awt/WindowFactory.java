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
package com.wpl.ui.factory.components.awt;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.frame.UiWindowPosition;
import com.wpl.ui.enums.WindowPosition;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;
import com.wpl.ui.listeners.MethodListenerProxy;

public class WindowFactory extends ContainerFactory {
	private static Logger LOGGER = LoggerFactory.getLogger(WindowFactory.class);

	@Override
	public void initialize(ComponentContext context) {
		super.initialize(context);

		if (context.isPack()) {
			((Window) context.getComponent()).pack();
		}
	}

	protected void windowPosition(final ComponentContext context,
			final Window component, final WindowPosition value) {

		if (value == WindowPosition.DEFAULT) {
			LOGGER.debug("{}|default window position", context.getId());
			return;
		}

		// WindowPosition.CENTER

		context.addPostInit(new Runnable() {
			@Override
			public void run() {

				component.invalidate();

				if (context.isPack()) {
					component.pack();
					LOGGER.debug("{}|packed", context.getId());
				}

				final Dimension dim = component.getToolkit().getScreenSize();
				final Rectangle abounds = component.getBounds();

				final int centerX = (dim.width - abounds.width) / 2;
				final int centerY = (dim.height - abounds.height) / 2;

				component.setLocation(centerX, centerY);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("{}|Position window to center: x={}, y={}",
							new Object[] { context.getId(), centerX, centerY });
				}
			}
		});
	}

	@UiAnnotationHandler(UiWindowPosition.class)
	void handleUiWindowPosition(ComponentContext context,
			final Window component, UiWindowPosition annotate) {
		windowPosition(context, component, annotate.value());
	}

	@Override
	protected void wireComponent(ComponentContext context) {

		super.wireComponent(context);

		Window window = (Window) context.getComponent();

		MethodListenerProxy<WindowListener> windowListenerProxy = new MethodListenerProxy<WindowListener>(
				WindowListener.class, context.getActionListeners());

		MethodListenerProxy<WindowFocusListener> windowFocusListenerProxy = new MethodListenerProxy<WindowFocusListener>(
				WindowFocusListener.class, context.getActionListeners());

		if (windowListenerProxy.hasListeningMethod()) {
			window.addWindowListener(windowListenerProxy.getProxy());
			LOGGER.debug("{}|Window.addWindowListener", context.getId());
		}

		if (windowFocusListenerProxy.hasListeningMethod()) {
			window.addWindowFocusListener(windowFocusListenerProxy.getProxy());
			LOGGER.debug("{}|Window.addWindowFocusListener", context.getId());
		}
	}
}
