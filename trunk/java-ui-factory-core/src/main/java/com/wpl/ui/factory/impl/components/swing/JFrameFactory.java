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

import java.awt.LayoutManager;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.impl.UiAnnotationHandler;
import com.wpl.ui.factory.impl.components.awt.FrameFactory;
import com.wpl.ui.layout.managers.NullLayout;

/**
 * 
 * @author kenny
 * @since 1.0
 */
public class JFrameFactory extends FrameFactory {

	private static Logger LOGGER = LoggerFactory.getLogger(JFrameFactory.class);

	protected void frameCloseOperation(ComponentContext context,
			JFrame component, FrameCloseOperation value) {
		component.setDefaultCloseOperation(value.getSwingConstant());

		LOGGER.debug("{}|JFrame.setDefaultCloseOperation({})", context.getId(),
				value);
	}

	@UiAnnotationHandler(UiFrameCloseOperation.class)
	protected void handleUiFrameCloseOperation(ComponentContext context,
			JFrame component, UiFrameCloseOperation annotate) {
		frameCloseOperation(context, component, annotate.value());
	}

	@UiAnnotationHandler(JFrameProperties.class)
	protected void handleJFrameProperties(ComponentContext context,
			JFrame component, JFrameProperties annotate) {

		windowPosition(context, component, annotate.windowPosition());

		if (annotate.height() > 0 && annotate.width() > 0) {
			component.setSize(annotate.width(), annotate.height());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{}|JFrame.setSize({},{})", new Object[] {
						context.getId(), annotate.width(), annotate.height() });
			}
			context.setPack(false);
		}

		frameCloseOperation(context, component, annotate.frameCloseOperation());

		title(context, component, annotate.title());
	}

	@UiAnnotationHandler(UiLayout.class)
	protected void handleUiLayout(ComponentContext context, JFrame component,
			UiLayout annotate) {
		if (annotate.value() == NullLayout.class) {
			component.setLayout(null);
			return;
		}

		try {

			LayoutManager lm = annotate.value().newInstance();
			component.setLayout(lm);

			LOGGER.debug("{}|using layout {}", context.getId(), annotate
					.value());

		} catch (InstantiationException e) {
			LOGGER.error("UiLayout - InstantiationException - {} - {} ",
					annotate.value(), e.getMessage());
		} catch (IllegalAccessException e) {
			LOGGER.error("UiLayout - IllegalAccessException - {} - {} ",
					annotate.value(), e.getMessage());
		}
	}
}
