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

import javax.swing.JComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.UiLayout;
import com.github.kennycyb.uifactory.core.factory.annotations.UiToolTip;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.ComponentFactory;

/**
 * @since 1.0
 * @author kenny
 * 
 */
public class JComponentFactory extends ComponentFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JComponentFactory.class);

	@UiAnnotationHandler(UiLayout.class)
	protected void handleUiLayout(final ComponentContext context,
			final JComponent component, final UiLayout layout) {

		try {
			component.setLayout(layout.value().newInstance());
			LOGGER.debug("{}|JComponent.setLayout({})", context.getId(),
					layout.value());
		} catch (final InstantiationException e) {
			LOGGER.error("{}|Unable to create layout manager {}",
					context.getId(), layout.value());
		} catch (final IllegalAccessException e) {
			LOGGER.error("{}|Unable to create layout manager {}",
					context.getId(), layout.value());
		}
	}

	@UiAnnotationHandler(UiToolTip.class)
	protected void handleUiToolTip(final ComponentContext context,
			final JComponent component, final UiToolTip annotate) {

		component.setToolTipText(annotate.value());
	}

	@Override
	protected void wireComponent(final ComponentContext context) {
		super.wireComponent(context);

	}
}
