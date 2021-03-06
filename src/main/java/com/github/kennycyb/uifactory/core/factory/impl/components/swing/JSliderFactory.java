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

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.components.JSliderProperties;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;
import com.github.kennycyb.uifactory.core.listeners.MethodListenerProxy;

/**
 * 
 * @since 1.0
 */
public class JSliderFactory extends JComponentFactory {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JSliderFactory.class);

	@Override
	protected void wireComponent(final ComponentContext context) {
		super.wireComponent(context);

		final JSlider component = (JSlider) context.getComponent();

		final MethodListenerProxy<ChangeListener> changeListener = new MethodListenerProxy<ChangeListener>(
				ChangeListener.class, context.getActionListeners());

		if (changeListener.hasListeningMethod()) {
			component.addChangeListener(changeListener.getProxy());
		}
	}

	@UiAnnotationHandler(JSliderProperties.class)
	void handleJSliderProperties(final ComponentContext context,
			final JSlider component, final JSliderProperties annotate) {

		component.setMaximum(annotate.maximum());
		LOGGER.debug("{}|JSlider.setMaximum({})", context.getId(),
				annotate.maximum());

		component.setMinimum(annotate.minimum());
		LOGGER.debug("{}|JSlider.setMinimum({})", context.getId(),
				annotate.minimum());

		component.setInverted(annotate.inverted());
		LOGGER.debug("{}|JSlider.setInverted({})", context.getId(),
				annotate.inverted());

		component.setValue(annotate.value());
		LOGGER.debug("{}|JSlider.setValue({})", context.getId(),
				annotate.value());

		component.setOrientation(annotate.orientation().getSwingConstant());
		LOGGER.debug("{}|JSlider.setOrientation({})", context.getId(),
				annotate.orientation());

	}
}
