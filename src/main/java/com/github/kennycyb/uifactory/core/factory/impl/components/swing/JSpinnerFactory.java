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

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.components.spinner.SpinnerIntegerModelProperties;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;

/**
 * 
 * @since 1.0
 */
public class JSpinnerFactory extends JComponentFactory {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JSpinnerFactory.class);

	@UiAnnotationHandler(SpinnerIntegerModelProperties.class)
	void handleSpinnerIntegerModelProperties(final ComponentContext context,
			final JSpinner component,
			final SpinnerIntegerModelProperties annotate) {

		component.setModel(new SpinnerNumberModel(annotate.value(), annotate
				.minimum(), annotate.maximum(), annotate.stepSize()));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"{}|JSpinner.setModel(SpinnerNumberModel({}, {}, {}, {}))",
					new Object[] { context.getId(), annotate.value(),
							annotate.minimum(), annotate.maximum(),
							annotate.stepSize() });
		}
	}
}
