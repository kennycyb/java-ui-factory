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
package com.wpl.ui.factory.components.swing;

import javax.swing.JProgressBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.components.JProgressBarProperties;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;

/**
 * 
 * @since 1.0
 */
public class JProgressBarFactory extends JComponentFactory {
	private static Logger LOGGER = LoggerFactory
			.getLogger(JProgressBarFactory.class);

	/**
	 * 
	 * @param context
	 * @param component
	 * @param annotate
	 * @since 1.0
	 */
	@UiAnnotationHandler(JProgressBarProperties.class)
	void handleJProgressBarProperties(ComponentContext context,
			JProgressBar component, JProgressBarProperties annotate) {

		component.setOrientation(annotate.orientation().getSwingConstant());
		LOGGER.debug("{}|JProgressBar.setOrientation({})", context.getId(),
				annotate.orientation());

		component.setMinimum(annotate.minimum());
		LOGGER.debug("{}|JProgressBar.setMinimum({})", context.getId(),
				annotate.minimum());

		component.setMaximum(annotate.maximum());
		LOGGER.debug("{}|JProgressBar.setMaximum({})", context.getId(),
				annotate.maximum());

	}
}
