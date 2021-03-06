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
package com.github.kennycyb.uifactory.core.factory.impl.components.awt;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.frame.UiWindowPosition;
import com.github.kennycyb.uifactory.core.factory.enums.WindowPosition;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;
import com.github.kennycyb.uifactory.core.listeners.MethodListenerProxy;

public class WindowFactory extends ContainerFactory {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WindowFactory.class);

	@Override
	public void initialize(final ComponentContext context) {
		super.initialize(context);

		if (context.isPack()) {
			LOGGER.debug("{}|Packing", context.getId());
			((Window) context.getComponent()).pack();
		}
	}

	/**
	 * Handle WindowPosition.
	 * 
	 * @param context
	 * @param component
	 * @param value
	 */
	protected void windowPosition(final ComponentContext context,
			final Window component, final WindowPosition value) {

		// Default position - do nothing

		if (value == WindowPosition.DEFAULT) {
			LOGGER.debug("{}|default window position", context.getId());
			return;
		}

		// Full screen

		if (value == WindowPosition.FULL) {
			context.addPostInit(new Runnable() {
				@Override
				public void run() {
					final Dimension dim = Toolkit.getDefaultToolkit()
							.getScreenSize();
					component.setSize(dim);
					component.setLocation(0, 0);
					component.validate();
					component.repaint();
				}
			});
			return;
		}

		// Center the Window

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
	void handleUiWindowPosition(final ComponentContext context,
			final Window component, final UiWindowPosition annotate) {
		windowPosition(context, component, annotate.value());
	}

	@Override
	protected void wireComponent(final ComponentContext context) {

		super.wireComponent(context);

		final Window window = (Window) context.getComponent();

		final MethodListenerProxy<WindowListener> windowListenerProxy = new MethodListenerProxy<WindowListener>(
				WindowListener.class, context.getActionListeners());

		final MethodListenerProxy<WindowFocusListener> windowFocusListenerProxy = new MethodListenerProxy<WindowFocusListener>(
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
