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

import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiEnabled;
import com.wpl.ui.annotations.UiIcon;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.actions.UiActionCommand;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;

public abstract class AbstractButtonFactory extends JComponentFactory {

	private static Logger LOGGER = LoggerFactory
			.getLogger(AbstractButtonFactory.class);

	@UiAnnotationHandler(UiText.class)
	protected void handleUiText(ComponentContext context,
			AbstractButton component, UiText text) {
		component.setText(text.value());
	}

	@UiAnnotationHandler(UiActionCommand.class)
	protected void handlerUiActionCommand(ComponentContext context,
			AbstractButton component, UiActionCommand annotate) {
		component.setActionCommand(annotate.value());
	}

	@UiAnnotationHandler(UiEnabled.class)
	protected void handlerUiEnabled(ComponentContext context,
			AbstractButton component, UiEnabled annotate) {
		component.setEnabled(annotate.value());
	}

	@UiAnnotationHandler(UiIcon.class)
	protected void handlerUiIcon(ComponentContext context,
			AbstractButton component, UiIcon annotate) {
		URL url = getClass().getClassLoader().getResource(annotate.value());
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
