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
package com.wpl.ui.factory.components;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.frame.UiWindowPosition;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.FactoryContext;
import com.wpl.ui.factory.MethodListenerProxy;
import com.wpl.ui.factory.UiAnnotationHandler;

public class WindowFactory extends ContainerFactory {
	private static Logger LOGGER = LoggerFactory.getLogger(WindowFactory.class);

	@Override
	protected Class<?> defaultType() {
		return Window.class;
	}

	@UiAnnotationHandler(UiWindowPosition.class)
	void handleUiWindowPosition(FactoryContext factory,
			ComponentContext context, Window component,
			UiWindowPosition annotate) {
		Dimension dim = component.getToolkit().getScreenSize();
		Rectangle abounds = component.getBounds();
		component.setLocation((dim.width - abounds.width) / 2,
				(dim.height - abounds.height) / 2);
	}

	@Override
	public void wireComponent(FactoryContext factory, ComponentContext context) {
		super.wireComponent(factory, context);

		MethodListenerProxy<WindowListener> windowListenerProxy = new MethodListenerProxy<WindowListener>(
				factory.getObject(), context.getActionListeners(),
				WindowListener.class);

		MethodListenerProxy<WindowFocusListener> windowFocusListenerProxy = new MethodListenerProxy<WindowFocusListener>(
				factory.getObject(), context.getActionListeners(),
				WindowFocusListener.class);

		Window window = (Window) context.getComponent();
		window.addWindowListener(windowListenerProxy.getProxy());
		window.addWindowFocusListener(windowFocusListenerProxy.getProxy());
	}
}