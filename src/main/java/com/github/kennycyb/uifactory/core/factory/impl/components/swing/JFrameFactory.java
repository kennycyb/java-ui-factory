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

import java.awt.LayoutManager;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.UiLayout;
import com.github.kennycyb.uifactory.core.factory.annotations.components.JFrameProperties;
import com.github.kennycyb.uifactory.core.factory.annotations.frame.UiFrameCloseOperation;
import com.github.kennycyb.uifactory.core.factory.enums.FrameCloseOperation;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.FrameFactory;
import com.github.kennycyb.uifactory.core.layout.managers.NullLayout;

/**
 * 
 * @author kenny
 * @since 1.0
 */
public class JFrameFactory extends FrameFactory {

	/**
	 * The LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JFrameFactory.class);

	/**
	 * Setup frame close operation.
	 * 
	 * @param context
	 * @param component
	 * @param value
	 */
	protected final void frameCloseOperation(final ComponentContext context,
			final JFrame component, final FrameCloseOperation value) {
		component.setDefaultCloseOperation(value.getSwingConstant());

		LOGGER.debug("{}|JFrame.setDefaultCloseOperation({})", context.getId(),
				value);
	}

	@UiAnnotationHandler(UiFrameCloseOperation.class)
	protected void handleUiFrameCloseOperation(final ComponentContext context,
			final JFrame component, final UiFrameCloseOperation annotate) {
		frameCloseOperation(context, component, annotate.value());
	}

	@UiAnnotationHandler(JFrameProperties.class)
	protected void handleJFrameProperties(final ComponentContext context,
			final JFrame component, final JFrameProperties annotate) {

		windowPosition(context, component, annotate.windowPosition());

		if (annotate.height() > 0 && annotate.width() > 0) {
			component.setSize(annotate.width(), annotate.height());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{}|JFrame.setSize({},{})",
						new Object[] { context.getId(), annotate.width(),
								annotate.height() });
			}
			context.setPack(false);
		}

		frameCloseOperation(context, component, annotate.frameCloseOperation());

		title(context, component, annotate.title());
	}

	@UiAnnotationHandler(UiLayout.class)
	protected void handleUiLayout(final ComponentContext context,
			final JFrame component, final UiLayout annotate) {
		if (annotate.value() == NullLayout.class) {
			component.setLayout(null);
			return;
		}

		try {

			final LayoutManager lm = annotate.value().newInstance();
			component.setLayout(lm);

			LOGGER.debug("{}|using layout {}", context.getId(),
					annotate.value());

		} catch (final InstantiationException e) {
			LOGGER.error("UiLayout - InstantiationException - {} - {} ",
					annotate.value(), e.getMessage());
		} catch (final IllegalAccessException e) {
			LOGGER.error("UiLayout - IllegalAccessException - {} - {} ",
					annotate.value(), e.getMessage());
		}
	}
}
