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
package com.github.kennycyb.uifactory.core.factory.impl.components.swing;

import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.UiEnabled;
import com.github.kennycyb.uifactory.core.factory.annotations.UiIcon;
import com.github.kennycyb.uifactory.core.factory.annotations.UiText;
import com.github.kennycyb.uifactory.core.factory.annotations.actions.UiActionCommand;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;
import com.github.kennycyb.uifactory.core.listeners.MethodListenerProxy;

/**
 * Component Factory for AbstractButton.
 * 
 * @since 1.0
 */
public abstract class AbstractButtonFactory extends JComponentFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractButtonFactory.class);

	@Override
	public void wireComponent(final ComponentContext context) {

		super.wireComponent(context);

		final AbstractButton component = (AbstractButton) context
				.getComponent();

		final MethodListenerProxy<ActionListener> actionListenerProxy = new MethodListenerProxy<ActionListener>(
				ActionListener.class, context.getActionListeners());

		if (actionListenerProxy.hasListeningMethod()) {
			component.addActionListener(actionListenerProxy.getProxy());
		}
	}

	@UiAnnotationHandler(UiText.class)
	protected void handleUiText(final ComponentContext context,
			final AbstractButton component, final UiText text) {
		component.setText(text.value());
	}

	@UiAnnotationHandler(UiActionCommand.class)
	protected void handleUiActionCommand(final ComponentContext context,
			final AbstractButton component, final UiActionCommand annotate) {
		component.setActionCommand(annotate.value());
	}

	@UiAnnotationHandler(UiEnabled.class)
	protected void handleUiEnabled(final ComponentContext context,
			final AbstractButton component, final UiEnabled annotate) {
		component.setEnabled(annotate.value());
	}

	@UiAnnotationHandler(UiIcon.class)
	protected void handleUiIcon(final ComponentContext context,
			final AbstractButton component, final UiIcon annotate) {
		final URL url = getClass().getClassLoader().getResource(
				annotate.value());
		if (url == null) {
			LOGGER.warn("(AbstractButtonFactory){} - icon not found - {}",
					context.getId(), annotate.value());
			return;
		}

		component.setIcon(new ImageIcon(url));
		LOGGER.debug(
				"(AbstractButtonFactory){}.setIcon(new ImageIcon(\"{}\"))",
				context.getId(), annotate.value());
	}
}
