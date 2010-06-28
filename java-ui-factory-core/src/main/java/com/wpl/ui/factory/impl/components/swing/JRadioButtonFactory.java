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

import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.components.JRadioButtonProperties;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.impl.UiAnnotationHandler;

/**
 * @author kenny
 * @since 1.0
 */
public class JRadioButtonFactory extends JToggleButtonFactory {
	private static Logger LOGGER = LoggerFactory
			.getLogger(JRadioButtonFactory.class);

	private static final ThreadLocal<Map<String, ButtonGroup>> buttonGroups = new ThreadLocal<Map<String, ButtonGroup>>();

	@UiAnnotationHandler(JRadioButtonProperties.class)
	void handleJRadioButtonProperties(final ComponentContext context,
			final JRadioButton component, final JRadioButtonProperties annotate) {

		component.setText(annotate.text());
		LOGGER.debug("{}|JRadioButton.setText(\"{}\")", context.getId(),
				annotate.text());

		component.setSelected(annotate.selected());
		LOGGER.debug("{}|JRadioButton.setSelected({})", context.getId(),
				annotate.selected());

		if (!annotate.groupId().isEmpty()) {
			if (buttonGroups.get() == null) {
				buttonGroups.set(new HashMap<String, ButtonGroup>());
			}

			ButtonGroup group = buttonGroups.get().get(annotate.groupId());
			if (group == null) {
				group = new ButtonGroup();
				buttonGroups.get().put(annotate.groupId(), group);
			}

			group.add(component);
			LOGGER.debug("{}|added to ButtonGroup: {}", context.getId(),
					annotate.groupId());
		}
	}
}
