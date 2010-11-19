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
package com.wpl.ui.factory.impl.components.swing;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.annotations.UiAlignment;
import com.wpl.ui.factory.annotations.UiIcon;
import com.wpl.ui.factory.annotations.UiText;
import com.wpl.ui.factory.annotations.components.JLabelProperties;
import com.wpl.ui.factory.impl.UiAnnotationHandler;

public class JLabelFactory extends JComponentFactory {

	private static Logger LOGGER = LoggerFactory.getLogger(JLabelFactory.class);

	@UiAnnotationHandler(UiText.class)
	protected void handleUiText(final ComponentContext context,
			final JLabel component, final UiText annotate) {
		component.setText(annotate.value());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|JLabel.setText(\"{}\")", context.getId(),
					annotate.value());
		}
	}

	@UiAnnotationHandler(JLabelProperties.class)
	protected void handleJFrameProperties(final ComponentContext context,
			final JLabel component, final JLabelProperties annotate) {

		component.setText(annotate.text());
	}

	@UiAnnotationHandler(UiAlignment.class)
	protected void handleUiAlignment(final ComponentContext context,
			final JLabel component, final UiAlignment annotate) {

		component.setHorizontalAlignment(annotate.horizontal()
				.getSwingConstant());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|JLabel.setHorizontalAlignment({})",
					context.getId(), annotate.horizontal());
		}
	}

	@UiAnnotationHandler(UiIcon.class)
	protected void handleUiIcon(final ComponentContext context,
			final JLabel component, final UiIcon annotate) {
		final URL url = getClass().getClassLoader().getResource(
				annotate.value());
		if (url == null) {
			LOGGER.warn("{}|JLabel - icon not found - {}", context.getId(),
					annotate.value());
			return;
		}

		component.setIcon(new ImageIcon(url));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|JLabel.setIcon(new ImageIcon(\"{}\"))",
					context.getId(), annotate.value());
		}
	}
}
