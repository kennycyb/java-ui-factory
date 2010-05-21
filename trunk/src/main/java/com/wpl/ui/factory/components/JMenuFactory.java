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

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.menu.UiMenuBarItems;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;

public class JMenuFactory extends JComponentFactory {
	private static Logger LOGGER = LoggerFactory.getLogger(JMenuFactory.class);

	@Override
	protected Class<?> defaultType() {
		return JMenu.class;
	}

	@UiAnnotationHandler(UiText.class)
	protected void handlerUiText(ComponentContext context, JMenu component,
			UiText annotate) {
		component.setText(annotate.value());
		LOGGER.debug("(JMenu){}.setText(\"{}\")", context.getId(), annotate
				.value());
	}

	@UiAnnotationHandler(UiMenuBarItems.class)
	protected void handlerUiMenuBarItems(ComponentContext context,
			JMenu component, UiMenuBarItems annotate) {

		for (String item : annotate.value()) {

			if (item.equals("-")) {
				component.addSeparator();
				continue;
			}

			JMenuItem m = new JMenuItem(item);
			component.add(m);
		}

	}
}
