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

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.UiText;
import com.github.kennycyb.uifactory.core.factory.annotations.menu.UiMenuBarItems;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;

/**
 * 
 * @author kenny
 * @since 1.0
 */
public class JMenuFactory extends JComponentFactory {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JMenuFactory.class);

	/**
	 * 
	 * @param context
	 * @param component
	 * @param annotate
	 * @since 1.0
	 */
	@UiAnnotationHandler(UiText.class)
	protected void handleUiText(final ComponentContext context,
			final JMenu component, final UiText annotate) {
		component.setText(annotate.value());
		LOGGER.debug("(JMenu){}.setText(\"{}\")", context.getId(),
				annotate.value());
	}

	/**
	 * 
	 * @param context
	 * @param component
	 * @param annotate
	 * @since 1.0
	 */
	@UiAnnotationHandler(UiMenuBarItems.class)
	protected void handleUiMenuBarItems(final ComponentContext context,
			final JMenu component, final UiMenuBarItems annotate) {

		for (final String item : annotate.value()) {

			if (item.equals("-")) {
				component.addSeparator();
				continue;
			}

			final JMenuItem m = new JMenuItem(item);
			component.add(m);
		}

	}
}
